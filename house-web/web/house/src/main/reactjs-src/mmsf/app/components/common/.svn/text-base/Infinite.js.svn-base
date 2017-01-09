import React from 'react'
import ReactDOM from 'react-dom'
import LoadingAnimation from './LoadingAnimation.js'
import style from './Infinite.less'

export default class Infinite extends React.Component {
	propTypes: {
		loadMore: React.PropTypes.func.isRequired,
		loading: React.PropTypes.func.isRequired,
		threshold: React.PropTypes.number,
		hasMore: React.PropTypes.bool
	}

  constructor(props) {
    super(props);
    this.threshold = this.props.threshold || 10;
    this.el = null;
  }

  componentDidMount() {
  		this.el = ReactDOM.findDOMNode(this.refs.scroll);
  		this.attachScrollListener();
  }

  scrollListener () {
    if (this.props.loading) return;
    // 滚动到距离列表底部还差threshold的时候触发
    if ((this.el.scrollHeight - this.threshold) <= (this.el.scrollTop + this.el.offsetHeight)) {
      this.props.loadMore();
    }
  }

  attachScrollListener () {
     if (!this.props.hasMore) {
       return;
     }
     this.el.addEventListener('scroll', this.scrollListener.bind(this));
     window.addEventListener('resize', this.scrollListener.bind(this));
   }

   renderFooter() {
      if (this.props.loading && this.props.hasMore) {
        return  <LoadingAnimation color={'#ff6f5c'} />
      }
      if (!this.props.hasMore) {
        return  <span className={style.noData}>没有更多数据</span>
      }
   }

  render() {
    return (
    	<div ref='scroll' className={style.scrollContainer}>
    		{this.props.children}
    		{
              this.renderFooter()
    		}
    	</div>
    )
  }
}
