import React from 'react'

export default class FormBase extends React.Component {

    validators() {
        throw new Error('Method not implemented');
    }

    getStore() {
        throw new Error('Method not implemented');
    }

    getAddAction() {
        throw new Error('Method not implemented');
    }

    getErrorAction() {
        throw new Error('Method not implemented');
    }

    validate() {
        let validators = this.validators();
        let errors = {};
        let result = true;
        Object.keys(validators).forEach(key => {
            let item = validators[key];
            item.forEach(validator => {
                if(!validator(this.props.data[key], key, this.props.data)) {
                    result = false;
                    if(!errors[key]) {
                        errors[key] = [];
                    }
                    errors[key].push(validator.message);
                }
            })
        });
        this.getStore().dispatch(this.getErrorAction()(errors));
        return result;
    }

    getHandleChange(attr) {
        return e => {
            let data = {};
            data[attr] = e.target.value || null;
            this.getStore().dispatch(this.getAddAction()(
                data
            ))
        };
    }

    getHandleSelectChange(attr) {
        return val => {
            let data = {};
            data[attr] = val.value;
            this.getStore().dispatch(this.getAddAction()(
                data
            ))
        };
    }

    handleSubmit(e) {
        e.preventDefault();
        if(this.validate()) {
            this.props.handleSubmit(e);
        }
    }

}