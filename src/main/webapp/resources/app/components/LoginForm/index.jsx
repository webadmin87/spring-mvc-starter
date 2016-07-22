import React from 'react'

export default class LoginForm extends React.Component {

    __login() {
        this.props.login(this.refs.username.value, this.refs.password.value);
    }

    render() {
        return <div className="login-form">
            <form>
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input className="form-control" type="text" name="username" ref="username"/>
                </div>

                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input className="form-control" type="password" name="password" ref="password"/>
                </div>

                <div className="form-group">
                    <input type="button" className="btn btn-primary" onClick={ this.__login.bind(this) } value="Login"/>
                </div>

            </form>

        </div>;
    }


}