<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:set var="locale" value="${pageContext.response.locale}"/>
<html lang="${locale.language}" xmlns="http://www.w3.org/1999/xhtml">
    <head> 
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
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
            <div class="page-header">
                <a href="https://github.com/antonbakalets/org-ua-oblik">
                    <img style="position: absolute; top: 0; right: 0; border: 0; z-index: 1;"
                         src="https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png"
                         alt="Fork me on GitHub">
                </a>
                
                <!-- TODO <h1>putt title here</h1> -->
            </div>
        </header>

        <div class="container">
            <div class="row vertical-offset-100">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h2 class="panel-title"><spring:message code="login.title"/></h2>
                        </div>
                        <div class="panel-body">
                            <form method="POST" action="<c:url value="/j_spring_security_check"/>" role="form">
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

                                <div class="form-group">
                                    <label class="sr-only"><spring:message var="labelUsername" code="login.username"/></label>
                                    <input type="text" id="username" name="j_username" placeholder="${labelUsername}" class="form-control" autocomplete="off">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" ><spring:message var="labelPassword" code="login.password"/></label>
                                    <input type="password" id="password" name="j_password" class="form-control" placeholder="${labelPassword}" value="">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input id="login-rememberme" type="checkbox" name="_spring_security_remember_me" />
                                        <spring:message code="login.remember"/>
                                    </label>
                                </div>
                                <input id="login-submit" type="submit" value="<spring:message code="login.button.login"/>" class="btn btn-lg btn-success btn-block"/>
                                <%-- TODO <a href="#"><spring:message code="login.forgot.password"/></a>--%>
                                <%-- TODO <a href="#"><spring:message code="login.register"/></a>--%>

                                    <input id="openid_identifier" name="openid_identifier" target="_top"
                                           type="hidden"
                                           value="https://www.google.com/accounts/o8/id"/>
                                    <img src="../img/ico_calendar.gif" onClick="submitForm('googleOpenId')"/>
                            </form>
                        </div>
                    </div>
                    <!-- Social Sign In Buttons -->
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <div class="row social-button-row">
                                <div class="col-lg-4">
                                    <!-- Add Facebook sign in button -->
                                    <a href="${pageContext.request.contextPath}/auth/facebook"><button class="btn btn-facebook">
                                        <span class="glyphicon glyphicon-tasks"/> | facebook
                                    </button></a>
                                </div>
                            </div>
                            <div class="row social-button-row">
                                <div class="col-lg-4">
                                    <!-- Add Twitter sign in Button -->
                                    <a href="${pageContext.request.contextPath}/auth/twitter"><button class="btn btn-twitter">
                                        <span class="glyphicon glyphicon-tasks"/> | twitter
                                    </button></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
            <br/>
            <br/>
            <%--<a href="${pageContext.request.contextPath}/registration/register.html">Зареєструватися</a>--%>
        </footer>
    </body>
</html>

