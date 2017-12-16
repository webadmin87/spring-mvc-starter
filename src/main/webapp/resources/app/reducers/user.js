import { getDefaultGridState, addActionColumn } from 'core/GridBase'

function getInitialEntity() {
   return {
       images: []
   };
}

export function userReducer(state, action) {
    if(!state) {
        state = getDefaultGridState();
        state.entity = getInitialEntity();
        state.errors = {};
        state.serverErrors = [];
        state.roles = [];
    }
    let newState = Object.assign({}, state);
    if(action.type==='user.list') {
        addActionColumn(action.data.results);
        Object.assign(newState, action.data);
        return newState
    } else if (action.type==='user.add') {
        newState.entity = Object.assign({}, newState.entity, action.data);
        return newState;
    } else if (action.type==='user.error') {
        newState.errors = action.data;
        return newState;
    } else if(action.type==='user.server.error') {
        newState.serverErrors = action.data;
        return newState;
    } else if(action.type==='user.roles.loaded') {
        let roles = [];
        action.data.forEach(v => {
            roles.push({
                    value: v,
                    label: v
                }
            )
        });
        newState.roles = roles;
        return newState
    } else if (action.type==='user.entity.load') {
        newState.entity = Object.assign({}, action.data);
        return newState;
    } else if (action.type==='user.entity.reset') {
        newState.entity = getInitialEntity();
        newState.errors = {};
        newState.serverErrors = [];
        newState.loadData = true;
        return newState;
    } else if (action.type==='user.entity.imagesadd') {
        newState.entity = Object.assign({}, newState.entity);
        newState.entity.images = [...newState.entity.images, ...action.data];
        return newState;
    } else if (action.type==='user.entity.imagesupdate') {
        newState.entity = Object.assign({}, newState.entity);
        if(newState.entity.images[action.data.i]) {
            newState.entity.images[action.data.i] = action.data.model;
        }
        return newState;
    } else if (action.type==='user.entity.imagesdelete') {
        newState.entity = Object.assign({}, newState.entity);
        if(newState.entity.images[action.data]) {
            newState.entity.images.splice(action.data, 1);
        }
        return newState;
    }
    return state
}
