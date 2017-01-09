import React from 'react'
import LoadingAnimation from './LoadingAnimation'
import style from './Loading.less'

export default class Loading extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
    		<div className={style.container} style={{display: this.props.show ? 'block' : 'none'}}>
            <div className={style.loadingContainer}>
              <LoadingAnimation />
              <span className={style.text}>{this.props.content || '数据加载中'}</span>
            </div>
    		</div>
    );
  }
}
