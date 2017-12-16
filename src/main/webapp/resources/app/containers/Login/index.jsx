import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import LoginForm from "components/LoginForm"
import { authenticationAction } from "actions/authentication"
import {encode} from "querystring"
import store from "store"
import i18next from 'i18next'
import { withRouter } from "react-router-dom"

class Login extends React.Component {

    componentWillMount() {
        this.__isRedirect(this.props);
    }

    componentWillReceiveProps(newProps) {
        this.__isRedirect(newProps);
    }

    __isRedirect(props) {
        if(props.authentication) {
            this.props.history.push('/');
        }
    }

    __login(username, password) {

        axios.post('login', encode({username, password}),
            {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }
        )
            .then(function (response) {
                store.dispatch(authenticationAction(response.data));
            })
            .catch(function (error) {
                store.dispatch(authenticationAction(null, i18next.t('app_authentication_error')));
            });

    }

    render() {
        return <LoginForm login={ this.__login.bind(this) } error={ this.props.error } />
    }

}

const mapStateToProps = function(store) {

    return {
        authentication: store.authenticationState.authentication,
        error: store.authenticationState.error
    }

};

export default withRouter(connect(mapStateToProps)(Login))