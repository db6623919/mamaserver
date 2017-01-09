import React from 'react'
import { hashHistory } from 'react-router'

import FormRow from '../common/FormRow'
import Title from '../common/Title'
import Toast from '../common/Toast'
import moment from 'moment'
import Button from '../common/Button'
import { mmPost,getUrlParam } from '../../utils/utils'
import style from './Order.less'

export default class Order extends React.Component {

  constructor(props) {
    super(props);
    // 获取上一个页面传递过来的参数
    var _orderInfo = this.props.location.state || {
            houseInfo: {
                        shopName : 'f',
                        shopId : 1,
                        houseId : 1,
                        area : 1,
                        bedType : 1,
                        houseName : '3',
                        memTotalAmt : 1,
                        personNum : 1
            },
            startDate: '33',
            endDate: '33',
            days: 1,
            houseNum: 1,
            orderId: 1,
            oldPrice: 1,
            payAmt:1
    }

    this.state = {
        isShowErr:false,
        err: null,
        showModal:false,
        orderInfo: _orderInfo
    }

    this.act = 'add'
    this.timer = null
  }

  // 显示错误
  _showErr (err) {
    var that = this
    this.setState({
        isShowErr:true,
        err:err
      })
    this.timer = setTimeout(function() {
      that.setState({
        isShowErr:false
      })
    },1500)
  }

  // 支付,跳转到原有的支付页面
  _onClick() {
    var _orderInfo = this.state.orderInfo
    var returnUrl = encodeURIComponent('http://'+window.host+'#/payLoading/' + _orderInfo.orderId + '/' + _orderInfo.payAmt + '/' + _orderInfo.houseInfo.shopId)
    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9edd5171a7a05a0a&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect"
  }

  // 新金额
  _submitNewPrice() {
    var that = this
    that.setState({
        loading:true
    })

    // 校验
    var price = that.refs.newPrice.value
    if (!price) {
        that._showErr('金额不能为空')
        return ;
    }
    if (price == 0) {
        that._showErr('金额不能为0')
        return ;
    }

    that.setState({
        showModal:false
    })
    // 提交新金额
    var params = {
        houseId: that.state.orderInfo.houseInfo.houseId,
        shopId: that.state.orderInfo.houseInfo.shopId,
        payPrice:price,
        act:this.act
    }
    if (this.act === 'edit') {
        params.orderId = this.state.orderInfo.orderId
    }
     mmPost(window.api.baseUrl + window.api.submitOrder,params)
        .then(data => {
          that.setState({
            loading: false
          })
          if ((data.code === 0 || data.code === '0') ) {
            that.act = 'edit'
            var newState = that.state.orderInfo
            newState.oldPrice = price
            newState.payAmt = data.payAmt
            that.setState({
                orderInfo: newState
            })
          } else {
            that._showErr(data.message || window.err)
          }
        }, err=> {
          that.setState({
            loading: false
          })
          that._showErr(window.err)
        })
  }

  render() {
    const orderInfo = this.state.orderInfo
    return (
      <div> 
        <Toast 
          show = {this.state.isShowErr}
          content = {this.state.err}/>
        <Title isBack={true} title='订单支付' />
        <h2 className={style.h2}>订房信息</h2>
        <FormRow
            keyPart={'客栈'}
            valuePart={orderInfo.houseInfo.shopName || ''}
            />	
        <FormRow
            keyPart={'房型'}
            valuePart={orderInfo.houseInfo.houseName || ''}
            />
        <FormRow
            keyPart={'入住-退房时间'}
            valuePart={
                <span>
                     { orderInfo.startDate+'-'+orderInfo.endDate +' ' }
                     <strong>{ orderInfo.days+'晚'}</strong>
                </span>
              
            }
            />	
        <FormRow
            keyPart={'预定房间数'}
            valuePart={
                <strong>{ orderInfo.houseNum+'套'}</strong>
            }
            />	
        <h2 className={style.h2}>订单信息</h2>
        <FormRow
            keyPart={'订单编号'}
            valuePart={orderInfo.orderId}
            />	        
        <FormRow
            keyPart={'订单金额'}
            valuePart={
                <span>                    
                    <button className={style.modify} onClick={(e)=>{this.setState({showModal:true})}}>修改</button>
                    {'¥'+orderInfo.oldPrice}
                </span>
            }
            />
        <FormRow
            keyPart={'优惠金额'}
            valuePart={
                    
            	'¥'+(orderInfo.oldPrice-orderInfo.payAmt)
            	// <span className={style.discountContainer}>
            	// 	 详情询问客栈老板
            	// 	 <i className={style.arrow}></i>
            	// </span>
            }/>
        <FormRow
            keyPart={
            	<span className={style.highlight}>支付金额</span>
            }
            valuePart={
            	<strong>{'¥'+orderInfo.payAmt}</strong>
            }/>
        <h2 className={style.h2}>支付方式</h2>
        <FormRow
            keyPart={
            	<span>
            		<i className={style.weixin}></i>
            		微信支付
            	</span>
            }
            valuePart={
            	<i className={style.check}></i>
            }/>
   		<div className={style.btnContainer}>
          <Button 
          	onClick={this._onClick.bind(this)}
          	text="确认支付"/> 
        </div>
        {
	this.state.showModal ?
            <div>
                <div className={style.mask}></div>
                <div className={style.modalContainer}>
                  <input ref='newPrice' type="tel" placeholder='输入新金额'/>
                  <button className={style.ok} onClick={this._submitNewPrice.bind(this)}>确认</button>
                  <button className={style.cancel} onClick={()=>{this.setState({showModal:false})}}>取消</button>
                </div>
            </div>
            :
	null
        }

      </div>
    );
  }
}

