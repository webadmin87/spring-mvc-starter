import { getDefaultGridState } from 'core/GridBase'

export function userReducer(state, action) {
    if(!state) {
        state = getDefaultGridState()
        state.entity = {
            images: []
        }
        state.errors = {}
    }
    let newState = Object.assign({}, state)
    if(action.type=='user.list') {
        Object.assign(newState, action.data)
        return newState
    } else if (action.type=='user.add') {
        Object.assign(newState.entity, action.data)
        return newState
    } else if (action.type=='user.error') {
        newState.errors = action.data
        return newState
    }
    return state
}
