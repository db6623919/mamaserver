import React from 'react'
import { hashHistory } from 'react-router';
import style from './Nav.less'

var navIcon = 'data:image/png;base64,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'
var homeIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4CAYAAACohjseAAAABGdBTUEAALGPC/xhBQAABTtJREFUaAXtmn9IXWUYx/X+jGvTuiItNnCQS4jqj61kRQW3oigi0KbiIMPp9UctAg2kX38EwTCoIAN/XFOwtZjLFftDkDWF2hgRQn8tjGKgjG2VyHVdU6/X2+eRI9y2c+4959z7XlycF17f4/M87/M83+f99Zz33IICpzgRcCLgRMCJgBOB7ROBvr6+XQMDAyX58qgwX4b6+/trsPVRMpnco9mc9Hq9R1paWn5T6YNygGNjY+6FhYUeQHRpQBYLCwtvB6iXNupyuRpbW1tPqwKpFODw8HDZ2traCcCEABOn7SwtLe1bWloqicfjg4B6CTrkZA/0d+vq6hK5BqoMIGutCufHcX437RXa2o6OjvOpAJi2b0I/Cs1DnSoqKmpobGz8I1Um22clANlEWjY2Nj7DOT/gzvv9/oNNTU1X9ZwF5BOAPAFvJ7KXaWvb29sv6MnaoeUU4MTEhH9ubq4Xh8PiDOtLQHa2tbXF0zlHQO4mIALycUCKbBcge9P1McvLGcBIJLI7kUjIlKzC+D9ut7uNzeMLs45MT097Zmdne+jfKX0A+lUgEAgzZWNmdejJ5QQgIxDSRqAMxy4xcjWA+1nPYCYaU/YgMsMA3YGuixwlNc3NzbOZ+hnxXUYMs3TAdeHMGeTLqJMej+chu+DEJlPza0A9LODQex+77U8aaGFbLrZHcHR0tGh5eflznKjHGZrkUZx7j+cNy17odND0R9DbIGz0flxZWdkdCoXWdcQNSbYAEtG9GD6F1vupS0zJV9hIvjW0kgUDW6/TXTIgL+0P2KrH1hWzKi0DHBwcfIH1dgyDkk/+4vP5qrNZI2YcBeQjyJ3E5i7aq4xmPbPlezN9Ta9BlLsw9D7gTmvgxsk+qlSDExCAucCOuo/HKepO7J+VtS+8TMXUCI6MjNyxurr6JYqfJ3oJ2rfJSj7MpDzXfC2v/QAfuvFBfB9nBjUR5OtGtjIC5Hx7kPPtFArvQfFfrIEGdsnvjBTmg84yeZGZNIpPJfj0KzarGeWLerbTAmQaPIOSb6gBOs/I+cYCn9tSJIfz/Px8cOv/fLYrKysVgJRZtQeQ12j3M6sk1ftPkSTXsKDgEMwACkbIJ18ln1wRYdnCY7HYp2Qeh1B8m6GCPDHw4S58fEpcu9FkWoBsIkei0WhvOByeSe3I+Sfr7zCK5czLafafasfk8yrgzgWDQTm2bippp+hN0hqBVyEBVcaUPcCU/dFIbjvQTR8TNzgraVnBdgcnPtoFKH3zXtj0vBwVPiuG065BK4ryIcuan+F+R5bVA2bt3WoATQPbCsAtNUW3nLbSOgCtRGs7yipfg7LzsTm8AXjJNHakBgH63/w/xXn6CUdO2oup1H5WnpUDBMRxqtyzGJVn4R+AWWMkkA1d6RocGhqqEHCkUjFGqQ5HH0ut0Gup15Gp5g3h3myAGPVVOoLr6+sVYhgA53idOannBGnfYejPIbOXVl59clqUjiCebupnlNJ9c9jkIWMrL84UDdUAM9lXzncAKg+xYgPOCCoOsHL1tkaQDe+aeMbZ9bSRh2z7LuqTwqfV/Tao9d288uD+51EjXULXLn/l8U/5Y7bYOgdx+BgGurhOPIPhNT1jpGiy7ct1u3xXOK4nIzQ+s42hR+533uJMfA1Zo28Pd4o8CYN8RzRdbI1geXn5O5I/YmUBx3wGVX5k8Dv1ZQ75s0Yeccc6Ca+VKqNcjK6gXoW3jK4IbTfVdMn6cDW6QlhcXExaTaAZ9RJG1K3nfXFxcVTFjxT0bDk0JwJOBJwIOBFwIvB/icC/XmE/cSVIVqIAAAAASUVORK5CYII='
var discoverIcon = 'data:image/png;base64,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'
var searchIcon = 'data:image/png;base64,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'
var personalIcon = 'data:image/png;base64,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'
var logoutIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAB4AAAAeCAYAAAA7MK6iAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMDY3IDc5LjE1Nzc0NywgMjAxNS8wMy8zMC0yMzo0MDo0MiAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZjM5ZDQyYi1hOGQ5LTQ3MWQtYTQ5ZC1mZGVjM2QwYWE3YWQiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NzAwMUJEN0U2QzI5MTFFNkE2QUM4OTE1Q0JEOEIzQjQiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NzAwMUJEN0Q2QzI5MTFFNkE2QUM4OTE1Q0JEOEIzQjQiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTUgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDozN2M0OWU3ZS1lZTgxLTQ3ZDktOGM4Yi1hZjk5YzEyNGEyNmUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGYzOWQ0MmItYThkOS00NzFkLWE0OWQtZmRlYzNkMGFhN2FkIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+z/0rGAAAANxJREFUeNpi/P//P8NAACaGAQKjFg9Kiz2B+BkQ/ycDP4PqRwBQqiYSPwNiGxLUI2MbqH64GCMJ2QmkkJGC0EXRP5q4kAErECsj8b/Sw2I2IF4JxOVIYlLUtBhb4gJZugKaEMKB+BeV7MKbuGhlKcGgfg7E4kBsDMQ/ySgoHhLvf9SMng3En4DYg8yCAh/+j8zHpiAYarknvS0G4UAg/gzE3khiZvSwGIT9gfgMEv8fNS0eLasHpcWgPG5Dpj3WQPyC3IaAFxA//08eeALVT1biGm1ljlo8NCwGCDAAww9xtSuByBMAAAAASUVORK5CYII='
var logoutIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADgAAAA4CAYAAACohjseAAAABGdBTUEAALGPC/xhBQAAAwNJREFUaAXtmMtrk0EQwJugieKlkJOHWvAfEC968CSCJy8KDeLBHkqSL1QQPYgPUKv4QkQRNA9SIiKKxYogxIPgzbN4FQSNBxX0qtG8/E3xULChs+nuprG7sOzXL7Mz85uZfXwdGQktRCBEIEQgRCBEIETgv41ATENWKpU2dzqdMY2sD5lYLPYriqK3GlvrNELITNKvKGV9iNUxMq4xpAVc0EXkPvPwSaPYkUyy2+1uM9FtBIji+5TGSRMDNmVZKlsA/GiiM24iPIyyAXAYs7bY55DBxdEYxmevGaxUKtvL5fI+n4HyBlgoFHa0Wq1X3Ijm5dkXpBdAgHYB9JIzbJT+PB6Pv/EFaHrQG/vF4bxboOibmPwwlUodTqfTbWNFfU5wCgjcXkryGb5t5Jp3L5fLTTF2+vRVpv1g/iPGb1odzgBlM2m3209wJElJlrLZbB7nulrHlpIjQAJ2aKnfer1zsgaLxeIBMvcUo0n6bRyLVgrXC2C599YBydxB1ttj+nqgrufz+aPLOeHyd6slym55hrK8gMNx4Gbp86zDnbYBKPkPmUzmq0avNUDKcg8GL5K5hf8SME5J1zjRh8wR5tzRzLMGSFQTZI+kxeDqvmf8rnGgHxl0f9HOswbILvmCEp0B7hzGt9Iv83Fc1TriSs7qJsOGcp5MnsJZ0TtL2UauHNfqtQooRjkSrjIcJ5NSqwUgB7qLWgcUSDJ5k3Uy/Xc93qJ0T8j7QTQngALC+rvLkKHL1ewamTzLuKImVVGtVjfUajW5QKiaM0CxDqSchZP0Ns7NkMlLKq96CHGJGGs0Gj/r9fq7HiL/vHYKKNaAfACc3B9b9NNk8oa899WcAwoIa3KO3XWCTP7mz2NkUr4PvTQvgELC7iqfTfuBnAb4tRc6jFg76DUOU641jZxNGW8ZtOm0ia4AaBKt1SgbMrgas2Lik+kumuMMmzAxYFOWC4Opv2bHBAZGcVj6QBr2je2qIpJIJIoonzPW7mhCs9mUa19oIQIhAiECIQIhAiECaz0CfwBN4/bX7dyd/QAAAABJRU5ErkJggg=='
var loginIcon = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2lpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpFQ0E2QzRGMzNCMEZFNTExOEFEREJCOUY5NkUxOTdEMiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoyRDI2QUUwQjY4QjYxMUU1OTYxRkI1RENDMTIyNkQ0NiIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoyRDI2QUUwQTY4QjYxMUU1OTYxRkI1RENDMTIyNkQ0NiIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ0MgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6N2QyNWIyZGQtNGE4OS04NTQ0LThjMjAtNTFhMTNlODg3MDVmIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkVDQTZDNEYzM0IwRkU1MTE4QUREQkI5Rjk2RTE5N0QyIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+t8ETqQAAA+NJREFUeNrsmmlsDWEUhufS2kKaiiUEsSRVsQQl9hCJfUka+9IUidBaI1SopVURavvBD7VLJdQWtZPwyxb8aSgSQhCqGntpS7Tek7w3Tq57r87cmbkaTvKkZ+bOfHfe+ZZzznfrqaioMKqyVTOquEX4nihNGGO1ra5gHBgC2oI6oAQ8BldADrgJLHV5rezjlRNgwbqA7aC3n89qg45kAcgHy8Dpv2EIRYLN4E6Ah/dn7cEpsJ89FDYB8uW5YJFq4w3IBP1ANPCAehS3EbxU9yeCyyAqHAKqg0NgGI9/gHWgFVgKroIP/KwY3AApoA1I4/ViPcEFGd5uC1gFRtP/CkaAVPrBrAykg3h1rYjY6qaAzmC5evNjwUWTbcgEnqRWo1lggFsCNqiVaxs4b/F7ZSLvpC9zZZMbAjqAwfTfg4wQ51+6GkpxoI/TAqYoX5bBdyEKKADH1PF4pwX0V/4Zm+LQhQDtOyKgrfLv2iRAtxPrtID6yi+yScBH5de0Ep3NCCjVuZVNAiJ9jkucFPAswHAKxVor/7mVTNWMgFvKH2KTgKHKv+30HMj1WVI9NuRh8aGubGYEnFYZZScwMUQBU9QQ+uATExwR8M0n+kot0Njiwzfj/bqtYjdyod3gOv0mDEQNTLYhos+Bhjx+wHrBlWTuBzPJIpWdyuTuVcn7u/H6jjz+whSizM16QJa7kUzoDBYyUsQcZjoQ4afuHggOsKhvoWoJqSvu2borYWJJ7ce0uDVfxATyGTxklK0L2vkpHQu5At2wfVvFhOWzopK3H6POSx3cPch9r1gnPwtnUV8DLGT3xwS45lOA801BHljBHnJdQA8+gNSyjXjuLSssGRYtGeSi+FfG/ChWcN7JH8Ul+T4Y5KaAuRwysaoomQ2as7Y96WdovGCUna+uK+BnzVlTr7Ya2c0IyORb9M6bfRSSZSKLLGNPxfA+b02cxvaqOyVgLVhC/zuYCWYEGed/smL2XIKKAbLZtcsJAdO47+NNJyYyItthBxkLvLXGdLDYTgGStO1Qx0nghGGvXfLZJ1oP+tohQKqlbJZ6BsfuXsMZk8m/hr7Mgz3qey0LSGIPeNOHRYazlqGKGpnkc0IRIPv6K9VxChMvJ00SxWQ1lFIrE+gCCZis0mRJd48a7tgdDifvLshUqwISlS+TuNxwz7YoP9mKgGjj1y8u0p1HDHftGnhCX+qGOLMC4lRElIzztcsC5KWd9amdTQnQez55RnhMb7EMNyugkfJfhknAU+XHGL/v4AUVoC8uCpOAQuWXByu8/AnIYkkoEyknTAIe8Tkk7Z4XLNv1/P9fif8C/nEBPwUYAF8Fyf2lVBrBAAAAAElFTkSuQmCC'

export default class Nav extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	isShow:false
    }
    this.clickEvent = null
  }

  _switchShow(isShow) {
  	var menu = this.refs.menu
        if (!menu) return
  	if (typeof window.screenX === 'number') {
  		var height = 0
  		Array.prototype.slice.call(menu.childNodes).forEach(function(child){
  			if (child.nodeType === 1) {
  				var oStyle = window.getComputedStyle(child)
  				 height += child.clientHeight + (parseInt(oStyle.borderTopWidth) || 0) + (parseInt(oStyle.borderBottomWidth) || 0);
  			}
  		})
  		menu.style.height = isShow ? height+'px' : '0px'
  	} else {
      // IE6-IE8浏览器
      menu.style.height = isShow ? height+'auto' : '0px'
  	}
  }

	componentDidMount() {
		var that = this
    that.clickEvent = that._clickEventCb.bind(that)
		document.addEventListener('click',that.clickEvent,false)
	}

	_clickEventCb() {
		var that = this
		var newState = false
		that._switchShow(newState)
		that.setState({
			isShow:newState
		})
	}

	componentWillUnmount() {
		var that = this

    document.removeEventListener('click',that.clickEvent)
	}

  _onClick(e) {
		var that = this
		var newState = !this.state.isShow
		this._switchShow(newState)
		that.setState({
			isShow:newState
		})
		e.nativeEvent.stopImmediatePropagation()
  }

  render() {
    return (
   	<div className={this.state.isShow ? style.nav+' '+style.active : style.nav} onClick={this._onClick.bind(this)}>
   			{
	   			this.props.isLogin ?
   				<ul ref="menu" className={style.menu} onClick={(e)=>{e.stopPropagation()}}>
		   			<span className={style.arrow}></span>
		   			<a href={'/index.htm'} className={style.firstItem}>
		   				<li className={style.home}>
		   					<img src={homeIcon}/>
		   					首页
		   				</li>
		   			</a>
		   			<a  href={'/discover/index.htm'} >
							<li className={style.discover}>
		   					<img src={discoverIcon}/>
								发现
							</li>
		   			</a>
            {
              // <a  href={'/search/index.htm'} >
              //   <li className={style.search}>
              //     <img src={searchIcon}/>
              //     搜索
              //   </li>
              // </a>
            }

		   			<a  href={'/my/usercenter.htm'} >
							<li className={style.personal}>
		   					<img src={personalIcon}/>
								个人中心
							</li>
		   			</a>
		   			<a href={'/toLogout.htm'} className={style.lastItem}>
							<li className={style.logout}>
								<img src={logoutIcon}/>
								退出
							</li>
		   			</a>
	   			</ul> :
	   			<ul ref="menu" className={style.menu} onClick={(e)=>{e.stopPropagation()}}>
		   			<span className={style.arrow}></span>
                           <a href={'/index.htm'} className={style.firstItem}>
                            <li className={style.home}>
                              <img src={homeIcon}/>
                              首页
                            </li>
                          </a>
                          <a  href={'/discover/index.htm'} >
                            <li className={style.discover}>
                              <img src={discoverIcon}/>
                              发现
                            </li>
                          </a>
                          {
                            // <a  href={'/search/index.htm'} >
                            //   <li className={style.search}>
                            //     <img src={searchIcon}/>
                            //     搜索
                            //   </li>
                            // </a>
                          }
	   				<a href={'/my/toLogin.htm'} className={style.lastItem}>
      					<li className={style.login}>
      						<img src={loginIcon}/>
      						登录/注册
      					</li>
	   				</a>
	   			</ul>
   			}
   	</div>
    );
  }
}
