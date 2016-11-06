import { getDefaultGridState } from 'core/GridBase'

export function userReducer(state, action) {
    if(!state) {
        state = getDefaultGridState()
        state.entity = {
            images: []
        }
        state.errors = {}
        state.roles = []
    }
    let newState = Object.assign({}, state)
    if(action.type=='user.list') {
        Object.assign(newState, action.data)
        return newState
    } else if (action.type=='user.add') {
        newState.entity = Object.assign({}, newState.entity, action.data)
        return newState
    } else if (action.type=='user.error') {
        newState.errors = action.data
        return newState
    } else if(action.type=='user.server.error') {
        newState.serverErrors = action.data
        return newState
    } else if(action.type=='user.roles.loaded') {
        let roles = []
        action.data.forEach(v => {
            roles.push({
                    value: v,
                    label: v
                }
            )
        })
        newState.roles = roles
        return newState
    }
    return state
}
