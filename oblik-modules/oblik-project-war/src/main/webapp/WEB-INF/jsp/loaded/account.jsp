<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-account" modelAttribute="accountBean" method="POST" 
           action="${pageContext.request.contextPath}/account/edit.html">
    <fieldset>
       <form:hidden path="accountId"/>

        <label><spring:message code="jsp.oblik.account.name"/></label>
        <form:input path="name" value="${accountBean.name}"/>
        <form:errors path="name" element="div" cssClass="alert alert-error"/>
        
        <label><spring:message code="jsp.oblik.account.currency"/></label>
        <form:select id="currency-to" path="currencyId">
            <c:forEach var="currency" items="${currencyList}">
                <form:option  value="${currency.currencyId}" label="${currency.symbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="accountId" element="div" cssClass="alert alert-error"/>

    </fieldset>
</form:form>