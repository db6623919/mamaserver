import React from 'react'
import { hashHistory } from 'react-router';
import style from './Toast.less'

export default class Title extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	isShow: true
    }
  }

  // componentDidMount() {
  // 		var that = this
  // 		setTimeout(function() {
	 //  		that.setState({
	 //  			isShow: false
	 //  		})
  // 		},1500)
  // }

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

