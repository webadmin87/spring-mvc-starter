import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userRolesLoadedAction, userAddAction, userServerErrorAction, userEntityLoad, userEntityReset } from "actions/user"
import store from "store"
import UserForm from 'components/UserForm'
import ServerErrors from "components/ServerErrors"
import Settings from "settings"
import { withRouter } from "react-router-dom"

class UserEdit extends React.Component {

    componentDidMount() {

        axios.all([axios.get(Settings.USERS_URL + 'roles/'), axios.get(Settings.USERS_URL + this.props.match.params.userId + '/')]).then(axios.spread(function (roles, entity) {
            store.dispatch((userRolesLoadedAction(roles.data)));
            store.dispatch((userEntityLoad(entity.data)));
        }));

    }

    handleSubmit(event) {
        axios.put(Settings.USERS_URL + this.props.data.id + '/', this.props.data).then(r => {
            store.dispatch(userEntityReset());
            this.props.history.push('/users');
        }).catch(e => {
            store.dispatch((userServerErrorAction(e.data)));
        });
    }

    render() {
        return ( <div>
            <ServerErrors errors={ this.props.serverErrors } />
            <UserForm isUpdate={ true } roles={ this.props.roles } data={ this.props.data } errors={ this.props.errors } serverErrors={ this.props.serverErrors } handleSubmit={ this.handleSubmit.bind(this) } />
        </div> );
    }

}


export default withRouter(connect(state => {
    return {
        data: state.userState.entity,
        errors: state.userState.errors,
        roles: state.userState.roles,
        serverErrors: state.userState.serverErrors
    }
})(UserEdit))