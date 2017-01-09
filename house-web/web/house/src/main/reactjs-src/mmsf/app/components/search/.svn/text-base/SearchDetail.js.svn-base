import React from 'react'
import style from './SearchDetail.less'
import { mmGet,mmPost,showErr,clearTimer } from '../../utils/utils'
import { hashHistory,browserHistory } from 'react-router';

var location = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAB7UlEQVRYR+1W0U3DQAx9jug3bABMQJmAMgHlD5VItAMQbgPaCUiyAEFKEH+UCSgT0A2ACSjfIIxyaUTTXnpOVAkh9T5zPvv52X4O4Y8P/XF8rAH8PwZYdbfw/XkEojaArWkPTcA8hNN4ID+aVOmrSgyw5/ZBuJgJPB9rAoZPYTyQghAB0Fnz1z2AltDxELTRk7AhA+CdRiA6EwafmnFEQdKzvbECYOW2wUizNxz+yD7SpvGacEh+PFoGwg7Ac8cg7BWd8EfahLnzDCRHi0B4TEGyXxsAq5Md8MbLggNDZqw6XbBzvWj7tUv+3WsZiKUMsHJbYDzOZ09Bko9f4YovXJaAnbWxADBnRUFsfGcG8N0j/zZaIQNpz+GY/Hg465TPTxUculoxA3r+3w3oU+VTFCY36d00+KVJoMrYyn0KpqCOBuRSwDcUJt3aU6CzK5sEm8JoeVg+AdpE4oc915/uAIl5ZsMIKIyV7YEMgN4Fn6+lijcfhfkNTqO5sl2QlcKkCSX5CSRY3ISFUZOUgjGgMO7bqK8FQDPhuSMQDowBGE8UxtKVrV2IeqDAQvZHNAbRdgFEhbqLpbiMRladJphGv02ZbkdukX87llJfuwT5wwyE8wzUD16rBMVydLTKLVs2NkYq94DNYdX7NYA1Az8/jcQhuIdlWAAAAABJRU5ErkJggg=='

class SmallTitle extends React.Component {
	render () {
		return (
			<div className={style.smallTitle}>{this.props.children}</div>
		);
	}
}

export default class SearchDetail extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			hotCities: [],
			allCities: [],
			showResult: false,
			localCity: window.localCity || null
		};
		this.range = this.props.location.state || null;
		this.lastFirstLetter = '';
		this.result = null;
		this.throttleRequest = null;
		this.history = window.debug ? hashHistory : browserHistory;
		this.resHeight = null;
	}

	createComparisonFunction(propertyName) {
		return  function(object1, object2) {
				var value1 = object1[propertyName];
				var value2 = object2[propertyName];
				if(value1 < value2) {
						return -1;
				} else if (value1 > value2) {
						return 1;
				} else {
						return 0;
				}
		}
	}

	componentWillMount() {
		const that = this;

		this.throttleRequest = this._throttle(this._requestSearchResult, 500);
		// 获取当前地址
		if(!window.localCity){
			const myCity = new BMap.LocalCity();
			myCity.get(function(result) {
				window.cityName = result.name.split("市")[0];
				// 通过城市名字查id
				mmPost(window.api.getCityByName,{name: window.cityName})
					.then(data => {
						if (data && data.success) {
							const _city = {
								id : data.data.cityId,
								name: cityName
							}
							window.localCity = _city
							that.setState({
								localCity: _city
							})
						} else {

						}
					}, err => {

					});
			});
		}

		// 获取城市及热门城市
		mmGet(window.api.getCities)
			.then(data => {
				if(data && data.code === 0) {
					// 按首字母排序
					var _compareFunc = that.createComparisonFunction('firstLetter');
					var _allCities = data.result.allList.sort(_compareFunc);
					that.setState({
						hotCities: data.result.hotList,
						allCities: _allCities
					});
				}
			}, err => {

			})
	}

	componentDidMount () {
		let docHeight = document.body.clientHeight;
		let headerHeight = this.refs.header.clientHeight;
		this.resHeight = docHeight - headerHeight;
	}

	_selectCity(city) {
		this.history.push({
			pathname:window.url.searchIndex,
			state: {
				city: city
			}
		})
	}

	_throttle(fn, delay) {  
		var timer = null;  
		return function () {  
			var context = this, args = arguments;  
			clearTimeout(timer);  
			timer = setTimeout(function () {  
				fn.apply(context, args);  
			}, delay);  
		};
	}

	_requestSearchResult(keyword) {
		let that = this;
		if (!keyword || keyword.trim() === '') {
			that.setState({
				showResult: false
			})
			return;
		}

		mmGet(window.api.searchResult,{keyword: keyword})
			.then(data => {
				if (data && data.code === 0) {
					that.result = data.result
					that.setState({
						showResult: true
					})
				}
			}, err => {

			})
	}

	_search() {
		let keyword = this.refs.searchInput.value;
		this.throttleRequest(keyword);
	}

	_renderHotCitys(city) {
			return (
				<button key={city.cityId} className={style.city} onClick={() => {this._selectCity({name:city.cityName,id:city.cityId})}}>{city.cityName}</button>
		)
	}

	_clear() {
		this.refs.searchInput.value = null;
		this.setState({
			showResult: false
		});
	}

	_renderSearchResult() {
		let that = this
		let result = this.result

		return (
			<div>
				{
					// 无结果
					// !result || (!result.cities && !result.houses && !result.areas) ?
					// <div>
					// 	<SmallTitle>目的地</SmallTitle>
					// 		<a
					// 			key={Math.random().toString()}
					// 			className={style.row}>
					// 		<img src={location} />
					// 			无结果
					// 	 </a>
					// </div> : null
				}
				{
					result.cities && result.cities.length > 0 ?
					<div>
						<SmallTitle>目的地</SmallTitle>
						{
							result.cities.map(function(city) {
								return (
									<a
										key={city.id}
										className={style.row}
										onClick={()=>{that._selectCity(city)}} >
									<img src={location} />
										{city.name}
								 </a>)
							})
						}
					</div> :
					<div>
						<SmallTitle>目的地</SmallTitle>
							<a
								key={Math.random().toString()}
								className={style.row}>
							<img src={location} />
								无结果
						 </a>
					</div>
				}
				{
					result.houses && result.houses.length > 0 ?
					<div>
						<SmallTitle>民宿/客栈</SmallTitle>
						{
							result.houses.map(function(house) {
								return (
									<a
										href={'/house/toDetail.htm?houseId=' + house.id}
										key={house.id}
										className={style.row}>
										{house.name}
								 </a>)
							})
						}
					</div> :
					<div>
						<SmallTitle>民宿/客栈</SmallTitle>
							<a
								key={Math.random().toString()}
								className={style.row}>
								无结果
						 </a>
					</div>
				}
				{
					result.areas && result.areas.length > 0 ?
					<div>
						<SmallTitle>位置/景点/商圈/周边</SmallTitle>
						{
							result.areas.map(function(area) {
								return (
									<a
										href={'/house/searchHouseList.htm?cityId=' + area.parentId }
										key={area.id}
										className={style.row}>
										{area.name}
								 </a>)
							})
						}
					</div> :
					<div>
						<SmallTitle>位置/景点/商圈/周边</SmallTitle>
							<a
								key={Math.random().toString()}
								className={style.row}>
								无结果
						 </a>
					</div>
				}
			</div>
		)
	}

	_renderCityList(cities) {
		var that = this;
		var arr = [];
		cities.forEach(function(city) {
			if(that.lastFirstLetter !== city.firstLetter) {
				that.lastFirstLetter = city.firstLetter;
				arr.push(<SmallTitle key={that.lastFirstLetter}>{that.lastFirstLetter}</SmallTitle>);
			}
			arr.push(	<a key={city.cityId} className={style.row} onClick={()=>{that._selectCity({name:city.cityName,id:city.cityId})}}>
					{city.cityName}
				</a>)
		});
		return arr;
	}

	render() {
		var that = this
		return (
			<div className={style.page}>
				<header className={style.header} ref="header">
					<div className={style.inputCont}>
						<input ref="searchInput" placeholder='请输入目的地城市/客栈/位置/景点/商圈' onChange={this._search.bind(this)}/>
						<i className={style.clear} onClick={this._clear.bind(this)}></i>
					</div>
					<span onClick={() => {this.history.go(-1)}}>取消</span>
				</header>
				{
					// 查询结果
					that.state.showResult ?
					<div className={style.searchRes} style={{height:this.resHeight}}>
						{
							this._renderSearchResult()
						}
					</div>
						 :
					<div className={style.cityContainer} style={{height:this.resHeight}}>
						<SmallTitle>当前城市</SmallTitle>
						<a className={style.row} onClick={()=>{this._selectCity(this.state.localCity)}}>
							<img src={location} />
							{(this.state.localCity && this.state.localCity.name) || '定位中'}
						</a>
						<SmallTitle>热门城市</SmallTitle>
						<div className={style.hotCities}>
						{
							this.state.hotCities.map(city => {
								return that._renderHotCitys(city);
							})
						}
						</div>
						{
							this._renderCityList(this.state.allCities)
						}
					</div>
				}
			</div>
		);
	}
}
