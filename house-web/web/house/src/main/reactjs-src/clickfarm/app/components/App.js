import React from 'react';
import { Router, Route, hashHistory, IndexRoute} from 'react-router';

// import List from './list/List'
// import Reserve from './reserve/Reserve'
// import Order from './order/Order'
import NewOrder from './new-order/NewOrder';
import PayLoading from './pay-loading/PayLoading';
import Success from './success/Success';
import Index from './special-offer/Index';
import List from './special-offer/List';

import Test from './test/Test';

export default class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
		  <Router history={hashHistory}>
		    <Route path="/">
		      <IndexRoute component={NewOrder}/>
		      <Route path="/payLoading/:orderId/:amt/:shopId" component={PayLoading}/>
  				<Route path="/success" component={Success}/>
          <Route path="/special-offer" component={Index} />
          <Route path="/special-offer/list" component={List} />
		      {
				    // <Route path="/list" component={List}/>
				    // <Route path="/reserve" component={Reserve}/>
				    // <Route path="/order" component={Order}/>
				    // <Route path="/payLoading/:orderId/:amt/:shopId" component={PayLoading}/>
				    // <Route path="/success" component={Success}/>
			   }
		    </Route>
		  </Router>
    );
  }
}
