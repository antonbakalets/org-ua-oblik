<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<c:set var="locale" value="${pageContext.response.locale}"/>

<!DOCTYPE html>
<html lang="${locale.language}" xmlns="http://www.w3.org/1999/xhtml">
    <head> 
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery-ui-1.9.2.custom.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
        <!--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />-->

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.9.2.custom.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" />

        <title><spring:message code="jsp.oblik.title"/></title>
    </head>

    <body data-spy="scroll" data-target=".bs-docs-sidebar">
        <noscript>
            This page uses JavaScript and requires a JavaScript enabled browser.
            Your browser is not JavaScript enabled.
        </noscript>

        <%@include file="header.jsp" %>
        <%@include file="main.jsp" %>
        <%@include file="footer.jsp" %>
    </body>
</html>
