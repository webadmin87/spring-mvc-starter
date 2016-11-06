import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userRolesLoadedAction, userAddAction, userServerErrorAction } from "actions/user"
import store from "store"
import UserForm from 'components/UserForm'
import ServerErrors from "components/ServerErrors"

class UserAdd extends React.Component {

    componentDidMount() {
        axios.get('admin/user/roles/').then(r => {
            store.dispatch((userRolesLoadedAction(r.data)))
        })
    }

    handleSubmit(e) {
        axios.post('admin/user/', this.props.data).then(r => {
            this.context.router.push('/users');
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

UserAdd.contextTypes = {
    router: React.PropTypes.object.isRequired
}


export default connect(state => {
    return {
        data: state.userState.entity,
        errors: state.userState.errors,
        roles: state.userState.roles,
        serverErrors: state.userState.serverErrors
    }
})(UserAdd)