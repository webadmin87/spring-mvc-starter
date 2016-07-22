import React from "react"
import { connect } from "react-redux"
import { Link } from "react-router"
import store from "store"
import { logoutAction } from "actions/authentication"

class LoginPanel extends React.Component {


    __logout() {
        store.dispatch(logoutAction())
    }

    render() {
        let isAuth = this.props.authentication
        return <ul className="navbar-nav navbar-right nav">
            { !isAuth?<li><Link to="/login">Вход</Link></li>:null }
            { isAuth?<li><a onClick={ this.__logout.bind(this) }>Выход ({ this.props.authentication.username})</a></li>:null }
        </ul>;
    }


}

const mapStateToProps = function(store) {

    return {
        authentication: store.authenticationState.authentication
    }

}

export default connect(mapStateToProps)(LoginPanel)