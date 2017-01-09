import React from 'react'
import { DateRange } from 'react-date-range-ch'
// import { DateRange } from './lib/index.js'
import $ from 'jquery'
import moment from 'moment'
import style from './PickDate.less'

var monthDict = {
  'january':'01月',
  'february':'02月',
  'march':'03月',
  'april':'04月',
  'may':'05月',
  'june':'06月',
  'july':'07月',
  'august':'08月',
  'september':'09月',
  'october':'10月',
  'november':'11月',
  'december':'12月'
}

var dateDict ={
  'monday':'周一',
  'tuesday':'周二',
  'wednesday':'周三',
  'thursday':'周四',
  'friday':'周五',
  'saturday':'周六',
  'sunday':'周日'
}

export default class PickDate extends React.Component {

  constructor(props) {
    super(props);
    // 得到当前时间
    var  todayMomemt = this.props.startDate,
           todayDay = todayMomemt.format('DD')+'日',
           todayMonth = monthDict[todayMomemt.format('MMMM').toLowerCase()],
           todayDate = dateDict[todayMomemt.format('dddd').toLowerCase()],
           endMoment = this.props.endDate,
           endDay = endMoment.format('DD')+'日',
           endMonth = monthDict[endMoment.format('MMMM').toLowerCase()],
           endDate = dateDict[endMoment.format('dddd').toLowerCase()];
    this.state = {
      startDate: {
        moment:todayMomemt,
        day:todayDay,
        month:todayMonth,
        date:todayDate,
        param:null
      },
      endDate: {
        moment:endMoment,
        day:endDay,
        month:endMonth,
        date:endDate,
        param:null
      },
      clickTimes:0,
      showDateRange:false,
      params:null,
      dayText: [{
        ts: todayMomemt.format('x'),
        text: '入住'
      },{
        ts: endMoment.format('x'),
        text: '离开'
      }]
    }

    this.width = null;
  }

  componentDidMount() {
    this.width = document.body.clientWidth > 640 ? 640 : document.body.clientWidth
  }

  // 选择后自动关闭日期选择控件
  /*_handleSelect(range) {
    var that = this
    // 传值给父组件
    that.props.afterSelect(range.startDate,range.endDate)
    console.log(1)
    // 记录值改变次数
    that.setState({
      clickTimes: ++that.state.clickTimes
    })

    // 日期
    var startDate = {},
          endDate = {}
    startDate.moment = range.startDate
    startDate.day = range.startDate.format('DD').trim()+'日'
    startDate.month = monthDict[range.startDate.format('MMMM').toLowerCase()]
    startDate.date = dateDict[range.startDate.format('dddd').toLowerCase()]
    startDate.ms =  range.startDate.format('x')

    endDate.moment = range.endDate
    endDate.day = range.endDate.format('DD').trim()+'日'
    endDate.month = monthDict[range.endDate.format('MMMM').toLowerCase()]
    endDate.date = dateDict[range.endDate.format('dddd').toLowerCase()]
    endDate.ms =  range.endDate.format('x')

    var _showDateRange = this.state.showDateRange
    if (that.state.clickTimes === 2) {
      that.setState({
        clickTimes:0
      })
      _showDateRange = false;
    }

    that.setState({
      startDate:startDate,
      endDate:endDate,
    })

    setTimeout(function(){
      that.setState({
        showDateRange:_showDateRange
      })
    },500)
  }*/

  _handleSelect(range) {
    var that = this;
    // 日期
    var startDate = {},
          endDate = {};
    startDate.moment = range.startDate;
    startDate.day = range.startDate.format('DD').trim()+'日';
    startDate.month = monthDict[range.startDate.format('MMMM').toLowerCase()];
    startDate.date = dateDict[range.startDate.format('dddd').toLowerCase()];
    startDate.ms =  range.startDate.format('x');

    endDate.moment = range.endDate;
    endDate.day = range.endDate.format('DD').trim()+'日';
    endDate.month = monthDict[range.endDate.format('MMMM').toLowerCase()];
    endDate.date = dateDict[range.endDate.format('dddd').toLowerCase()];
    endDate.ms =  range.endDate.format('x');

    let _dayText = null;
    if (range.startDate.isSame(range.endDate,'day')) {
      _dayText = [{
        ts: range.startDate.format('x'),
        text: '入住'
      }];
    } else {
      _dayText = [{
        ts: range.startDate.format('x'),
        text: '入住'
      },{
        ts: range.endDate.format('x'),
        text: '离开'
      }];
    }

    that.setState({
      startDate:startDate,
      endDate:endDate,
      dayText: _dayText
    });


  }

  _showDateRange() {
    this.initStartDate = this.props.startDate.format('DD/MM/YYYY');
    this.initEndDate = this.props.endDate.format('DD/MM/YYYY');
    this.setState({
      showDateRange:true
    });
  }

  // 关闭组件
  _closeDateRange(e) {
    // 传值给父组件
    this.props.afterSelect(this.state.startDate.moment,this.state.endDate.moment);
    // 不显示选择控件
    this.setState({
      showDateRange:false,
      clickTimes: 0
    })
    e.stopPropagation();
  }

  render() {
    var state = this.state
    return (
      <div className={style.container}>
        <div className={style.opb}>
          <span className={style.title}>入住日期</span>
          <span className={style.title}>退房日期</span>
        </div>
        <div className={style.dateContainer}>
          <div onClick={this._showDateRange.bind(this)} className={style.date}>
            <h2>{state.startDate.day}</h2>
            <div>
              <span>{state.startDate.month}</span>
              <i>{state.startDate.date}</i>
            </div>
          </div>
          <i className={style.arrow}></i>
          <div onClick={this._showDateRange.bind(this)} className={style.date}>
            <h2>{state.endDate.day}</h2>
            <div>
              <span>{state.endDate.month}</span>
              <i>{state.endDate.date}</i>
            </div>
          </div>
        </div>
        {
          this.state.showDateRange ?
          <div className={style.dateRangeContainer}>
            {
              //<div className={style.back} onClick={this._closeDateRange.bind(this)}>返回</div>
            }
            <div className={style.confirm} onClick={this._closeDateRange.bind(this)}>{ '确定' + '(' + this.state.endDate.moment.diff(this.state.startDate.moment,"days") + '晚)'}</div>
            <DateRange
              calendars={5}
              disableDaysBeforeToday={true}
              showMonthArrow = {false}
              lang = {'cn'}
              offsetPositive = {true}
              startDate={ this.initStartDate  }
              endDate={ this.initEndDate  }
              shownDate = {moment()}
              onChange={this._handleSelect.bind(this)}
              dayText = {this.state.dayText}
              theme={{
                Calendar : {
                  width: this.width,
                },
                DateRange      : {
                  background   : '#ffffff'
                },
                MonthAndYear : {
                  fontSize: '1rem'
                },
                Weekday :{
                  fontSize: '1rem'
                },
                MonthButton    : {
                  background   : '#ff6f5c',
                  width:'24px',
                  height:'24px',
                  textAlign:'center'
                },
                MonthArrowPrev : {
                  borderRightColor : '#fff',
                  marginLeft: 3,
                },
                MonthArrowNext : {
                  borderLeftColor : '#fff',
                  marginLeft       :10,
                },
                Day            : {
                  fontSize: '1rem',
                  transition   : 'transform .1s ease, box-shadow .1s ease, background .1s ease'
                },
                DayText : {
                  color: '#ff6f5c'
                },
                DayPassive : {
                  opacity: 1,
                  color: '#ccc'
                },
                DaySelected    : {
                  //borderRadius : '50%',
                  background   : '#ff6f5c'
                },
                DayStartEdge : {
                  // borderTopLeftRadius : '50%',
                  // borderBottomLeftRadius : '50%',
                  background   : '#ff6f5c'
                },
                DayEndEdge : {
                  background   : '#ff6f5c',
                  // borderTopRightRadius : '50%',
                  // borderBottomRightRadius : '50%',
                },
                DayInRange     : {
                  background   : '#ffc5be',
                  color        : '#fff'
                },
                DayHover       : {
                  background   : '#ffffff',
                  color        : '#7f8c8d',
                  transform    : 'scale(1.1) translateY(-10%)',
                  boxShadow    : '0 2px 4px rgba(0, 0, 0, 0.4)'
                }

            }}/>

        </div>:null

        }
      </div>
    );
  }
}
