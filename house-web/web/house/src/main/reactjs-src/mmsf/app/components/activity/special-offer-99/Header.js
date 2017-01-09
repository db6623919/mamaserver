import React from 'react';
import style from './Header.less';

export default class Header extends React.PureComponent {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <header className={style.header}>
        {
          !this.props.noBack ?
          <i className={style.back} onClick={this.props.back}></i>
          : null
        }
        妈妈美宿19家精美客栈
      </header>
    );
  }
}
