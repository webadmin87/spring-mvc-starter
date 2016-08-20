export function userReducer(state, action) {
    if(!state) {
        state = {
            results: [],
            currentPage: 0,
            maxPages: 0,
            externalResultsPerPage: 2,
            externalSortColumn:null,
            externalSortAscending: true,
            loadData: true
        }
    }
    let newState = Object.assign({}, state)
    if(action.type=='user.list') {
        Object.assign(newState, action.data)
        return newState
    }
    return state
}
