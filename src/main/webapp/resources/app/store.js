import { createStore, combineReducers } from "redux"
import { authenticationReducer } from "reducers/authentication"
import { userReducer } from "reducers/user"

export default createStore(combineReducers({
    authenticationState: authenticationReducer,
    userState: userReducer
}))