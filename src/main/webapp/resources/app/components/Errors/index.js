import React from 'react'
import i18next from 'i18next'

export default class Errors extends React.Component {

    render() {

        if(!this.props.errors || this.props.errors.length == 0) {
            return null
        }

        let str = "111"
        this.props.errors.map(item => {
            str += <p> + i18next.t(item) + </p>
        })

        return <div className="bg-danger">
            { str }
        </div>

    }

}