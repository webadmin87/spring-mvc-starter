<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html ng-app="springMvcStarter" lang="ru">
<head>
    <script type="text/javascript" src="resources/node_modules/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/underscore/underscore-min.js"></script>
    <script type="text/javascript" src="resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular/angular.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-resource/angular-resource.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-bootstrap/ui-bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-file-upload/dist/angular-file-upload.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-i18n/angular-locale_ru.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-ui-router/release/angular-ui-router.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-ui-grid/ui-grid.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-messages/angular-messages.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/textangular/dist/textAngular-rangy.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/textangular/dist/textAngular-sanitize.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/textangular/dist/textAngular.min.js"></script>
    <script type="text/javascript" src="resources/node_modules/angular-translate/dist/angular-translate.min.js"></script>

    <c:choose>
        <c:when test="${profiles[0] == 'prod'}">
            <script type="text/javascript" src="resources/build/js/packed.min.js"></script>
        </c:when>
        <c:otherwise>
            <script type="text/javascript" src="resources/build/js/packed.js"></script>
        </c:otherwise>
    </c:choose>

    <link rel="stylesheet" href="resources/node_modules/angular-ui-grid/ui-grid.min.css"/>
    <link rel="stylesheet" href="resources/node_modules/bootstrap/dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="resources/node_modules/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="resources/node_modules/textangular/dist/textAngular.css"/>

    <link rel="stylesheet" href="resources/build/css/styles.css"/>
</head>
<body>

<nav class="navbar-inverse navbar" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#w0-collapse"><span
                    class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" ui-sref="main">Spring mvc starter</a></div>
        <div id="w0-collapse" class="collapse navbar-collapse">
            <ul class="navbar-nav navbar-right nav" ng-controller="UserInfoCtrl">
                <li ng-show="!isAuth()"><a ui-sref="login">Вход</a></li>
                <li ng-show="isAuth()"><a href="" ng-click="logout()">Выход ({{getUser().username}})</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-xs-12" ui-view></div>
    </div>
</div>


</body>
</html>
