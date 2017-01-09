import React from 'react';
import {hashHistory, browserHistory} from 'react-router';

import LazyLoad from '../../common/LazyLoad.min.js';
import Header from './Header';
import style from './List.less';

class Item extends React.PureComponent {
    constructor(props) {
        super(props);
    }

    _toDetail(id){
      // cnzz统计
      _czc.push(['_trackEvent', 'special-offer-99', 'toDetail', 'special-offer-99','1']);
      // 百度统计
      _hmt.push(['_trackEvent', 'special-offer-99', 'toDetail', 'special-offer-99', '1']);
      window.location.href = `/house/toDetail.htm?houseId=${id}`;
    }

    render() {
        const {id, imgUrl, title, discount, isOdd} = this.props;

        return (
            <a onClick={(e)=>{this._toDetail(id)}} className={style.item + ' ' + (isOdd
                ? style.odd
                : style.even)}>
                <LazyLoad placeholder={< img className = {
                    style.img
                }
                src = '/assets/i/mmsf/lazy_image.jpg' />} offsetVertical={100}>
                    <img className={style.img} src={imgUrl}/>
                </LazyLoad>
                <div className={style.intro}>
                    <h2>{title}</h2>
                    <div className={style.priceCont}>
                        ¥<strong>99</strong>
                        <span className={style.discount}>省{discount}</span>
                    </div>
                </div>
            </a>
        );
    }
}

export default class List extends React.PureComponent {

    constructor(props) {
        super(props);
    }

    _back() {
        var history = window.debug
            ? hashHistory
            : browserHistory;
        history.push({pathname: window.url.specialOffer99Index});
    }

    componentDidMount() {
        window.scrollTo(0, 0);
    }

    render() {
        return (
            <div>
                <Header back={this._back.bind(this)}/>
                <div className={style.page}>
                    {window.specialOffer99Data.list.map((house, index) => {
                        let isOdd = (index + 1) % 2 === 0
                            ? false
                            : true;
                        return <Item key={house.id} {...house} isOdd={isOdd}/>
                    })
                    }
                </div>
            </div>
        );
    }
}
