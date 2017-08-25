import React from 'react'
import { Link } from "react-router-dom"

export default class Start extends React.Component {

    render() {
        return (
            <div className="alert alert-info">
                Главная страница приложения. Для просмотра списка пользователей, перейдите по <Link to="/users">ссылке</Link>
            </div>
        );
    }

}