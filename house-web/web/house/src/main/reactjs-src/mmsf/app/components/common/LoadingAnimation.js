import React from 'react'
import style from './LoadingAnimation.less'

export default class LoadingAnimation extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
  	var color = this.props.color || '#fff'
    return (
              <div className={style.spinner}>
                  <div style={{backgroundColor:color}} className={style.rect1}></div>
                  <div style={{backgroundColor:color}} className={style.rect2}></div>
                  <div style={{backgroundColor:color}} className={style.rect3}></div>
                  <div style={{backgroundColor:color}} className={style.rect4}></div>
                  <div style={{backgroundColor:color}} className={style.rect5}></div>
              </div>
    );
  }
}
