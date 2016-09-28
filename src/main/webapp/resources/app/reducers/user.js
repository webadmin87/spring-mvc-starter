import { getDefaultGridState } from 'core/GridBase'

export function userReducer(state, action) {
    if(!state) {
        state = getDefaultGridState()
    }
    let newState = Object.assign({}, state)
    if(action.type=='user.list') {
        Object.assign(newState, action.data)
        return newState
    }
    return state
}
