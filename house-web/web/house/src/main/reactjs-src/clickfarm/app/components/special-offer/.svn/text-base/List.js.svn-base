import React from 'react';
import { hashHistory } from 'react-router';

import Header from './Header';
import style from './List.less';

class Row extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  render() {
    const {id, imgUrl, title, oldPrice, specialPrice} = this.props;
    return (
      <div className={style.row}>
        <div className={style.img} style={{backgroundImage:`url('${imgUrl}')`}}>
        </div>
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

export default class List extends React.PureComponent {

  constructor(props) {
    super(props);
    this.state = {
      housesList:[
        {
          id: 914,
          imgUrl: '/assets/i/clickfarm/xjw.png',
          title: '[深圳小酒窝1号店客栈]现代美式乡村风格+五星级高端客栈+休闲书吧',
          oldPrice: 299
        }
      ]
    }
  }

  _back() {
    hashHistory.push({
      pathname:'/special-offer'
    })
  }

  render() {
    return (
      <div>
        <Header back={this._back.bind(this)}/>
        {
          this.state.housesList.map((house) => {
            return <Row key={house.id} {...house}/>
          })
        }
      </div>
    );
  }
}
