function notEmpty(value) {
    return value != null && value != '' && typeof(value) != 'undefined'
}

notEmpty.message = 'app_validator_not_empty'

function username(value) {
    let re = /^[A-z0-9_-]+$/;
    return !notEmpty(value) || re.test(value)
}

username.message = 'app_validator_username'

function phone(value) {
    let re = /^7[0-9]{10}$/;
    return !notEmpty(value) || re.test(value)
}

phone.message = 'app_validator_phone'

function password(value) {
    return !notEmpty(value) || value.length >= 6
}

password.message = 'app_validator_password'

function confirmPassword(value, attr, data) {
    return value == data[attr]
}

confirmPassword.message = 'app_validator_confirm_password'

function email(value) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(value)
}

email.message = 'app_validator_email'

export { notEmpty, password, confirmPassword, email, phone, username}