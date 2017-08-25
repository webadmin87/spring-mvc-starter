import React from 'react'
import LoginPanel from 'containers/LoginPanel'
import { connect } from "react-redux"
import axios from "axios"
import i18next from 'i18next'
import SETTINGS from 'settings'
import { getAuthentication } from "services/auth"
import { withRouter } from "react-router-dom"

class App extends React.Component {

    constructor(props) {
        super(props)
        this.isReady = false
        this.__configureAxios()
    }

    componentWillMount() {
        axios.get(`resources/app/messages/${SETTINGS.LANGUAGE}.json`).then(r=>{
            this.__initLanguage(SETTINGS.LANGUAGE, r.data)
            this.__isRedirect(this.props)
        })
    }

    componentWillReceiveProps() {
        this.__isRedirect(this.props)
    }

    __configureAxios() {

        axios.interceptors.request.use(config => {
            let auth = getAuthentication()
            if(auth) {
                config.headers['X-AUTH-TOKEN'] = auth.token
            }
            return config;
        }, error => {
            return Promise.reject(error);
        });

        axios.interceptors.response.use(response => {
            return response;
        }, error => {
            if(error.status == 401 && this.props.location.pathname != '/login') {
                this.props.history.push("/login")
            }
            return Promise.reject(error);
        });

    }

    __isRedirect(props) {
        if(!props.authentication && this.props.location.pathname != '/login') {
            this.props.history.push("/login")
        }
    }

    __initLanguage(lng, data) {
        i18next.init({
            lng: lng,
            resources: data
        }, (err, t) => {
            this.isReady = true
            this.forceUpdate()
        })
    }

    render() {

        if(!this.isReady) {
            return null
        }

        return <div>
            <nav className="navbar-inverse navbar" role="navigation">
                <div className="container">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle" data-toggle="collapse" data-target="#w0-collapse"><span
                            className="sr-only">Toggle navigation</span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <a href="" className="navbar-brand">Spring mvc starter</a></div>
                    <div id="w0-collapse" className="collapse navbar-collapse">
                        <LoginPanel />
                    </div>
                </div>
            </nav>
            <div className="container">
                <div className="row">
                    <div className="col-xs-12">
                        {this.props.children}
                    </div>
                </div>
            </div>
        </div>;
    }

}

const mapStateToProps = function(store) {
    return {
        authentication: store.authenticationState.authentication
    }
}

export default withRouter(connect(mapStateToProps)(App))
