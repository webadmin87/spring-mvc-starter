import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, hashHistory, IndexRoute } from 'react-router'
import StartContainer from 'containers/Start'
import LoginContainer from 'containers/Login'
import App from './app.jsx'
import { Provider } from "react-redux"
import store from "store"

class AppRouter extends React.Component {

    render() {
        return (
            <Provider store={store}>
                <Router history={hashHistory}>
                            <Route path="/" component={App}>
                                <IndexRoute component={StartContainer} />
                                <Route path="/login" component={LoginContainer}/>
                            </Route>
                    </Router>
            </Provider>
        )

    }

}

ReactDOM.render(
    <AppRouter />,
    document.getElementById("app")
);