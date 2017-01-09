import React from 'react'
import { Link,hashHistory } from 'react-router';
import Button from '../common/Button'
import style from './Row.less'

export default class Row extends React.Component {
  constructor(props) {
    super(props);
  }

  _onClick() {
    hashHistory.push({
      pathname:'/reserve',
      state: this.props
    })
  }

  render() {
    return (
        <div>
          	<h2 className={style.h2}>{this.props.houseName}</h2>	
           <div>
           	<div className={style.col}>
  	           	<span className={style.key}>宜住&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
  	           	<span className={style.value}>{this.props.personNum}人</span>
           	</div>
	           <div className={style.col}>
  	           	<span className={style.key}>床型</span>
  	           	<span className={style.value}>{this.props.bedType}</span>
           	</div>
           </div>
           <div>
           	<div className={style.col}>
  	           	<span className={style.key}>房间面积</span>
  	           	<span className={style.value}>{this.props.area}m<sup className={style.sup}>2</sup></span>
           	</div>
	           <div className={style.col}>
  	           	<span className={style.key}>现价</span>
  	           	<span className={style.value +' '+ style.highlight}>¥{this.props.memTotalAmt}/晚</span>
           	</div>
           </div>
           <div className={style.btnContainer}>
              {
                this.props.isFull ?
                <Button disabled={true} text="满房"/> :
                <Button 
                  disabled={false} 
                  onClick={this._onClick.bind(this)}
                  text="立即预订"/> 
              }
           </div>
        </div>
    );
  }
}