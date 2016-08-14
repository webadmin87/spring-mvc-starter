import React from "react"
import { connect } from "react-redux"
import { Link } from "react-router"
import store from "store"
import { logoutAction } from "actions/authentication"
import i18next from 'i18next'

class LoginPanel extends React.Component {


    __logout() {
        store.dispatch(logoutAction())
        this.context.router.push("/login")
    }

    render() {
        let isAuth = this.props.authentication
        return <ul className="navbar-nav navbar-right nav">
            { !isAuth?<li><Link to="/login">{ i18next.t('app_login_panel_enter') }</Link></li>:null }
            { isAuth?<li><a onClick={ this.__logout.bind(this) }>{ i18next.t('app_login_panel_exit') } ({ this.props.authentication.username})</a></li>:null }
        </ul>;
    }


}

LoginPanel.contextTypes = {
    router: React.PropTypes.object.isRequired
}

const mapStateToProps = function(store) {
    return {
        authentication: store.authenticationState.authentication
    }
}

export default connect(mapStateToProps)(LoginPanel)