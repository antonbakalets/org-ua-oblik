<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="fabType" value="${fn:toLowerCase(formActionBean.type)}"/>


<form:form id="form-${fabType}"
           modelAttribute="formActionBean" method="POST" 
           action="${pageContext.request.contextPath}/formaction.html">
    <form:hidden path="txId"/>
    <form:hidden path="type"/>

    <div class="form-group">
        <label class="sr-only"><spring:message var="accountLabel" code="jsp.oblik.account"/></label>
        <form:select id="account-from-${fabType}" path="firstAccount" cssClass="form-control">
            <c:if test="${empty formActionBean.txId}">
                <form:option value="" label="${accountLabel}" disabled="disabled" selected="selected"/>
            </c:if>
            <c:forEach var="account" items="${accountFromItems}">
                <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="firstAccount" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <label class="sr-only"><spring:message var="firstAmountLabel" code="jsp.oblik.ammount"/></label>
        <div class="input-group">
            <form:input id="firstAmmount-${fabType}" path="firstAmmount" cssClass="form-control calculable" placeholder="${firstAmountLabel}"/>
            <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
        </div>
        <form:errors path="firstAmmount" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <label class="sr-only"><spring:message var="dateLabel" code="jsp.oblik.date"/></label>
        <div class="input-group">
            <form:input id="date-${fabType}" path="date" cssClass="form-control datepicker" placeholder="${dateLabel}"/>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"/></span>
        </div>
        <form:errors path="date" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <label class="sr-only"><spring:message var="accountLabel" code="jsp.oblik.account"/></label>
        <form:select id="account-to-${fabType}" path="secondAccount" cssClass="form-control">
            <c:if test="${empty formActionBean.txId}">
                <form:option value="" label="${accountLabel}" disabled="disabled" selected="selected"/>
            </c:if>
            <c:forEach var="account" items="${accountToItems}">
                <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="secondAccount" element="div" cssClass="alert alert-danger"/>
    </div>

    <c:if test="${formActionBean.type == 'TRANSFER'}">
        <div id="second-ammount-div-${fabType}" class="form-group">
            <label class="sr-only"><spring:message var="secondAmountLabel" code="jsp.oblik.expense.ammount"/></label>
            <div class="input-group">
                <form:input id="secondAmmount-${fabType}" path="secondAmmount" cssClass="form-control calculable" placeholder="${secondAmountLabel}"/>
                <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
            </div>
            <form:errors path="secondAmmount" element="div" cssClass="alert alert-danger"/>
        </div>
    </c:if>

    <div class="form-group">
        <label class="sr-only"><spring:message var="noteLabel" code="jsp.oblik.note"/></label>
        <form:textarea id="note-${fabType}" path="note" cssClass="form-control" placeholder="${noteLabel}"/>
        <form:errors path="note" element="div" cssClass="alert alert-danger"/>
    </div>

    <div class="form-group">
        <button id="form-${fabType}-button" type="button" class="btn btn-primary"><spring:message code="jsp.oblik.button.save"/></button>
    </div>
</form:form>