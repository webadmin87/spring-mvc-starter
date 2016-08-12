export function authenticationAction(data, error) {
    return {
        data,
        error,
        type: 'authentication'
    }
}

export function logoutAction() {
    return {
        type: 'logout'
    }
}