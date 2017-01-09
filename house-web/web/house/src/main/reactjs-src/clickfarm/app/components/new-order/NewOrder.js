import React from 'react'
import FormRow from '../common/FormRow'
import Toast from '../common/Toast'
import Loading from '../common/Loading'
import { getUrlParam,mmPost,mmGet } from '../../utils/utils'
import { hashHistory } from 'react-router';
import style from './NewOrder.less'

export default class NewOrder extends React.Component {
  constructor(props) {

    super(props);
    this.state = {
      disableSubmit: true,
      shopName:null,
      isShowErr: false,
      err: '',
      cutMoney:'',
      inputMoney:'',
      payMoney:'',
    }

    this.oldPrice = null
    this.shopId = null
    this.timer = null
    this.act = 'add'
    this.orderId = null
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

  componentWillMount() {
    var that = this;

    that.setState({
      loading: true
    });
    this.shopId = parseInt(getUrlParam('shopId'));

    mmGet(window.api.baseUrl + window.api.getHouseShop,{shopId:this.shopId})
      .then(data => {
        that.setState({
          loading: false
        });
        if ((data.code === 0 || data.code === '0') ) {
          that.setState({
            shopName: data.result
          });
        } else {
          that._showErr(data.message || window.err);
        }
      }, err=> {
        that.setState({
          loading: false
        });
        that._showErr(window.err);
      })
  }

  _submit () {
    var returnUrl = encodeURIComponent('http://'+window.host+'#/payLoading/' + this.orderId + '/' + this.state.payMoney + '/' + this.shopId)
    window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9edd5171a7a05a0a&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect"
  }

  _ok() {
    var that = this

    // 校验
    var price = that.refs.newPrice.value
    if (!price) {
        that._showErr('金额最低为一元')
        return ;
    }
    if (price == 0) {
        that._showErr('金额最低为一元')
        return ;
    }


    that.setState({
        inputMoney: price,
        loading:true
    })
    // 提交新金额
    var params = {
        houseId:0,
        shopId: this.shopId,
        payPrice:price,
        act:this.act
    }
    if (this.act === 'edit') {
        params.orderId = this.orderId
    }
     mmPost(window.api.baseUrl + window.api.submitOrder,params)
        .then(data => {
          that.setState({
            loading: false
          })
          if ((data.code === 0 || data.code === '0') ) {
            that.act = 'edit'
            var _cutMoney = that.state.inputMoney - data.payAmt
            that.setState({
                payMoney:data.payAmt,
                cutMoney: _cutMoney,
                disableSubmit: false
            })
            if (data.orderId) {
                 that.orderId = data.orderId
            }
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

  _onChange() {
    var that = this
    var price = this.refs.newPrice.value;
    if (price !== this.oldPrice) {
      that.setState({
        disableSubmit: true
      })
    }
  }

  render() {
    return (
      <div>
        <Toast
          show = {this.state.isShowErr}
          content = {this.state.err}/>
        <Loading show={this.state.loading} content={'数据传输中'}/>
          <div>
            <h2 className={style.shopName}>{this.state.shopName}</h2>
            <FormRow
                keyPart={'订单金额：'}
                valuePart={
                  <div>
                    <input onChange={this._onChange.bind(this)} ref="newPrice" className={style.input} type="tel" placeholder='请输入订单金额'/>
                    <button className={style.ok} onClick={this._ok.bind(this)}>确定</button>
                  </div>
                }
                />
            <FormRow
                keyPart={'优惠金额：'}
                valuePart={'¥'+(this.state.cutMoney)}
                />
            <FormRow
                keyPart={
                  <span className={style.highlight}>支付金额</span>
                }
                valuePart={
                  <strong>{'¥'+this.state.payMoney}</strong>
                }/>
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
                {
                  this.state.disableSubmit ?
                  <button disabled={true} className={style.submit} onClick={this._submit.bind(this)}>去支付</button>:
                  <button className={style.submit} onClick={this._submit.bind(this)}>去支付</button>
                }
          </div>
      </div>
    );
  }
}
