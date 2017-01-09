import React from 'react'
import style from './Test.less'

export default class List extends React.Component {


  constructor(props) {
    super(props);
  }

  componentDidMount () {

  }

  tdClick(e) {
    console.log(e)
  }

  trClick(e) {
    console.log(e)
  }

  render() {
    return (
      <table>
        <tr onClick={this.trClick.bind(this)}>
          <td onClick={this.tdClick.bind(this)}>1</td>
          <td onClick={this.tdClick.bind(this)}>1</td>
        </tr>
        <tr onClick={this.trClick.bind(this)}>
          <td onClick={this.tdClick.bind(this)}>2</td>
          <td onClick={this.tdClick.bind(this)}>2</td>
        </tr>
      </table>
    );
  }
}
