import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userAddAction } from "actions/user"
import store from "store"
import UserForm from 'components/UserForm'

class UserAdd extends React.Component {

    handleSubmit() {
        axios.post('admin/user/', this.props.data).then(r => {

        }).catch(e => {
            console.log(e.data)
        })
    }

    render() {
        return <UserForm data={ this.props.data } errors={ this.props.errors } handleSubmit={ this.handleSubmit.bind(this) } />
    }

}

export default connect(state => {
    return {
        data: state.userState.entity,
        errors: state.userState.errors
    }
})(UserAdd)