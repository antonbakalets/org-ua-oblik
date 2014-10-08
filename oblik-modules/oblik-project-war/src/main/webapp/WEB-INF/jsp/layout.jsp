<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<c:set var="locale" value="${pageContext.response.locale}"/>

<!DOCTYPE html>
<html lang="${locale.language}" xmlns="http://www.w3.org/1999/xhtml">
    <head> 
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,400,600&subset=latin,cyrillic-ext,cyrillic'/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/datepicker3.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/normalize.css" />

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.11.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ineditable.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/calculable.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>

        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" />

        <title><spring:message code="jsp.oblik.title"/></title>
    </head>

    <body data-spy="scroll" data-target=".bs-docs-sidebar">
        <noscript>
            This page uses JavaScript and requires a JavaScript enabled browser.
            Your browser is not JavaScript enabled.
        </noscript>

        <div class="container">
            <%@include file="header.jsp" %>
            <%@include file="main.jsp" %>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
