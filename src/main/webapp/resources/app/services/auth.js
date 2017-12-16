let STORAGE_KEY = "AUTH_STORAGE"

export let setAuthentication = function (a) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(a));
}

export let getAuthentication = function () {
    return JSON.parse(localStorage.getItem(STORAGE_KEY));
}

export let reset = function () {
    localStorage.removeItem(STORAGE_KEY);
}

export let isAuth = function() {
    return getAuthentication() != null;
}

export let hasRole = function(roles) {
    let user = getAuthentication();
    if(!user) {
        return false;
    }
    return roles.indexOf(user.role) > -1;
}