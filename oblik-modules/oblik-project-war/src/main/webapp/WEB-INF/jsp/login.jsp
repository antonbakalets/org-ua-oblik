<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:set var="locale" value="${pageContext.response.locale}"/>
<html lang="${locale.language}" xmlns="http://www.w3.org/1999/xhtml">
    <head> 
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/magic-bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" />

        <title><spring:message code="jsp.oblik.title"/></title>
    </head>

    <body>
        <noscript>
            This page uses JavaScript and requires a JavaScript enabled browser.
            Your browser is not JavaScript enabled.
        </noscript>

        <header>
            <br/>
            <br/>
            <br/>
        </header>>
        <div class="container clearfix">
            <form method="POST" action="<c:url value="/j_spring_security_check"/>" class="form-horizontal">
                <fieldset>
                    <div id="legend">
                        <legend class=""><spring:message code="login.title"/></legend>
                    </div>

                    <c:if test="${!empty messageCode}">
                        <div class="alert alert-success">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <spring:message code="${messageCode}"/>
                        </div>
                    </c:if>

                    <c:if test="${!empty SPRING_SECURITY_LAST_EXCEPTION.message}">
                        <div class="alert alert-error">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                        </div>
                    </c:if>

                    <div class="control-group">
                        <label class="control-label"  for="username"><spring:message code="login.username"/>:</label>
                        <div class="controls">
                            <input type="text" id="username" name="j_username" placeholder="" class="input-xlarge" autocomplete="off">
                        </div>
                    </div>

                    <div class="control-group">
                        <label class="control-label" for="password"><spring:message code="login.password"/>:</label>
                        <div class="controls">
                            <input type="password" id="password" name="j_password" placeholder="" class="input-xlarge">
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <label class="checkbox" for="password">
                                <input id="login-rememberme" type="checkbox" name="_spring_security_remember_me" />
                                <spring:message code="login.remember"/>
                            </label>
                        </div>
                    </div>

                    <div class="control-group">
                        <div class="controls">
                            <input id="login-submit" type="submit" value="<spring:message code="login.button.login"/>" class="btn btn-success"/>
                            <a href="#"><spring:message code="login.forgot.password"/></a>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
        <footer>
            <br/>
            <br/>
            <%--<a href="${pageContext.request.contextPath}/registration/register.html">Зареєструватися</a>--%>
        </footer>
    </body>
</html>

