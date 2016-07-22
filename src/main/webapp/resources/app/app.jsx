import React from 'react'
import LoginPanel from 'containers/LoginPanel'

export default class App extends React.Component {

    render() {
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
                        <a className="navbar-brand">Spring mvc starter</a></div>
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
