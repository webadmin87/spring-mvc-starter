import React from 'react'

export default class Errors extends React.Component {

    render() {

        if(!this.props.errors || this.props.errors.length == 0) {
            return null
        }

        return <div className="alert alert-danger">
            {this.props.errors.map( (item, key) => {
                return <p key={ key }> { item.defaultMessage } </p>
            })}
        </div>

    }

}