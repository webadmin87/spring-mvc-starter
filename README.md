# Spring mvc starter

Каркас для создание одностраничных REST приложений на основе Spring MVC и ReactJS. Содержит сконфигурированные Spring MVC + Spring Data JPA + Hibernate + Spring Security + Ehcache + ReactJS.
Представляет собой приложение со следующим функиционалом:

* Авторизация по токену. Получение токена происходит после аутентификации по логину и паролю. Токен сохраняется на клиентской части приложения.
* Отображение списка пользователей посредством. Постраничная навигация и сортировка реализованы на стороне сервера.
* Создание и редактирование пользователей с возможностью загрузки файлов.
* Удаление пользователей.

Также сконфигурирована отправка почты и maven плагины необходимые для сборки и запуска проекта (jetty, tomcat 7, exec). При развертывании приложения создается пользователь **admin** с паролем **password**.

## Системные требования

* JAVA 8
* Maven 3
* NPM 1.3

## Настройка производственной среды для Tomcat 7

### В server.xml

Конфигурируем DataSource:

```
<GlobalNamingResources>

    <Resource name="jdbc/ResourceDB" auth="Container" type="javax.sql.DataSource"
              username="postgres" password="xh48u56"
              url="jdbc:postgresql://192.168.56.101:5432/dbname"
              driverClassName="org.postgresql.Driver"
              initialSize="10" 
              maxWait="10000"
              maxActive="200" 
              maxIdle="50"
              validationQuery="select 1"
              poolPreparedStatements="true"
              removeAbandoned="true"
              removeAbandonedTimeout="60"
              logAbandoned="true"
              />


</GlobalNamingResources>
```

### В context.xml

Подключаем ресурс к контексту:

```
<ResourceLink name="jdbc/ResourceDB" global="jdbc/ResourceDB" type="javax.sql.DataSource"/>
```

### В catalina.sh

Экспортируем переменную окружения, содержащую путь к папке хранящей пользовательские файлы приложения:

```
export TOMCAT_USERFILES_DIR=/path/to/userfiles
```

## Настрйки среды разработки

Задать переменную окружения, содержащую путь к папке хранящей пользовательские файлы приложения:

```
export TOMCAT_USERFILES_DIR=/path/to/userfiles
```

Предпочтительно использовать Jetty. Запускать используя dev профиль для maven и spring:

```
mvn -P dev -Dspring.profiles.active="dev" -Dorg.eclipse.jetty.LEVEL=DEBUG jetty:run
```


## Общие настройка окружения

Для кеша по умолчанию используется папка **/var/cache/ehcache**. Она должна существовать.

## Особенности сборки

Перед осуществлением сборки необходимо вручную установить npm зависимости. Для этого перейти в папку **src/main/webapp/resources** и выполнить:

```
npm install
```
