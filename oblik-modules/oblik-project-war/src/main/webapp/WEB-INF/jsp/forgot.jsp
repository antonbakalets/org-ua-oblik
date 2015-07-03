<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>

<c:set var="locale" value="${pageContext.response.locale}"/>
<html lang="${locale.language}" xmlns="http://www.w3.org/1999/xhtml">
    <head> 
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />

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

                                LALALA
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <footer>
        </footer>
    </body>
</html>

