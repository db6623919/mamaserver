import React from 'react'
import { Link } from 'react-router'
import { hashHistory } from 'react-router'
import Title from '../common/Title'
import style from './Success.less'
import { getUrlParam } from '../../utils/utils'

export default class Success extends React.Component {

  constructor(props) {
    super(props);
    var _result = getUrlParam('result') || 'error'
    var _code = getUrlParam('code')
    var _orderId = getUrlParam('orderId')
    var _money = getUrlParam('money')
    var _shopId = getUrlParam('shopId')
    this.state = {
        result:_result,
        code:_code,
        orderId:_orderId,
        money:_money,
        shopId:_shopId
    }
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
          <i className={
            this.state.result==='success' ? style.success : style.error
          }></i>
          <div className={style.text}>{
            this.state.result === 'success' ? 
            <span>订单已经支付成功 </span> : 
            <div> 
              订单处理失败
              <div className={style.phone}> 如有疑问请致电客服：400-996-6633</div>
            </div>
          }</div>
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
      </div>
    );
  }
}

