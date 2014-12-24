<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-currency" modelAttribute="currencyBean" method="POST" 
           action="${pageContext.request.contextPath}/currency/edit.html">

    <form:hidden path="currencyId"/>
    <form:hidden path="defaultRate"/>
    <form:hidden path="oldSymbol"/>

    <div class="form-group">
        <label class="sr-only"><spring:message var="labelSymbol" code="jsp.oblik.currency.symbol"/></label>
        <form:input path="symbol" placeholder="${labelSymbol}" cssClass="form-control"/>
        <form:errors path="symbol" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <label class="sr-only"><spring:message var="labelRate" code="jsp.oblik.currency.rate"/></label>
        <c:choose>
            <c:when test="${currencyBean.defaultRate}">
                <p class="form-control-static">${currencyBean.rate}</p>
                <form:hidden path="rate"/>
            </c:when>
            <c:otherwise>
                <form:input path="rate" placeholder="${labelRate}" cssClass="form-control"/>
                <form:errors path="rate" element="div" cssClass="alert alert-danger"/>
            </c:otherwise>
        </c:choose>
    </div>
</form:form>