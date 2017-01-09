import React from 'react'
import { Link,hashHistory } from 'react-router';

import style from './Button.less'

export default class Button extends React.Component {
  constructor(props) {
    super(props);
  }

  // shouldComponentUpdate(newProps) {
  //   return this.props.disabled
  // }

  render() {
    return (
      <button
        disabled={this.props.disabled ? 'disabled' : null}
        onClick={this.props.onClick}
        className={style.btn}>
      {this.props.children}
      </button>
    );
  }
}
