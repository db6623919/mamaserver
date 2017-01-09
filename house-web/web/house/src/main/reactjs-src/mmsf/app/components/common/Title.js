import React from 'react'
import { hashHistory } from 'react-router';
import Nav from './Nav.js'
import style from './Title.less'

export default class Title extends React.Component {
  constructor(props) {
    super(props);
  }

  _back() {
    hashHistory.goBack();
  }

  render() {
    return (
      <header className={style.header}>
        { 
          this.props.isBack ?
          <i className={style.back} onClick={this._back.bind(this)}></i> :
          null
        }
        {
          this.props.isCancel ?
          <span className={style.cancel} onClick={this.props.cancel}>取消</span> :
          null
        }
        {
          this.props.isOk ?
          <span className={style.ok} onClick={this.props.okClick}>完成</span> :
          null
        }
        {
          this.props.isNav ?
          <Nav isLogin={this.props.isLogin}/> :
          null
        }
        {this.props.title}
      </header>
    );
  }
}

