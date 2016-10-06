import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userListAction } from "actions/user"
import {encode} from "querystring"
import store from "store"
import i18next from 'i18next'
import { GridBase, getMapToStateFunction } from 'core/GridBase'
import { Link } from "react-router"

class UserList extends GridBase {

    __getUrl() {
        return 'admin/user/'
    }

    __getColumns() {
        return  ["id", "name", "username", "email", "role"]
    }

    __getAction() {
        return userListAction
    }

    __getStore() {
        return store
    }

    render() {
        let grid = super.render();
        return <div>
            <Link to="/userAdd" className="btn btn-primary">{ i18next.t('app_entity_add') }</Link>
            <hr />
            { grid }
        </div>
    }

}

UserList.contextTypes = {
    router: React.PropTypes.object.isRequired
}

const mapStateToProps = getMapToStateFunction('userState')

export default connect(mapStateToProps)(UserList)