import React from 'react'
import { hashHistory,browserHistory } from 'react-router';
import moment from 'moment'
import style from './SearchIndex.less'
import PickDate from '../common/PickDate'
import Button from '../common/Button'
import Title from '../common/Title'
import NavBottom from '../common/NavBottom'
import RecommendHouse from '../common/RecommendHouse'
import { mmGet } from '../../utils/utils'
// 这里引入只是为了使webpack打包
import searchBanner from '../../assets/i/mmsf/search-banner.png'
var arrowIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAABAElEQVRYR+3WQQ6CMBAF0DYDe24gN1CXhATpSfRoehKQDUvxBh4B1nSCaVISFi6YdgIxKWuS//rboUix8yN3zhcBEBr4rwaqqkoA4IKIT6VUzzFBpAaaprlLKa/TNPUAoPI873wRTgATahBxHJ+zLPv4IEgAswVRFNVCiKMN7bTWymc7SAATyo0gA7gRTgBOhDOAC+EFWCDMOB7sdDyKoritnQxvQNu26TiOLyllsjnATkQlhDjZFb+11iVlLJ0b4Ag3aCcAV7gTgDOcDOAOJwPm29Ce9gERU8qB+zWapDOwuI4HACg3v47tD0mJiLXvyuc2SA2s/bpR3guA0EBo4AsHe6QhFmUWWQAAAABJRU5ErkJggg=='

export default class SearchIndex extends React.Component {
  constructor(props) {
  	super(props);
    let locationState = this.props.location.state;
    let city = (locationState && locationState.city) || {};
    let startDateCached = sessionStorage.getItem("startDate");
    let endDateCached = sessionStorage.getItem("endDate");
    let _startDate = (startDateCached && moment(startDateCached,'x')) || moment(0,'HH'),
       _endDate = (endDateCached && moment(endDateCached,'x')) || moment(0,'HH').add(1,'days');

   	this.state = {
      city: city,
      startDate:_startDate,
      endDate:_endDate,
      houseList:[],
      days:0
		};
  }

  componentWillMount() {
    var that = this;
    mmGet(window.api.recommendList)
      .then(data => {
        if (data && data.code === 0) {
          that.setState({
            houseList : data.result
          })
        } else {

        }
      }, err => {

      });
  }

   // 得到日期选择组件中的日期
  _getDate (startDate,endDate) {
    sessionStorage.setItem("startDate",startDate.format('x'));
    sessionStorage.setItem("endDate",endDate.format('x'));

    var _days = endDate.diff(startDate,"days");
    this.setState({
      startDate:startDate,
      endDate:endDate,
      houseDays:_days
    });
  }

  // 跳转到城市搜索页面
  _toSearchDetail() {
    var history = window.debug ? hashHistory : browserHistory;
    history.push({
      pathname:window.url.searchDetail
    });
  }

  _toSearchRes() {
    let checkin = this.state.startDate.format('x');
    let checkout = this.state.endDate.format('x');
    location.href = '/house/searchHouseList.htm?'+`cityId=${this.state.city.id}&name=`+encodeURIComponent(this.state.city.name)+`&checkinDate=${checkin}&checkoutDate=${checkout}`;
  }

  render() {
    return (
	    <div className={style.contentContainer}>
	    	<Title title='搜索' isLogin={false}/>
        <NavBottom />
	    	<div className={style.banner}>
	    		<div className={style.pad}>
	    			<div className={style.destination} onClick={this._toSearchDetail.bind(this)}>
	    				{this.state.city.name || '目的地城市/客栈/位置/景点/商圈'}
	    				<img src = {arrowIcon} className={style.arrow}/>
	    			</div>
	    			<PickDate
                width = {this.width}
   			     	  startDate={this.state.startDate}
        		 		endDate={this.state.endDate}
          			afterSelect={this._getDate.bind(this)}
          			className={style.pickDate} />
              <div className={style.btnCont} onClick={this._toSearchRes.bind(this)}>
	        			<Button disabled={!this.state.city.id ? 'disabled' : null}>搜索</Button>
        			</div>
	    		</div>
	    	</div>
        <div className={style.recommendCont}>
        	<RecommendHouse houseList={this.state.houseList}/>
        </div>
	    </div>
    );
  }
}
