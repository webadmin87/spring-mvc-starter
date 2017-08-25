import React from 'react'
import ReactDOM from 'react-dom'
import { HashRouter, Route, Switch } from 'react-router-dom'
import StartContainer from 'containers/Start'
import LoginContainer from 'containers/Login'
import UserListContainer from 'containers/UserList'
import UserAddContainer from 'containers/UserAdd'
import UserEditContainer from 'containers/UserEdit'
import App from './app.jsx'
import { Provider } from "react-redux"
import store from "store"

class AppRouter extends React.Component {

    render() {
        return (
            <Provider store={store}>

                <HashRouter>
                    <App>
                        <Switch>
                            <Route exact path='/' component={StartContainer}/>
                            <Route path="/login" component={LoginContainer}/>
                            <Route path="/users" component={UserListContainer}/>
                            <Route path="/userAdd" component={UserAddContainer}/>
                            <Route path="/userEdit/:userId" component={UserEditContainer}/>
                        </Switch>
                    </App>
                </HashRouter>
            </Provider>
        );
    }
}

ReactDOM.render(
    <AppRouter />,
    document.getElementById("app")
);