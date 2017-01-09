import React from 'react'
import { Router, Route, hashHistory } from 'react-router'
import moment from 'moment'
import Title from '../common/Title'
import Button from '../common/Button'
import FormRow from '../common/FormRow'
import Toast from '../common/Toast'
import Loading from '../common/Loading'
import { mmPost,mmGet } from '../../utils/utils'
import PickDate from './PickDate'
import style from './Reserve.less'

export default class Reserve extends React.Component {

  constructor(props) {
    var _startDate = moment(),
           _endDate = moment().add(1,'days')
    super(props);
    // 获取上一个页面传递过来的参数
    var _houseInfo = this.props.location.state || {
      shopName:'',
      houseName:'',
      memTotalAmt:0
    }

    this.state = {
      houseInfo: _houseInfo,
      houseNum:1,
      houseDays:1,
      showErr:false,
      startDate:_startDate,
      endDate:_endDate,
      isShowErr: false,
      err:null,
      timer:null,
      loading: false
    }
  }

  // 增加/减少 房间套数
  _addHouse(num) {
    var _houseNum = this.state.houseNum + num
    _houseNum = _houseNum < 1 ? 1 : _houseNum
    this.setState({
      houseNum: _houseNum
    })
  }

  // 得到日期选择组件中的日期
  _getDate (startDate,endDate) {
    var _days = endDate.diff(startDate,"days")
    this.setState({
      startDate:startDate,
      endDate:endDate,
      houseDays:_days
    })
  }

  // 显示错误
  _showErr (err) {
    var that = this
    this.setState({
        isShowErr:true,
        err:err
      })
    this.state.timer = setTimeout(function() {
      that.setState({
        isShowErr:false
      })
    },1500)
  }

  // 提交
  _onClick() {
  var that = this
    var startMs = parseInt(that.state.startDate.format('x')), // 当天00:00:00
           endMs = parseInt(that.state.endDate.format('x')),
          yesterday0 = parseInt(moment().subtract(1,"days").format('x') )// 减去一天
    
    if (!this.state.startDate || !this.state.endDate) {
      that._showErr('入住日期和退房日期不能为空')
    } else if (startMs === endMs) {
      that._showErr('入住日期和退房日期不能相同')
    } 
    else if (startMs <= yesterday0 || endMs <= yesterday0) {
      that._showErr('入住日期和退房日期不能早于当前')
    }
    // 提交
    else {
      // loading
      that.setState({
        loading: true
      })

      var houseInfo = this.state.houseInfo
      var oldPrice = houseInfo.memTotalAmt*this.state.houseNum*this.state.houseDays
      hashHistory.push({
        pathname:'/order',
        state: {
          houseInfo: houseInfo,
          startDate: this.state.startDate.format('YYYY.MM.DD'),
          endDate: this.state.endDate.format('YYYY.MM.DD'),
          days: this.state.endDate.diff(this.state.startDate,'days'),
          houseNum: this.state.houseNum,
          oldPrice: oldPrice,
          payAmt:oldPrice
        }
      })
      // var params = {
      //   "houseId": houseInfo.houseId,
      //   shopId: parseInt(houseInfo.shopId),
      //   startTime: this.state.startDate.format('YYYY-MM-DD'),
      //   endTime: this.state.endDate.format('YYYY-MM-DD'),
      //   payPrice: oldPrice,
      //   totalRoomNum : this.state.houseNum
      // }
      // mmPost(window.api.baseUrl + window.api.submitOrder,params)
      //   .then(data => {
      //     that.setState({
      //       loading: false
      //     })
      //     if ((data.code === 0 || data.code === '0') ) {

      //     } else {
      //       that._showErr(data.message || window.err)
      //     }
      //   }, err=> {
      //     that.setState({
      //       loading: false
      //     })
      //     that._showErr(window.err)
      //   })
    }
  }

  componentWillUnmount() {
    this.state.timer && clearTimeout(this.state.timer)
  }

  render() {
    var houseInfo = this.state.houseInfo
    return (
      <div className="transition-item">
        <Toast 
          show = {this.state.isShowErr}
          content = {this.state.err}/>
        <Loading show={this.state.loading} content={'数据传输中'}/>
        <Title isBack={true} title='预订房间' />
        <PickDate 
          startDate={this.state.startDate} 
          endDate={this.state.endDate} 
          afterSelect={this._getDate.bind(this)} 
          className={style.pickDate} />
        <div className={style.houseInfo}>
          <FormRow
            keyPart={'客栈信息'}
            valuePart={this.state.houseInfo.shopName}
            />
          <FormRow
            keyPart={'房间信息'}
            valuePart={this.state.houseInfo.houseName}
            />
          <FormRow
            keyPart={'预订房间数'}
            valuePart={
              <div className={style.houseNumContainer}>
                <i className={style.minus} onClick={this._addHouse.bind(this,-1)}></i>
                <span className={style.houseNum}>{this.state.houseNum}套</span>
                <i className={style.plus} onClick={this._addHouse.bind(this,1)}></i>
              </div>
            }/>
        </div>
        <div className={style.bill}>
          <FormRow
            keyPart={'订单金额'}
            valuePart={
              <div>
                <span>
                  {
                    '('+houseInfo.memTotalAmt+'元×'+this.state.houseNum+'套×'+this.state.houseDays+'晚'+')'}
                </span>
                <strong className={style.reserveStrong}>¥ {houseInfo.memTotalAmt*this.state.houseNum*this.state.houseDays}</strong>
              </div>
            }/>
        </div>
        <div className={style.btnContainer}>
          <Button 
           onClick={this._onClick.bind(this)}
           text="提交订单"/> 
        </div>
      </div>
    );
  }
}

