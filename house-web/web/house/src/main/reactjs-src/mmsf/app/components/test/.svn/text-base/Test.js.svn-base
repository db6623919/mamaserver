import React from 'react'

var Son = React.createClass({
  getDefaultProps() {
    console.log('Son getDefaultProps');
  },

  getInitialState() {
    console.log('Son getInitialState');
    this.obj = this.props.obj;
    return {
      num:this.props.num,
      obj:this.props.obj
    };
  },

  componentWillMount() {
    console.log('Son componentWillMount');
  },

  componentDidMount() {
    console.log('Son componentDidMount');
  },

  componentWillReceiveProps(newProps) {
    console.log(newProps);
    console.log('Son componentWillReciveProps');
  },

  shouldComponentUpdate() {
    console.log('Son shouldComponentUpdate');
    return true;
  },

  componentWillUpdate() {
    console.log('Son componentWillUpdate');
  },

  componentDidUpdate() {
      console.log("Son componentDidUpdate");
  },

  render() {
    console.log('Son render');
    return (
      <div>
        Son Component
        <button onClick={this.props.changeParent}>点击改变父组件的props</button>
        {--this.state.obj.money}
      </div>
    );
  }
})

var Test = React.createClass({
  getDefaultProps() {
    console.log('Test getDefaultProps');
  },

  getInitialState() {
    console.log('Test getInitialState');
    return {
      num:0,
      obj:{
        money:100
      }
    };
  },

  componentWillMount() {
    console.log('Test componentWillMount');
  },

  componentDidMount() {
    console.log('Test componentDidMount');
  },

  componentWillReceiveProps(newProps) {
    console.log('Test componentWillReciveProps');
  },

  shouldComponentUpdate() {
    console.log('Test shouldComponentUpdate');
    return true;
  },

  componentWillUpdate() {
    console.log('Test componentWillUpdate');
  },

  componentDidUpdate() {
      console.log("Test componentDidUpdate");
  },

  render() {
    console.log('Test render');
    return (
      <div>
        Test Component
        <button onClick={()=>{this.setState({num:++this.state.num});}}>点击</button>
        <Son obj={this.state.obj} num={this.state.num} changeParent={()=>{this.setState({num:++this.state.num});}}/>
        <Son obj={this.state.obj} num={this.state.num} changeParent={()=>{this.setState({num:++this.state.num});}}/>
      </div>
    );
  }
})

module.exports = Test;
