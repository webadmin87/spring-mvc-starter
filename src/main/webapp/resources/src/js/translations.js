(function(angular){

    angular.module('springMvcStarter')
        .config(["$translateProvider", function ($translateProvider) {

            $translateProvider.translations('ru', {

                'Users': 'Пользователи',
                'Login error': 'Ошибка входа',
                'Username': 'Имя пользователя',
                'Password': 'Пароль',
                'Login': 'Вход',
                'Not found': '404 страница не найдена',
                'Add': 'Создать',
                'Save': 'Сохранить',
                'Cancel': 'Отменить',
                'Error saving': 'Ошибка сохранения',
                'Active': 'Активность',
                'Fio': 'ФИО',
                'Confirm password': 'Подтверждение пароля',
                'Avatar': 'Аватар',
                'Phone': 'Телефон',
                'Text': 'Текс',
                'Email': 'Электронный адрес',
                'Role': 'Роль',
                'Actions': 'Действия',
                'Field is required': 'Поле обязательно к заполнению',
                'Invalid field value': 'Неверное значение поля',
                'Filed to short': 'Значение слишком короткое',
                'Field to long': 'Значение слишком длинное',
                'Passwords does not match': 'Пароли не совпадают',
                'Email incorrect': 'Элекстронный адрес введен неверно',
                'Uploaded files': 'Загруженные файла',
                'Upload queue': 'Очередь загрузки',
                'Upload by url': 'Загрузить по URL',
                'Upload': 'Загрузить',
                'Name': 'Имя',
                'Status': 'Статус',
                'Info': 'Инфо',
                'Progress': 'Прогресс',
                'Remove': 'Удалить',
                'Confirm remove?': 'Подтвердить удаление?'

            });

            $translateProvider.preferredLanguage('ru');

            $translateProvider.useSanitizeValueStrategy(null);

        }]);

})(angular);