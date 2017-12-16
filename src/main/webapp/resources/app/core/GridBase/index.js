import React from 'react'
import axios from 'axios'
import Griddle from 'griddle-react'

export class GridBase extends React.Component {

    __getUrl() {
        throw  new Error('Method not implemented');
    }

    __getColumns() {
        throw  new Error('Method not implemented');
    }

    __getAction() {
        throw  new Error('Method not implemented');
    }

    __getStore() {
        throw  new Error('Method not implemented');
    }

    __getColumnMeta() {
        return [];
    }

    __getData() {

        let params = {
            page: this.props.currentPage+1
        };

        if(this.props.externalSortColumn) {
            Object.assign(params, {
                sortField: this.props.externalSortColumn,
                sortDirection: this.props.externalSortAscending?'ASC':'DESC'
            })
        }

        if(this.props.externalResultsPerPage) {
            Object.assign(params, {
                pageSize: this.props.externalResultsPerPage
            });
        }

        axios.get(this.__getUrl(), {params}).then(r => {
            if(r.data.length == 0 && this.props.currentPage > 0) {
                this.setPage(this.props.currentPage-1);
            } else {
                this.__getStore().dispatch(this.__getAction()({
                    results: r.data,
                    maxPages: r.headers['x-pagination-total-pages'],
                    loadData: false
                }));
            }
        }).catch(err=>{
            console.warn('Unable to load data');
        });
    }

    //what page is currently viewed
    setPage(index){
        this.__getStore().dispatch(this.__getAction()({currentPage: index, loadData: true}));
    }

    //this will handle how the data is sorted
    sortData(sort, sortAscending, data){
        this.__getStore().dispatch(this.__getAction()({externalSortColumn: sort, externalSortAscending: sortAscending, loadData: true}));
    }

    //this changes whether data is sorted in ascending or descending order
    changeSort(sort, sortAscending){
        this.__getStore().dispatch(this.__getAction()({externalSortColumn: sort, externalSortAscending: sortAscending, loadData: true}));
    }

    //this method handles the filtering of the data
    setFilter(filter){
    }

    //this method handles determining the page size
    setPageSize(size){
        this.__getStore().dispatch(this.__getAction()({externalResultsPerPage: size, currentPage: 0, loadData: true}));
    }

    render() {

        if(this.props.loadData) {
            this.__getData();
        }

        return ( <Griddle columnMetadata={this.__getColumnMeta()} useExternal={true} externalSetPage={this.setPage.bind(this)}
                        externalChangeSort={this.changeSort.bind(this)} externalSetFilter={this.setFilter.bind(this)}
                        externalSetPageSize={this.setPageSize.bind(this)} externalMaxPage={this.props.maxPages}
                        externalCurrentPage={this.props.currentPage} results={this.props.results}
                        resultsPerPage={this.props.externalResultsPerPage}
                        externalSortColumn={this.props.externalSortColumn}
                        externalSortAscending={this.props.externalSortAscending}
                        showFilter={false} showSettings={false} columns={this.__getColumns()} /> );
    }

}

export function getMapToStateFunction(name) {
    return store => {
        return {
            results: store[name].results,
            currentPage: store[name].currentPage,
            maxPages: store[name].maxPages,
            externalResultsPerPage: store[name].externalResultsPerPage,
            externalSortColumn: store[name].externalSortColumn,
            externalSortAscending: store[name].externalSortAscending,
            loadData: store[name].loadData
        }

    }
}

export function getDefaultGridState() {
    return {
        results: [],
        currentPage: 0,
        maxPages: 0,
        externalResultsPerPage: 2,
        externalSortColumn: null,
        externalSortAscending: true,
        loadData: true
    }
}

export function addActionColumn(results) {
    if(results) {
        results.forEach(item => {
            item.action = true
        })
    }
}

export class ActionColumn extends React.Component {

    onDelete(e) {
        throw  new Error('Method not implemented');
    }

    onEdit(e) {
        throw  new Error('Method not implemented');
    }

    render() {
        return (
            <div>
                <a href="" onClick={ this.onEdit.bind(this) }><i className="glyphicon glyphicon-pencil"></i></a>
                <a href="" onClick={ this.onDelete.bind(this) }><i className="glyphicon glyphicon-trash"></i></a>
            </div>
        );
    }

}