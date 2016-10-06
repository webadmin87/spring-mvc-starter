import React from 'react'
import { userAddAction, userErrorAction } from 'actions/user'
import store from "store"
import { notEmpty, email, password, confirmPassword} from "core/validators"
import FormBase from "core/FormBase"
import i18next from 'i18next'
import Errors from 'components/Errors'

export default class UserForm extends FormBase {

    validators() {

        return {
            'name': [notEmpty],
            'username': [notEmpty],
            'email': [notEmpty, email],
            'inputPassword': [notEmpty, password],
            'confirmInputPassword': [notEmpty, confirmPassword],
            'role': [notEmpty],
        }

    }

    getStore() {
        return store
    }

    getAddAction() {
        return userAddAction
    }

    getErrorAction() {
        return userErrorAction
    }

    render() {

        console.log(this.props.errors)

        return <form>

            <div className="form-group">
                <label htmlFor="user-name">{ i18next.t('app_user_name') }</label>
                <input type="text" className="form-control" id="user-name" onChange={this.getHandleChange('name')} />
                <Errors errors={ this.props.errors.name } />
            </div>

            <div className="form-group">
                <label htmlFor="user-username">{ i18next.t('app_user_username') }</label>
                <input type="text" className="form-control" id="user-username" onChange={this.getHandleChange('username')} />
                <Errors errors={ this.props.errors.username } />
            </div>

            <div className="form-group">
                <label htmlFor="user-password">{ i18next.t('app_user_password') }</label>
                <input type="password" className="form-control" id="user-password" onChange={this.getHandleChange('password')} />
                <Errors errors={ this.props.errors.inputPassword } />
            </div>

            <div className="form-group">
                <label htmlFor="user-confirm-password">{ i18next.t('app_user_confirm_password') }</label>
                <input type="password" className="form-control" id="user-name" onChange={this.getHandleChange('confirmPassword')} />
                <Errors errors={ this.props.errors.confirmInputPassword } />
            </div>

            <div className="form-group">
                <label htmlFor="user-email">{ i18next.t('app_user_email') }</label>
                <input type="text" className="form-control" id="user-email" onChange={this.getHandleChange('email')} />
                <Errors errors={ this.props.errors.email } />
            </div>

            <div className="form-group">
                <label htmlFor="user-phone">{ i18next.t('app_user_phone') }</label>
                <input type="text" className="form-control" id="user-phone" onChange={this.getHandleChange('phone')} />
                <Errors errors={ this.props.errors.phone } />
            </div>

            <div className="form-group">
                <label htmlFor="user-role">{ i18next.t('app_user_role') }</label>
                <input type="text" className="form-control" id="user-role" onChange={this.getHandleChange('role')} />
                <Errors errors={ this.props.errors.role } />
            </div>

            <button className="btn btn-primary" onClick={ this.handleSubmit.bind(this) }>{ i18next.t('app_entity_save') }</button>

        </form>

    }

}