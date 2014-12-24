<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-account" modelAttribute="accountBean" method="POST" 
           action="${pageContext.request.contextPath}/account/edit.html">

    <form:hidden path="accountId"/>
    <form:hidden path="ammount"/>
    <form:hidden path="oldName"/>
    <form:hidden path="kind"/>

    <div class="form-group">
        <label class="sr-only"><spring:message var="labelName" code="jsp.oblik.account.name"/></label>
        <form:input path="name" value="${accountBean.name}" placeholder="${labelName}" cssClass="form-control"/>
        <form:errors path="name" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <label class="sr-only"><spring:message var="labelCurrency" code="jsp.oblik.account.currency"/></label>
        <form:select id="currency-to" path="currencyId" placeholder="labelCurrency" cssClass="form-control">
            <c:forEach var="currency" items="${currencyList}">
                <form:option  value="${currency.currencyId}" label="${currency.symbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="currencyId" element="div" cssClass="alert alert-danger"/>
    </div>
</form:form>