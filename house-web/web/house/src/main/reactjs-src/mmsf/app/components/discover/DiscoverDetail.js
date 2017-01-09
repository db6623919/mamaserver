import React from 'react'
import $ from 'jquery'
import Title from '../common/Title'
import RecommendHouse from '../common/RecommendHouse'
import style from './DiscoverDetail.less'
import Infinite from '../common/Infinite'
import { mmGet,showErr,clearTimer } from '../../utils/utils'
// 这里引入只是为了使webpack将图片拷贝到正确的路径
import bannerElderly from '../../assets/i/mmsf/discover-detail-banner.png'
import iconShare from '../../assets/i/mmsf/icon-share.png'

import lazyImage from '../../assets/i/mmsf/lazy_image.jpg'
export default class DiscoverDetail extends React.Component {

  constructor(props) {
    super(props);
    // console.log(this.props.location.state)
    this.activity = this.props.location.state || {}
    this.state = {
      id:1,
      loading:true,
      houseList:[],
      hasMore:true
    }
    this.pageNum = 1
  }

  componentWillMount() {
    // 修改网页标题
    $('title').html('妈妈送房-'+this.activity.activityName)
    this.getHouseList(this.pageNum)
  }

  getHouseList() {
    var that = this
    var newState = {}
    if (!this.state.hasMore) return

    that.setState({
      loading:true
    })
    mmGet(window.api.discoverDetail,{id:this.activity.id,pageNum:this.pageNum})
      .then(data => {
        newState.loading=false
        if (data.houseList) {
          var tmp = this.state.houseList
          tmp = tmp.concat(data.houseList)
          newState.houseList = tmp
          this.pageNum += 1
        } else {
          newState.hasMore = false
        }
        that.setState(newState)
      },err => {
        that.setState({
          loading:false
        })
      })
  }

  loadMore() {
    this.getHouseList()
  }

  render() {
    return (
      <Infinite
        loading = {this.state.loading}
        hasMore = {this.state.hasMore}
        loadMore = {this.loadMore.bind(this)}
      >
        <Title isBack={true} title={this.activity.activityName} />
        <div className={style.banner+' '+style.elderly} style={{backgroundImage:`url("${this.activity.imgUrl}")`}}>
          {/*<img src={window.baseUrl + '/assets/i/mmsf/icon-share.png'} className={style.share}/>*/}
        </div>
        <div className={style.descPad}>
          <h1>{this.activity.activityName}</h1>
          <div className={style.subtitle}>{this.activity.title}</div>
          <div className={style.separator}></div>
          <p className={style.desc}>{this.activity.introduction}</p>
        </div>
        <RecommendHouse houseList={this.state.houseList}/>
      </Infinite>
    );
  }
}
