import React from 'react';
import {hashHistory, browserHistory} from 'react-router';

import LazyLoad from '../../common/LazyLoad.min.js';
import Header from './Header';
import style from './Index.less';

import "../../../assets/i/mmsf/special-offer-99-banner.png";
import "../../../assets/i/mmsf/special-offer-99-cxzf.png";
import "../../../assets/i/mmsf/special-offer-99-gala.png";
import "../../../assets/i/mmsf/special-offer-99-index-footer.png";
import "../../../assets/i/mmsf/special-offer-99-ml3h.png";
import "../../../assets/i/mmsf/special-offer-99-nc.png";
import "../../../assets/i/mmsf/special-offer-99-xjw.png";
import "../../../assets/i/mmsf/special-offer-99-ydyx.png";
import "../../../assets/i/mmsf/special-offer-99-xjw.png";
import "../../../assets/i/mmsf/special-offer-99-ml3h.png";
import "../../../assets/i/mmsf/special-offer-99-nc.png";
import "../../../assets/i/mmsf/special-offer-99-cxzf.png";
import "../../../assets/i/mmsf/special-offer-99-gala.png";
import "../../../assets/i/mmsf/special-offer-99-ydyx.png";
import "../../../assets/i/mmsf/special-offer-99-list-613xz.png";
import "../../../assets/i/mmsf/special-offer-99-list-gala.png";
import "../../../assets/i/mmsf/special-offer-99-list-aqgy.png";
import "../../../assets/i/mmsf/special-offer-99-list-bakz.png";
import "../../../assets/i/mmsf/special-offer-99-list-cxzf2hd.png";
import "../../../assets/i/mmsf/special-offer-99-list-cxzf1hd.png";
import "../../../assets/i/mmsf/special-offer-99-list-hazx.png";
import "../../../assets/i/mmsf/special-offer-99-list-jtql.png";
import "../../../assets/i/mmsf/special-offer-99-list-lqh.png";
import "../../../assets/i/mmsf/special-offer-99-list-ml3h.png";
import "../../../assets/i/mmsf/special-offer-99-list-nxn.png";
import "../../../assets/i/mmsf/special-offer-99-list-nc.png";
import "../../../assets/i/mmsf/special-offer-99-list-swkz.png";
import "../../../assets/i/mmsf/special-offer-99-list-xmkz.png";
import "../../../assets/i/mmsf/special-offer-99-list-xyh.png";
import "../../../assets/i/mmsf/special-offer-99-list-xjw1h.png";
import "../../../assets/i/mmsf/special-offer-99-list-xjw2h.png";
import "../../../assets/i/mmsf/special-offer-99-list-ydyx1h.png";
import "../../../assets/i/mmsf/special-offer-99-list-ydyx2h.png";

class Row extends React.PureComponent {
    constructor(props) {
        super(props);
    }

    _toList() {
      // cnzz统计
      _czc.push(['_trackEvent', 'special-offer-99', 'toList', 'special-offer-99','1']);
      // 百度统计
      _hmt.push(['_trackEvent', 'special-offer-99', 'toList', 'special-offer-99', '1']);

      var history = window.debug
          ? hashHistory
          : browserHistory;
      history.push(window.url.specialOffer99List);
    }

    _toDetail(id){
      // cnzz统计
      _czc.push(['_trackEvent', 'special-offer-99', 'toDetail', 'special-offer-99','1']);
      // 百度统计
      _hmt.push(['_trackEvent', 'special-offer-99', 'toDetail', 'special-offer-99', '1']);
      window.location.href = `/house/toDetail.htm?houseId=${id}`;
    }

    render() {
        const {id, imgUrl, title, oldPrice, specialPrice} = this.props;
        return (
            <div className={style.row}>
                <div onClick={(e)=>{this._toDetail(id)}}>
                  <LazyLoad placeholder={< div className = {
                      style.img
                  }
                  style = {{backgroundImage:`url('/assets/i/mmsf/lazy_image.jpg')`}} > </div>} offsetVertical={100}>
                      <div className={style.img} style={{
                          backgroundImage: `url('${imgUrl}')`
                      }}></div>
                  </LazyLoad>
                </div>
                <div className={style.detail}>
                    <div onClick={(e)=>{this._toDetail(id)}}>
                      <h2>{title}</h2>
                      <div className={style.priceCont}>
                          ¥<strong>{specialPrice}</strong>
                          <span className={style.oldPrice}>
                              {oldPrice}
                          </span>
                          <span className={style.discount}>
                              <span>省{oldPrice - specialPrice}元</span>
                          </span>
                          <div className={style.buy}>
                              立即抢购
                          </div>
                      </div>
                    </div>
                    <div onClick={(e)=>{this._toList()}} className={style.footer}>
                        <span className={style.more}>
                            更多99暖冬特卖
                        </span>
                        <i className={style.arrow}></i>
                    </div>
                </div>
            </div>
        );
    }
}

export default class Index extends React.PureComponent {

    constructor(props) {
        super(props);
    }

    _back() {}

    _toList() {
        var history = window.debug
            ? hashHistory
            : browserHistory;
        history.push(window.url.specialOffer99List);
    }

    render() {
        return (
            <div>
                <Header noBack={true}/>
                <img className={style.banner} src="/assets/i/mmsf/special-offer-99-banner.png"/>
                <div className={style.main}>
                    {
                      window.specialOffer99Data.index.map((house) => {
                        return <Row key={house.id} specialPrice={99} {...house}/>
                      })
                    }
                    <img onClick={this._toList.bind(this)} className={style.footerImg} src="/assets/i/mmsf/special-offer-99-index-footer.png"/>
                    <div className={style.footerText}>该活动最终解释权归妈妈送房所有</div>
                </div>
            </div>
        );
    }
}
