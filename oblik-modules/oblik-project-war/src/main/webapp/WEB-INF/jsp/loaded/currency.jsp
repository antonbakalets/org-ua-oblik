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

        <label for="rate"><spring:message code="jsp.oblik.currency.rate"/></label>
        <c:choose>
            <c:when test="${currencyBean.defaultRate}">
                <input id="rate" type="text" disabled="disabled" value="${currencyBean.rate}"/>
                <form:hidden path="rate"/>
            </c:when>
            <c:otherwise>
                <form:input path="rate"/>
                <form:errors path="rate" element="div" cssClass="alert alert-error"/>
            </c:otherwise>
        </c:choose>
    </fieldset>
</form:form>