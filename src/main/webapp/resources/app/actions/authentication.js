export function authenticationAction(data, error) {
    return {
        data,
        error,
        type: 'authentication.login'
    }
}

export function logoutAction() {
    return {
        type: 'authentication.logout'
    }
}