import React from 'react'
import style from './Loading.less'

export default class Loading extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
    		<div className={style.container} style={{display: this.props.show ? 'block' : 'none'}}>
            <div className={style.loadingContainer}>
              <div className={style.spinner}>
                  <div className={style.rect1}></div>
                  <div className={style.rect2}></div>
                  <div className={style.rect3}></div>
                  <div className={style.rect4}></div>
                  <div className={style.rect5}></div>
              </div>
              <span className={style.text}>{this.props.content || '数据加载中'}</span>
            </div>                                
    		</div>
    );
  }
}