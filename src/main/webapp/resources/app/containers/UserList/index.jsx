import React from 'react'
import axios from 'axios'
import { connect } from "react-redux"
import { userListAction } from "actions/user"
import {encode} from "querystring"
import store from "store"
import i18next from 'i18next'
import Griddle from 'griddle-react'

class UserList extends React.Component {

    __getData() {

        let params = {
            page: this.props.currentPage+1
        }

        if(this.props.externalSortColumn) {
            Object.assign(params, {
                sortField: this.props.externalSortColumn,
                sortDirection: this.props.externalSortAscending?'ASC':'DESC'
            })
        }

        if(this.props.externalResultsPerPage) {
            Object.assign(params, {
                pageSize: this.props.externalResultsPerPage
            })
        }

        axios.get('admin/user/', {params}).then(r => {
            store.dispatch(userListAction({results: r.data, maxPages: r.headers['x-pagination-total-pages'], loadData: false}))
        })
    }

    //what page is currently viewed
    setPage(index){
        store.dispatch(userListAction({currentPage: index, loadData: true}))
    }

    //this will handle how the data is sorted
    sortData(sort, sortAscending, data){
        store.dispatch(userListAction({externalSortColumn: sort, externalSortAscending: sortAscending, loadData: true}))
    }

    //this changes whether data is sorted in ascending or descending order
    changeSort(sort, sortAscending){
        store.dispatch(userListAction({externalSortColumn: sort, externalSortAscending: sortAscending, loadData: true}))
    }

    //this method handles the filtering of the data
    setFilter(filter){
    }

    //this method handles determining the page size
    setPageSize(size){
        store.dispatch(userListAction({externalResultsPerPage: size, currentPage: 0, loadData: true}))
    }


    render() {

        if(this.props.loadData) {
            this.__getData()
        }

        return <Griddle useExternal={true} externalSetPage={this.setPage}
                        externalChangeSort={this.changeSort} externalSetFilter={this.setFilter}
                        externalSetPageSize={this.setPageSize} externalMaxPage={this.props.maxPages}
                        externalCurrentPage={this.props.currentPage} results={this.props.results}
                        resultsPerPage={this.props.externalResultsPerPage}
                        externalSortColumn={this.props.externalSortColumn}
                        externalSortAscending={this.props.externalSortAscending}
                        showFilter={true} showSettings={true} columns={["id", "name", "username", "email", "role"]} />
    }

}

UserList.contextTypes = {
    router: React.PropTypes.object.isRequired
}

const mapStateToProps = function(store) {

    return {
        results: store.userState.results,
        currentPage: store.userState.currentPage,
        maxPages: store.userState.maxPages,
        externalResultsPerPage: store.userState.externalResultsPerPage,
        externalSortColumn: store.userState.externalSortColumn,
        externalSortAscending: store.userState.externalSortAscending,
        loadData: store.userState.loadData
    }

}

export default connect(mapStateToProps)(UserList)