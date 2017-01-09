import React from 'react'
import { Link,hashHistory } from 'react-router'
import { DateRange } from 'react-date-range-ch'
import $ from 'jquery'
import moment from 'moment'
import style from './PickDate.less'

var monthDict = {
  'january':'一月',
  'february':'二月',
  'march':'三月',
  'april':'四月',
  'may':'五月',
  'june':'六月',
  'july':'七月',
  'august':'八月',
  'september':'九月',
  'october':'十月',
  'november':'十一月',
  'december':'十二月'
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
      params:null
    }
  }


  _handleSelect(range) {
    var that = this
    // 传值给父组件
    that.props.afterSelect(range.startDate,range.endDate)

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
    
    // 是否关闭，每点击一次，这个函数就要调用两次，若第三次还是点击同一个地方，则第三次只调用该函数一次，好神奇
    var _showDateRange = this.state.showDateRange
    if (that.state.clickTimes === 4
      || (that.state.clickTimes === 3 && startDate.ms === endDate.ms)) {
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
  }
 
  _showDateRange() {
    this.setState({
      showDateRange:true
    })
  }

  // 关闭组件
  _closeDateRange(e) {
    this.setState({
      showDateRange:false,
      clickTimes: 0
    })
    e.stopPropagation();
  }

  render() {
    var state = this.state
    return (
      <div className={this.props.className}>
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
            <div className={style.back} onClick={this._closeDateRange.bind(this)}>返回</div> 
            <DateRange
              startDate={ now => {
                var startDate
                if (state.startDate && state.startDate.moment) {
                  startDate = state.startDate.moment.format('DD/MM/YYYY')
                } else {
                  startDate = null
                }
                return startDate
              }}
              endDate={ now => {
                var endDate
                if (state.endDate && state.endDate.moment) {
                  endDate = state.endDate.moment.format('DD/MM/YYYY')
                } else {
                  endDate = null
                }
                return endDate
              }}
              linkedCalendars={ true }
              onChange={this._handleSelect.bind(this)}
              theme={{
                DateRange      : {
                  background   : '#ffffff'
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
                  transition   : 'transform .1s ease, box-shadow .1s ease, background .1s ease'
                },
                DaySelected    : {
                  background   : '#ff6f5c'
                },
                DayActive    : {
                  background   : '#ffc5be',
                  boxShadow    : 'none'
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
          </div> :
          null
        }
      </div>
    );
  }
}

