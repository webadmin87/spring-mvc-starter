export function authenticationAction(data) {
    return {
        data,
        type: 'authentication'
    }
}

export function logoutAction() {
    return {
        type: 'logout'
    }
}