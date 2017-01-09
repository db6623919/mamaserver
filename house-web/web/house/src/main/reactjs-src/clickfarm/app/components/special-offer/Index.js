import React from 'react';
import { hashHistory } from 'react-router';

import LazyLoad from '../common/LazyLoad.min.js';
import Header from './Header';
import style from './Index.less';

class Row extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    const {id, imgUrl, title, oldPrice, specialPrice} = this.props;
    return (
      <div className={style.row}>

        <LazyLoad
          placeholder={
            <div className={style.img} style={{backgroundImage:`url('/assets/i/clickfarm/lazy_image.jpg')`}}>
            </div>
          }
          offsetVertical={100}
          >
          <div className={style.img} style={{backgroundImage:`url('${imgUrl}')`}}>
          </div>
        </LazyLoad>
        <div className={style.detail}>
          <h2>{title}</h2>
          <div className={style.priceCont}>
            ¥<strong>{specialPrice}</strong>
            <span className={style.oldPrice}>
              {oldPrice}
            </span>
            <span className={style.discount}>
              <span>省{oldPrice-specialPrice}元</span>
            </span>
          </div>
          <div className={style.footer}>
            <span className={style.more}>
              更多99暖冬特卖
            </span>
            <a className={style.buy}>
              立即抢购
            </a>
          </div>
        </div>
      </div>
    );
  }
}

export default class Index extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      housesList: [{
        id: 914,
        imgUrl: '/assets/i/clickfarm/xjw.png',
        title: '[深圳小酒窝1号店客栈]现代美式乡村风格+五星级高端客栈+休闲书吧',
        oldPrice: 299
      },{
        id: 891,
        imgUrl: '/assets/i/clickfarm/ml3h.png',
        title: '[深圳较场尾蜜里3号店客栈]无敌海景房+单车、麻将、咖啡、烧烤等娱乐休闲设施',
        oldPrice: 285
      },{
        id: 904,
        imgUrl: '/assets/i/clickfarm/nc.png',
        title: '[深圳较场尾鸟巢海边客栈]VIP麻将房+自助式厨房+多功能西餐厅',
        oldPrice: 248
      },{
        id: 893,
        imgUrl: '/assets/i/clickfarm/cxzf.png',
        title: '[深圳大鹏所城城祥左府客栈]地中海风情+距离大鹏所城5米+酒吧+花茶吧',
        oldPrice: 299
      },{
        id: 896,
        imgUrl: '/assets/i/clickfarm/gala.png',
        title: '[深圳官湖gala空间客栈]北欧现代时尚风格+步行至海滩1分钟+窑鸡餐厅',
        oldPrice: 235
      },{
        id: 913,
        imgUrl: '/assets/i/clickfarm/ydyx.png',
        title: '[深圳较场尾雅典印象2号店客栈]西班牙浪漫风格+意大利咖啡+进口啤酒',
        oldPrice: 225
      }]
    }
  }

  _back() {

  }

  _toList() {
    hashHistory.push('/special-offer/list');
  }

  render() {
    return (
      <div>
        <Header />
        <img className={style.banner} src="/assets/i/clickfarm/banner.png"/>
        <div className={style.main}>
          {
            this.state.housesList.map((house) => {
              return <Row key={house.id} specialPrice={99} {...house}/>
            })
          }
          <img onClick={this._toList.bind(this)} className={style.footerImg} src="/assets/i/clickfarm/footer.png"/>
          <div className={style.footerText}>该活动最终解释权归妈妈送房所有</div>
        </div>
      </div>
    );
  }
}
