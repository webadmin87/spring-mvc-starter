export function userListAction(data) {
    return {
        data,
        type: 'user.list'
    }
}

export function userAddAction(data) {
    return {
        data,
        type: 'user.add'
    }
}

export function userErrorAction(data) {
    return {
        data,
        type: 'user.error'
    }
}

export function userServerErrorAction(data) {
    return {
        data,
        type: 'user.server.error'
    }
}

export function userRolesLoadedAction(data) {
    return {
        data,
        type: 'user.roles.loaded'
    }
}

export function userEntityLoad(data) {
    return {
        data,
        type: 'user.entity.load'
    }
}

export function userEntityReset(data) {
    return {
        data,
        type: 'user.entity.reset'
    }
}

export function userImagesAdd(data) {
    return {
        data,
        type: 'user.entity.imagesadd'
    }
}

export function userImagesUpdate(data) {
    return {
        data,
        type: 'user.entity.imagesupdate'
    }
}