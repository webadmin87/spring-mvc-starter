import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userRolesLoadedAction, userAddAction, userServerErrorAction, userEntityReset } from "actions/user"
import store from "store"
import UserForm from 'components/UserForm'
import ServerErrors from "components/ServerErrors"
import Settings from "settings"
import { withRouter } from "react-router-dom"

class UserAdd extends React.Component {

    componentDidMount() {
        axios.get(Settings.USERS_URL + 'roles/').then(r => {
            store.dispatch((userRolesLoadedAction(r.data)))
        })
    }

    handleSubmit(e) {
        axios.post(Settings.USERS_URL, this.props.data).then(r => {
            store.dispatch(userEntityReset())
            this.props.history.push('/users')
        }).catch(e => {
            store.dispatch((userServerErrorAction(e.data)))
        })
    }

    render() {
        return <div>
            <ServerErrors errors={ this.props.serverErrors } />
            <UserForm roles={ this.props.roles } data={ this.props.data } errors={ this.props.errors } serverErrors={ this.props.serverErrors } handleSubmit={ this.handleSubmit.bind(this) } />
        </div>
    }

}

export default withRouter(connect(state => {
    return {
        data: state.userState.entity,
        errors: state.userState.errors,
        roles: state.userState.roles,
        serverErrors: state.userState.serverErrors
    }
})(UserAdd))