<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-currency" modelAttribute="currencyBean" method="POST" 
           action="${pageContext.request.contextPath}/currency/edit.html">
    <fieldset>
        <form:hidden path="currencyId"/>
        <form:hidden path="defaultRate"/>

        <label><spring:message code="jsp.oblik.currency.symbol"/></label>
        <form:input path="symbol"/>
        <form:errors path="symbol" element="div" cssClass="alert alert-error"/>

        <label><spring:message code="jsp.oblik.currency.rate"/></label>
        <form:input path="rate" disabled="${currencyBean.defaultRate}"/>
        <form:errors path="rate" element="div" cssClass="alert alert-error"/>
    </fieldset>
</form:form>