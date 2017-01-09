import React from 'react'
import { Link } from 'react-router'
import { hashHistory } from 'react-router'
import Title from '../common/Title'
import Toast from '../common/Toast'
import Loading from '../common/Loading'
import style from './Success.less'
import { mmPost, getUrlParam } from '../../utils/utils'

export default class Success extends React.Component {

  constructor(props) {
    super(props);
    // var _result = getUrlParam('result') || 'error'
    var _code = getUrlParam('code')
    var _orderId = getUrlParam('orderId')
    var _money = getUrlParam('money')
    var _shopId = getUrlParam('shopId')
    this.state = {
        isShowErr:false,
        err:null,
        isLoading:false,
        result: null,
        code:_code,
        orderId: _orderId,
        money:_money,
        shopId:_shopId
    }
    this.timer = null
  }

  _showErr (err) {
    this.setState({
      isShowErr: true,
      err: err
    })
    this.timer = setTimeout(()=> {
        this.setState({isShowErr: false});
    }, 2000);
  }

  _queryOrderStatus() {
    var that = this;
    mmPost(window.api.baseUrl + window.api.queryorderstatus,{orderId:that.state.orderId,productCode:'wxpay',trade_type:'JSAPI'})
      .then(data => {
        that.setState({
          loading: false
        })
        if (data.success) {
          var code = data.data.resultCode
          // 成功
          if(code == 1 || code ==3) {
            that.setState({
              result:'success'
            })
          }
          // 失败
          else {
            that.setState({
              result:'error'
            })
          }
        } else {
          that.setState({
            result:'error'
          })
          that._showErr(data.message || '查询订单失败')
        }
      }, err=> {
        that.setState({
          loading: false
        })
        that._showErr('网络故障，请刷新页面重试')
      })
  }

  componentDidMount() {
    var that = this
    that.setState({
      loading:true
    })
    setTimeout(that._queryOrderStatus.bind(that),2000);
  }

  render() {
    return (
      <div className={style.container}>
          <Title
            isBack={false}
            isCancel={false}
            isOk={true}
            okClick = {()=>{
              window.location.href = '/index.htm'
            }}
            title='订单信息' />
          <Loading show={this.state.loading} content={'查询订单状态'}/>
          <Toast
            show = {this.state.isShowErr}
            content = {this.state.err}/>
          {
            this.state.result ?
            <div>
              <i className={
                this.state.result==='success' ? style.success : style.error
              }></i>
              <div className={style.text}>
                {
                  this.state.result === 'success' ?
                  <div>
                    订单已经支付成功
                    <div className={style.moreInfo+' '+style.orderId}> 订单编号：
                      <strong>{this.state.orderId}</strong>
                    </div>
                  </div>
                    :
                  <div>
                    订单处理失败
                    <div className={style.moreInfo}> 如有疑问请致电客服：400-996-6633</div>
                  </div>
                }
              </div>
              <div className={style.btnContainer}>
                  <div className={style.innerContainer}>
                     <button
                        onClick={() => {
                          window.location.href = '/index.htm'
                        }}
                        className={style.btnOutline}>
                          回妈妈送房首页
                       </button>
                  </div>
                  <div className={style.innerContainer}>
                  {
                    this.state.result === 'success' ?
                     <button
                      onClick={() => {
                          window.location.href = 'http://' + window.host + '?shopId=' + this.state.shopId
                        }
                      }
                     className={style.btn}>
                      继续订房
                    </button> :
                    <button
                      onClick={() => {
                          var returnUrl = encodeURIComponent('http://'+window.host+'#/payLoading/' + this.state.orderId + '/' + this.state.money + '/' + this.state.shopId)
                          //  returnUrl是我本地的一个地址
                          window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9edd5171a7a05a0a&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect"
                      }}
                     className={style.btn}>
                      重新支付
                    </button>
                  }
                  </div>
              </div>
            </div> : null
          }

      </div>
    );
  }
}
