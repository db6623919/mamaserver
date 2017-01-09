import React from 'react'
import { hashHistory } from 'react-router'
import style from './RecommendHouse.less'
import LazyLoad from './LazyLoad.min.js'
import lazyImage from '../../assets/i/mmsf/lazy_image.jpg'
var partnershipIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACEAAAAqCAYAAAA9IzKsAAAABGdBTUEAALGPC/xhBQAAAXVJREFUWAntljFLw0AYhr0iUrA4uhY3aUEHRR0c3F11s5NQUZf+BX+D3cXVQXBxEJz8E1bEpfQPqHPj+x5+JRRzFJvcmyEfvNzlcsn75PsuubiF30iSZBPdbejNOfdi49FaAFxBY8jiDh0XE6ANwzSAgRzHgqjBaBf666n3YkLUM8yyxjOm/394MXBpC3XpBs7ndWoUgjiAC1V09LgmlPEN8xs1xC2+SZ9KiARZuGYZlBBPyMJADeGzoIR4h/kjARiqcvRRCq4JHwoI/1oaAFsFhH8tlRAsQT8NwH7sTAywFl7VEOvYFFtqCPpflAGig2w00iCx1wS9V6ATNQT9z8sAsYGS7BuIohzmPcmGEuII2VglkRJiCf6nagj6nyEbNWUmCNGEDkO//CNM4M9H0bEVgnjAZnNZNAHvry6Hf8YKwkpdZaLKhGXA2tKsiaERTbVZ41PTcjjEBrIMfUDp+MLBWg63n/0WNITuoSH0DO3MfvX8M38AFPKP460H6coAAAAASUVORK5CYII='

export default class RecommendHouse extends React.Component {
  constructor(props) {
    super(props);
  }

  renderOne(item) {
    if(item.imageUrl && item.imageUrl.indexOf('!h5i6s') < 0) {
      item.imageUrl = item.imageUrl + '!h5i6s';
    }
  	return (
  			<div key={item.houseId} className={style.item}>
  			     <a className={style.toSpecial} href={`/house/toDetail.htm?houseId=${item.houseId}`} >
                  <LazyLoad
                    placeholder={<img className={style.placeholder} src={'/assets/i/mmsf/lazy_image.jpg'} />}

                    offsetVertical={100}
                    >
                    <img src={item.imageUrl}/>
      				    </LazyLoad>
                  {
                    item.specialTag ?
                    <span className={style.partnership}>
                      <img src={partnershipIcon} />
                      {item.specialTag}
                    </span>:null
                  }
              </a>
  			     <div className={style.title}>
      					<h3>{item.name}</h3>
                {
                  item.marketPrice > 0 ?
                  <span>市场价：¥{item.marketPrice}起/晚</span> :
                  null
                }
      				</div>
      				<div className={style.desc}>
      				<ul className={style.info}>
                              {
                                // <li>交通便利</li>
                                // <li>全套家电</li>
                                // <li>交通便利</li>
                              }
                              {item.description}
      				</ul>
       					<span className={style.discountCont}>
                  {
                    item.discountDescription ?
                    <i>{item.discountDescription}</i> : null
                  }
      						送房价:
      						<span className={style.specialPrice}>¥<strong>{item.price}</strong></span>
      						起/晚
      					</span>
      				</div>
  		</div>
  	)
  }

  render() {
    var that = this
    return (
      <div className={style.container}>
      <h2>推荐客栈</h2>
       {
          that.props.houseList.map(function(item){
            return that.renderOne(item)
          })
       }
      </div>
    );
  }
}
