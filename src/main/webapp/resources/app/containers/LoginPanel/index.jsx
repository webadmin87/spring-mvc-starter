import React from "react"
import { connect } from "react-redux"
import { Link, withRouter } from "react-router-dom"
import store from "store"
import { logoutAction } from "actions/authentication"
import i18next from 'i18next'

class LoginPanel extends React.Component {


    __logout() {
        store.dispatch(logoutAction());
        this.props.history.push("/login");
    }

    render() {
        let isAuth = this.props.authentication;
        return ( <ul className="navbar-nav navbar-right nav">
            { !isAuth?<li><Link to="/login">{ i18next.t('app_login_panel_enter') }</Link></li>:null }
            { isAuth?<li><a onClick={ this.__logout.bind(this) }>{ i18next.t('app_login_panel_exit') } ({ this.props.authentication.username})</a></li>:null }
        </ul> );
    }


}

const mapStateToProps = function(store) {
    return {
        authentication: store.authenticationState.authentication
    }
};

export default withRouter(connect(mapStateToProps)(LoginPanel))