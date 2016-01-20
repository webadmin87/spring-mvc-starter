# Spring mvc starter

Каркас для создание одностраничных REST приложений на основе Spring MVC и AngularJS. Содержит сконфигурированные Spring MVC + Spring Data JPA + Hibernate + Spring Security + Ehcache + AngularJS.
Также сконфигурирована аутентификация по токену, загрузка файлов, отправка почты, необходимые maven плагины. При развертывании приложения создается пользователь **admin** с паролем **password**.

## Настройка производственной среды для Tomcat 7

### В server.xml

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

```
<ResourceLink name="jdbc/ResourceDB" global="jdbc/ResourceDB" type="javax.sql.DataSource"/>
```

### В catalina.sh

```
export TOMCAT_USERFILES_DIR=/opt/tomcat-7/userfiles
```

## Настрйки среды разработки

Предпочтительно использовать Jetty. Запускать используя dev профиль для maven и spring:

```
mvn -P dev -Dspring.profiles.active="dev" -Dorg.eclipse.jetty.LEVEL=DEBUG jetty:run
```

Задать через переменную окружения папку, в которой должны размещаться пользовательские файлы:

```
TOMCAT_USERFILES_DIR=/path/to/userfiles
```

## Общие настройка окружения

Для кеша по умолчанию используется папка **/var/cache/ehcache**. Она должна существовать.

## Особенности сборки

Перед осуществлением сборки необходимо вручную установить bower зависимости. Для этого перейти в папку **src/main/webapp** и выполнить:

```
bower install
```

Также перед запуском maven необходимо через **npm** установить зависимости указанные в **src/main/webapp/package.json**. Они необходимы для сборки фронтенда.