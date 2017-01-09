import React from 'react'
import {
    Router,
    Route,
    hashHistory,
    browserHistory,
    IndexRoute,
    Link
} from 'react-router';

import DiscoverList from './discover/DiscoverList.js'
import DiscoverDetail from './discover/DiscoverDetail.js'
import SearchIndex from './search/SearchIndex.js'
import SearchDetail from './search/SearchDetail.js'
import OrderSuccess from './order/Success.js'
import SpecialOffer99Index from './activity/special-offer-99/Index'
import SpecialOffer99List from './activity/special-offer-99/List'

// import Test from './test/Test.js'
// class Root extends React.Component {
//   render() {
//     return (
//       <div>
//               <NavLink to='' >我</NavLink>
//               {this.props.children}
//       </div>
//
//     )
//   }
// }

export default class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router history={window.debug
                ? hashHistory
                : browserHistory}>
                <Route path="/">
                    <Route path={window.url.discoverIndex} component={DiscoverList}/>
                    <Route path={window.url.discoverDetail} component={DiscoverDetail}/>
                    <Route path={window.url.searchIndex} component={SearchIndex}/>
                    <Route path={window.url.searchDetail} component={SearchDetail}/>
                    <Route path={window.url.orderSuccessWxpay} component={OrderSuccess}/>
                    <Route path={window.url.orderSuccessAlipay} component={OrderSuccess}/>
                    <Route path={window.url.specialOffer99Index} component={SpecialOffer99Index}/>
                    <Route path={window.url.specialOffer99List} component={SpecialOffer99List}/>
                </Route>
            </Router>
        );
    }
}
