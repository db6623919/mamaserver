import React from 'react'
import { Link } from 'react-router';

import Title from '../common/Title'
import Loading from '../common/Loading'
import Toast from '../common/Toast'
import Row from './Row'
import { getUrlParam,mmGet,mmPost } from '../../utils/utils'
import style from './List.less'

export default class List extends React.Component {


  constructor(props) {
    super(props);
    var _house;
    // _house = [
    //             {
    //                 "houseId":833,
    //                 "houseName":"内部测试房源预定无效",
    //                 "personNum":1,
    //                 "bedType":"1.2*1.2",
    //                 "area":"1",
    //                 "memTotalAmt":1
    //             },
    //             {
    //                 "houseId":115,
    //                 "houseName":"三亚阳光海景公寓海景双床房",
    //                 "personNum":2,
    //                 "bedType":"2*1.2*2.0",
    //                 "area":"30",
    //                 "memTotalAmt":228
    //             }]
    this.state = {
      shopId:null,
      timer:null,
      isShowErr: false,
      err:null,
      isDataNull: true,
      isHousesNull: true,
      shopName:null,
      houses: _house || null
    }
  }

  componentDidMount () {
    var that = this
    var shopId = getUrlParam('shopId')
    that.setState({
      shopId:shopId
    })
    mmPost(window.api.baseUrl+window.api.housesList,{shopId:shopId})
      .then(data => {
        if ((data.code === 0 || data.code === '0')&& data.result) {
          that.setState ({
            isDataNull: false,
            shopName: data.result.shopName
          })
          if (data.result.houseData) {
            that.setState({
              houses: data.result.houseData.houses
            })
          }
        } else {
          this._showErr(data.message)
        }
      },err => {
        this._showErr(window.err)
      })
  }

  componentWillUnmount() {
    this.state.timer && clearTimeout(this.state.timer);
  }


  _showErr (err) {
      this.setState({
        isShowErr: true,
        isDataNull: false,
        err: err
      })
      this.state.timer = setTimeout(()=> {
          this.setState({isShowErr: false});
      }, 2000);
  }

  render() {
    return (
      <div className={style.container}>
        <Toast 
          show = {this.state.isShowErr}
          content = {this.state.err}
          />
        {
            this.state.isDataNull ?
            <Loading  show={true}/> :
            <div> 
              <Title isBack={false} title={this.state.shopName} />
              {
                 (this.state.houses && this.state.houses.length) > 0 ?
                 this.state.houses.map((item) => {
                    return (
                      <Row 
                        shopName = {this.state.shopName}
                        shopId = {this.state.shopId}
                        houseId = {item.houseId}
                        area = {item.area}
                        bedType = {item.bedType}
                        houseName = {item.houseName}
                        memTotalAmt = {item.memTotalAmt}
                        personNum = {item.personNum}
                        />
                    )
                 }) :
                 <div className={style.noHouse}>暂无房源</div>
              }
           </div>
        }
     </div>
    );
  }
}

