import React from 'react'
import { hashHistory } from 'react-router'

import Loading from '../common/Loading'
import queryString from 'query-string'
import { mmPost,getUrlParam } from '../../utils/utils'

/**
 *  微信授权后的跳转地址
 */
export default class PayLoading extends React.Component {

  constructor(props) {
    super(props);
    // 重新支付时，参数会从结果页面传进来
    if (this.props.location && this.props.location.state) {
      this.code = this.props.location.state.code 
      this.orderId = this.props.location.state.orderId
      this.money = this.props.location.state.money
      this.shopId = this.props.location.state.shopId
   }
    this.state = {
    	isLoading:true,
    	isShowErr: false,
    	err:null
    }
  }

    _showErr (err) {
      this.setState({
        isShowErr: true,
        err: err
      })
      this.state.timer = setTimeout(()=> {
          this.setState({isShowErr: false});
      }, 2000);
  }

  componentDidMount () {
      var code
      if (this.code) {
        code = this.code
      } else {
        var _codeFromSearch = queryString.parse(window.location.search.split('?')[1]).code
        if(_codeFromSearch) {
          code = _codeFromSearch
        } else {
          var _codeFromHash = queryString.parse(window.location.hash.split('?')[1]).code
          if (_codeFromHash) {
            code = _codeFromHash
          }
        }
      } 

	var orderId = this.orderId || this.props.params.orderId
	var money = this.money || this.props.params.amt
	var shopId = this.shopId || this.props.params.shopId
      //
	window.location.replace(window.api.baseUrl+window.api.queryopenid+'?'+queryString.stringify({code:code,orderId:orderId,money:money,shopId:shopId}))
  }

  render() {
    return (
      <div> 
      	{
      		this.state.isLoading ? 
        		<Loading show={this.state.isLoading} content={'数据加载中'}/>:
        		null
      	}
      </div>
    );
  }
}

