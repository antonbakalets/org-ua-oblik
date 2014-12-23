<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="disableType" value="${empty formActionBean.txId ? '' : 'disabled'}"/>

<div class="panel panel-default">
    <div id="transaction-panel" class="panel-body">
        <form:form id="form-actions"
                   modelAttribute="formActionBean" method="POST" 
                   action="${pageContext.request.contextPath}/formaction.html">
            <form:hidden path="txId"/>
            <form:hidden path="type"/>

            <div class="form-group">
                <ul id="actions-type" class="nav nav-pills nav-justified">
                    <li class="${formActionBean.type == 'EXPENSE' ? 'active' : disableType}">
                        <a href="#"><spring:message code="jsp.oblik.expense"/></a>
                    </li>
                    <li class="${formActionBean.type == 'TRANSFER' ? 'active' : disableType}">
                        <a href="#"><spring:message code="jsp.oblik.transfer"/></a>
                    </li>
                    <li class="${formActionBean.type == 'INCOME' ? 'active' : disableType}">
                        <a href="#"><spring:message code="jsp.oblik.income"/></a>
                    </li>
                </ul>
            </div>

            <div class="form-group">
                <label class="sr-only"><spring:message var="accountLabel" code="jsp.oblik.account"/></label>
                <form:select id="account-from" path="firstAccount" cssClass="form-control">
                    <c:if test="${empty formActionBean.txId}">
                        <form:option value="" label="${accountLabel}" disabled="disabled" selected="selected"/>
                    </c:if>
                    <c:forEach var="account" items="${accountFromItems}">
                        <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencyId}"/>
                    </c:forEach>
                </form:select>
                <form:errors path="firstAccount" element="div" cssClass="alert alert-danger"/>
            </div>

            <div class="form-group">
                <label class="sr-only"><spring:message var="firstAmountLabel" code="jsp.oblik.ammount"/></label>
                <div class="input-group">
                    <form:input id="firstAmmount" path="firstAmmount" cssClass="form-control calculable" placeholder="${firstAmountLabel}"/>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
                </div>
                <form:errors path="firstAmmount" element="div" cssClass="alert alert-danger"/>
            </div>

            <div class="form-group">
                <label class="sr-only"><spring:message var="dateLabel" code="jsp.oblik.date"/></label>
                <div class="input-group">
                    <form:input id="date" path="date" cssClass="form-control datepicker" placeholder="${dateLabel}"/>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"/></span>
                </div>
                <form:errors path="date" element="div" cssClass="alert alert-danger"/>
            </div>

            <div class="form-group">
                <label class="sr-only"><spring:message var="accountLabel" code="jsp.oblik.account"/></label>
                <form:select id="account-to" path="secondAccount" cssClass="form-control">
                    <c:if test="${empty formActionBean.txId}">
                        <form:option value="" label="${accountLabel}" disabled="disabled" selected="selected"/>
                    </c:if>
                    <c:forEach var="account" items="${accountToItems}">
                        <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencyId}"/>
                    </c:forEach>
                </form:select>
                <form:errors path="secondAccount" element="div" cssClass="alert alert-danger"/>
            </div>

            <c:set var="secondAmmountStyle"><c:if test="${formActionBean.type != 'TRANSFER'}">style="display: none;"</c:if></c:set>
            <div id="second-ammount-div" class="form-group" ${secondAmmountStyle}>
                <label class="sr-only"><spring:message var="secondAmountLabel" code="jsp.oblik.expense.ammount"/></label>
                <div class="input-group">
                    <form:input id="secondAmmount" path="secondAmmount" cssClass="form-control calculable" placeholder="${secondAmountLabel}"/>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
                </div>
                <form:errors path="secondAmmount" element="div" cssClass="alert alert-danger"/>
            </div>

            <div class="form-group">
                <label class="sr-only"><spring:message var="noteLabel" code="jsp.oblik.note"/></label>
                <form:textarea id="note" path="note" cssClass="form-control" placeholder="${noteLabel}"/>
                <form:errors path="note" element="div" cssClass="alert alert-danger"/>
            </div>

            <div class="form-group">
                <a id="action-button" class="btn btn-link btn-xs" href="#">
                    <span class="glyphicon glyphicon-ok"/> 
                </a>
                <a id="action-cancel" class="btn btn-link btn-xs" href="#">
                    <span class="glyphicon glyphicon-remove"/> 
                </a>
                <c:if test="${!empty formActionBean.txId}">
                    <a id="action-delete-${formActionBean.txId}" class="btn btn-link btn-xs pull-right"
                       href="${pageContext.request.contextPath}/transaction/delete.html?transactionId=${formActionBean.txId}">
                        <span class="glyphicon glyphicon-trash"/> 
                    </a>
                </c:if>
            </div>
        </form:form>
    </div>
</div>