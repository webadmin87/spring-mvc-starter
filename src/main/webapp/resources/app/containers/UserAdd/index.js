import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userAddAction, userServerErrorAction } from "actions/user"
import store from "store"
import UserForm from 'components/UserForm'
import ServerErrors from "components/ServerErrors"

class UserAdd extends React.Component {

    handleSubmit(e) {
        axios.post('admin/user/', this.props.data).then(r => {

        }).catch(e => {
            store.dispatch((userServerErrorAction(e.data)))
        })
    }

    render() {
        return <div>
            <ServerErrors errors={ this.props.serverErrors } />
            <UserForm data={ this.props.data } errors={ this.props.errors } serverErrors={ this.props.serverErrors } handleSubmit={ this.handleSubmit.bind(this) } />
        </div>
    }

}

export default connect(state => {
    return {
        data: state.userState.entity,
        errors: state.userState.errors,
        serverErrors: state.userState.serverErrors
    }
})(UserAdd)