import React from 'react'
import { hashHistory,browserHistory } from 'react-router';
import Infinite from '../common/Infinite'
import Title from '../common/Title'
import Toast from '../common/Toast'
import Loading from '../common/Loading'
import NavBottom from '../common/NavBottom'
import { mmGet,showErr,clearTimer } from '../../utils/utils'
import style from './DiscoverList.less'

export default class DiscoverList extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      isShowErr:false,
      loading:false,
      isLogin:false,
      err:null,
      activityList:[]
    }
    this.timer = null;
  }

  _onClick(item) {
    var history = window.debug ? hashHistory : browserHistory
    history.push({
      pathname:window.url.discoverDetail,
      state: item
    })
  }

  componentWillMount() {
    var that = this
    that.setState({
      loading:true
    })

    mmGet(window.api.discoverIndex)
      .then(data => {

        that.setState({
          loading:false
        })

        if (data.code === 0) {
          that.setState({
            isLogin:data.isLogin,
            activityList: data.topicList
          })
        } else {
          showErr(that,data.message || window.err)
        }
      },err => {
        that.setState({
          loading:false
        })
        showErr(that, window.err)
      })
  }

  componentWillUnmount() {
    clearTimer(this)
  }

  _renderOne(item) {
    return (
        <div
          key = {item.id}
          className={style.item}
          style={{backgroundImage: `url("${item.imgUrl}")`}}
          onClick={this._onClick.bind(this,item)}>
          <h2><span>{item.activityName}</span></h2>
          <p>{item.title}</p>
        </div>
    )
  }

  render() {
    var that = this
    return (
      <div className={style.contentContainer}>
        <Toast
          show = {this.state.isShowErr}
          content = {this.state.err}/>
        <Loading show={this.state.loading} content={'数据传输中'}/>
        <Title isBack={false} title={'发现'} isLogin={this.state.isLogin}/>
        <NavBottom />
        {
          that.state.activityList.map(function(item){
            return that._renderOne(item)
          })
        }
      </div>

    )
  }
}
