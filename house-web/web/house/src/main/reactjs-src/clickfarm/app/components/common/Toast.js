import React from 'react'
import style from './Toast.less'

export default class Title extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	isShow: true
    }
  }

  render() {
    const {content,show} = this.props
    return (
		<div className={style.modal}  style={{display: show ? 'block' : 'none'}}>
	    		<div className={style.mask}></div>
	    		<div className={style.dialog}>{content}</div>
	    	</div>
    );
  }
}

