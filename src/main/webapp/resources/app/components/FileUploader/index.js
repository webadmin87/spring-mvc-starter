import React from 'react'
import Dropzone from 'react-dropzone'
import Settings from 'settings'
import axios from 'axios'
import i18next from 'i18next'

export default class FileUploader extends React.Component {

    onDrop(acceptedFiles) {
        let data = new FormData();
        acceptedFiles.forEach(file => {
            data.append('file', file);;
        });
        axios.post(Settings.UPLOAD_URL, data).then(res => {

            if(!res.data || !res.data.success ) {
                alert('Upload error!')
            }

            let models = [];

            res.data.success.forEach(path => {
               models.push(
                   {
                       title: '',
                       path: path,
                       sort: 500
                   }
               );
            });

            if(models.length) {
                this.props.dispatch(this.props.actionAdd(models));
            }

            if(res.data.error && res.data.error.length) {
                let msg = i18next.t('app_file_uploader_error') + ' ' + res.data.error.join(",");
                alert(msg);
            }

        }).catch(err => {
            console.error(err);
        });
    }

    isImage(type) {
        return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
    }

    isImageUrl(file) {
        let type = file.split('.').pop().toLowerCase() + '|';
        return this.isImage(type);
    }

    getHandleChange(name, i, def) {
        return e => {
            let file = Object.assign({}, this.props.files[i]);
            file[name] = e.target.value || def;
            this.props.dispatch(
                this.props.actionUpdate({
                    i: i,
                    model: file
                })
            );
        };
    }

    getHandleDelete(i) {
        return e => {
            e.preventDefault();
            this.props.dispatch(this.props.actionDelete(i));
        };
    }

    render() {
        return (
            <div className="row">
                <div className="col-xs-12 col-md-4 dropzone">
                    <Dropzone onDrop={this.onDrop.bind(this)}>
                        <p className="dropzone__info">{ i18next.t('app_file_uploader_info') }</p>
                    </Dropzone>
                </div>
                <div className="col-xs-12 col-md-8">
                    {this.props.files && this.props.files.length ?
                        <table className="table table-striped">
                            <tbody>
                                {
                                    this.props.files.map( ( f, i ) => <tr key={ f.path }>
                                        <td>{ this.isImageUrl(f.path) ? <img src={ f.path } width="60" alt="" /> : null }</td>
                                        <td><input className="form-control input-sm" type="text" value={ f.title } onChange={ this.getHandleChange("title", i, "") } /></td>
                                        <td>{ f.path }</td>
                                        <td><input className="form-control input-sm" type="text" value={ f.sort } onChange={ this.getHandleChange("sort", i, 0) } /></td>
                                        <td><a href="#" className="glyphicon glyphicon-remove" onClick={ this.getHandleDelete(i) }></a></td>
                                    </tr>)
                                }
                            </tbody>
                        </table>
                        : null }
                </div>
            </div>
        );

    }

}