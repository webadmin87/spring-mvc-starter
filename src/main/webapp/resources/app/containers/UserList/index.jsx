import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userListAction } from "actions/user"
import {encode} from "querystring"
import store from "store"
import i18next from 'i18next'
import { GridBase, getMapToStateFunction, ActionColumn } from 'core/GridBase'
import { Link } from "react-router"
import Settings from "settings"

class UserList extends GridBase {

    __getUrl() {
        return Settings.USERS_URL
    }

    __getColumns() {
        return  ["id", "name", "username", "email", "role", "action"]
    }

    __getAction() {
        return userListAction
    }

    __getStore() {
        return store
    }

    __getColumnMeta() {
        return [
            {
                "columnName": "action",
                "customComponent": UserActionColumn,
                "visible": true,
                "locked": false,
                "sortable": false
            }

        ]
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

class UserActionColumn extends ActionColumn {

    onDelete(e) {
        e.preventDefault();
        if(confirm('Delete record?')) {
            axios.delete(Settings.USERS_URL + this.props.rowData.id + '/')
                .then(r => {
                    store.dispatch(userListAction({loadData: true}))
                })
                .catch(r => {
                    alert(r.data || 'Delete error')
                })
        }
    }

    onEdit(e) {
        e.preventDefault()
        this.context.router.push('/userEdit/' + this.props.rowData.id + '/');
    }

}

UserActionColumn.contextTypes = {
    router: React.PropTypes.object.isRequired
}

UserList.contextTypes = {
    router: React.PropTypes.object.isRequired
}

const mapStateToProps = getMapToStateFunction('userState')

export default connect(mapStateToProps)(UserList)