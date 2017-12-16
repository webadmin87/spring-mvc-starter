import React from 'react'
import i18next from 'i18next'

export default class LoginForm extends React.Component {

    __login() {
        this.props.login(this.refs.username.value, this.refs.password.value);
    }

    render() {
        return ( <div className="login-form">

            { this.props.error?<div className="alert alert-danger">{ this.props.error }</div>:null }

            <form>
                <div className="form-group">
                    <label htmlFor="username">{ i18next.t('app_login_form_username') }</label>
                    <input className="form-control" type="text" name="username" ref="username"/>
                </div>

                <div className="form-group">
                    <label htmlFor="password">{ i18next.t('app_login_form_password') }</label>
                    <input className="form-control" type="password" name="password" ref="password"/>
                </div>

                <div className="form-group">
                    <input type="button" className="btn btn-primary" onClick={ this.__login.bind(this) } value={ i18next.t('app_login_form_enter') } />
                </div>

            </form>

        </div> );
    }


}