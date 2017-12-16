import React from 'react'
import { userAddAction, userErrorAction, userEntityReset, userImagesAdd, userImagesUpdate, userImagesDelete } from 'actions/user'
import store from "store"
import { notEmpty, email, password, confirmPassword, username, phone} from "core/validators"
import FormBase from "core/FormBase"
import i18next from 'i18next'
import Errors from 'components/Errors'
import Select from 'react-select'
import FileUploader from 'components/FileUploader'
import { withRouter } from "react-router-dom"

class UserForm extends FormBase {

    validators() {

        let validators = {
            'name': [notEmpty],
            'username': [notEmpty, username],
            'email': [notEmpty, email],
            'role': [notEmpty],
            'phone': [phone]
        };

        let confirmPasswordBinded =  confirmPassword.bind({}, "inputPassword");
        confirmPasswordBinded.message = confirmPassword.message;

        if(!this.props.isUpdate) {
            validators.inputPassword = [notEmpty, password];
            validators.confirmInputPassword = [notEmpty, confirmPasswordBinded];
        }

        return validators;

    }

    getStore() {
        return store;
    }

    getAddAction() {
        return userAddAction;
    }

    getErrorAction() {
        return userErrorAction;
    }

    onCancel() {
        store.dispatch(userEntityReset())
        this.props.history.goBack();
    }

    render() {

        return ( <form onSubmit={ this.handleSubmit.bind(this) }>

            <div className="form-group">
                <label htmlFor="user-name">{ i18next.t('app_user_name') }</label>
                <input type="text" className="form-control" id="user-name"  value={ this.props.data.name || '' } onChange={this.getHandleChange('name')} />
                <Errors errors={ this.props.errors.name } />
            </div>

            <div className="form-group">
                <label htmlFor="user-username">{ i18next.t('app_user_username') }</label>
                <input type="text" className="form-control" id="user-username"  value={ this.props.data.username || '' } onChange={this.getHandleChange('username')} />
                <Errors errors={ this.props.errors.username } />
            </div>

            <div className="form-group">
                <label htmlFor="user-password">{ i18next.t('app_user_password') }</label>
                <input type="password" className="form-control" id="user-password" value={ this.props.data.inputPassword || '' } onChange={this.getHandleChange('inputPassword')} />
                <Errors errors={ this.props.errors.inputPassword } />
            </div>

            <div className="form-group">
                <label htmlFor="user-confirm-password">{ i18next.t('app_user_confirm_password') }</label>
                <input type="password" className="form-control" id="user-name" value={ this.props.data.confirmInputPassword || '' } onChange={this.getHandleChange('confirmInputPassword')} />
                <Errors errors={ this.props.errors.confirmInputPassword } />
            </div>

            <div className="form-group">
                <label>{ i18next.t('app_user_images') }</label>
                <FileUploader actionAdd={ userImagesAdd } actionUpdate={ userImagesUpdate } actionDelete={ userImagesDelete } files={ this.props.data.images } dispatch={ store.dispatch } />
            </div>

            <div className="form-group">
                <label htmlFor="user-email">{ i18next.t('app_user_email') }</label>
                <input type="text" className="form-control" id="user-email" value={ this.props.data.email || '' } onChange={this.getHandleChange('email')} />
                <Errors errors={ this.props.errors.email } />
            </div>

            <div className="form-group">
                <label htmlFor="user-phone">{ i18next.t('app_user_phone') }</label>
                <input type="text" className="form-control" id="user-phone" value={ this.props.data.phone || '' } onChange={this.getHandleChange('phone')} />
                <Errors errors={ this.props.errors.phone } />
            </div>

            <div className="form-group">
                <label htmlFor="user-role">{ i18next.t('app_user_role') }</label>
                <Select
                    value={this.props.data.role}
                    options={this.props.roles}
                    onChange={this.getHandleSelectChange('role')}
                    />
                <Errors errors={ this.props.errors.role } />
            </div>

            <input type="submit" className="btn btn-primary" value={ i18next.t('app_entity_save') } />

            <input type="button" className="btn btn-default" onClick={ this.onCancel.bind(this) } value={ i18next.t('app_entity_cancel') } />

        </form> );

    }

}

UserForm.defaultProps = {
    isUpdate: false
};

export default withRouter(UserForm)