let STORAGE_KEY = "AUTH_STORAGE"
import { setAuthentication, getAuthentication, reset } from "services/auth"

export function authenticationReducer(state, action) {
    if(!state) {
        state = {
            authentication: getAuthentication()
        };
    }
    let newState = Object.assign({}, state);
    if(action.type==='authentication.login') {
        newState.authentication = action.data;
        newState.error=action.error;
        setAuthentication(action.data);
        return newState;
    } else if(action.type==='authentication.logout') {
        newState.authentication = null;
        reset();
        return newState;
    }
    return state;
}
