<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-currency" modelAttribute="currencyBean" method="POST" 
           action="${pageContext.request.contextPath}/currency/edit.html">
    <fieldset>
        <form:hidden path="currencyId"/>

        <label><spring:message code="jsp.oblik.currency.symbol"/></label>
        <form:input path="symbol"/>
        <form:errors path="symbol" element="div" cssClass="alert alert-error"/>

        <label><spring:message code="jsp.oblik.currency.rate"/></label>
        <form:input path="rate"/>
        <form:errors path="rate" element="div" cssClass="alert alert-error"/>
    </fieldset>
</form:form>