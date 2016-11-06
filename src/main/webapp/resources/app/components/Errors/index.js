import React from 'react'
import i18next from 'i18next'

export default class Errors extends React.Component {

    render() {

        if(!this.props.errors || this.props.errors.length == 0) {
            return null
        }
        console.log(this.props.errors)
        return <div className="alert alert-danger">
            {this.props.errors.map( (item, key) => {
                return <p key={ key }> { i18next.t(item) } </p>
            })}
        </div>

    }

}