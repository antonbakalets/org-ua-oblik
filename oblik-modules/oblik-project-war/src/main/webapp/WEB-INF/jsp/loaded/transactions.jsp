<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="title_date" code="jsp.oblik.date"/>
<spring:message var="title_amount" code="jsp.oblik.amount"/>
<spring:message var="title_account" code="jsp.oblik.account"/>
<spring:message var="title_category" code="jsp.oblik.category"/>
<spring:message var="title_note" code="jsp.oblik.note"/>

<ul class="pager">
    <li>
        <a id="transaction-prev" href="${pageContext.request.contextPath}/transaction/list.html?month=${monthArray[0]}">
            <span class="glyphicon glyphicon-backward"/>
        </a></li>
    <li class="disabled">
        <a id="transaction-curr"
           href="${pageContext.request.contextPath}/transaction/list.html?month=${monthArray[1]}">${monthArray[1]}</a>
    </li>
    <li>
        <a id="transaction-next" href="${pageContext.request.contextPath}/transaction/list.html?month=${monthArray[2]}">
            <span class="glyphicon glyphicon-forward"/>
        </a>
    </li>
</ul>

<c:forEach var="entry" items="${transaction_map}">
    <c:set var="date" value="${entry.key}"/>
    <c:set var="list" value="${entry.value}"/>

    <div class="row date-row">
        <div class="col-xs-3"><c:out value="${date}"/></div>
    </div>
    <c:forEach var="transaction" items="${list}">
        <c:if test="${transaction.type == 'TRANSFER'}">
            <div class="row transaction-row">
                <div class="col-xs-2 bg-info text-info text-right">
                    <strong>${transaction.firstAmount}&nbsp;${transaction.firstSymbol}</strong>
                        ${transaction.secondAmount}&nbsp;${transaction.secondSymbol}
                </div>
                <div class="col-xs-2 account-assets">${transaction.firstAccountName}</div>
                <div class="col-xs-2 account-assets">${transaction.secondAccountName}</div>
                <div class="col-xs-5">${transaction.note}</div>
                <div class="col-xs-1 ">
                    <a id="transaction_edit_${transaction.transactionId}"
                       class="btn btn-link btn-xs pull-right transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=transfer&txId=${transaction.transactionId}">
                        <span class="glyphicon glyphicon-edit"/>
                    </a>
                </div>
            </div>
        </c:if>
        <c:if test="${transaction.type == 'INCOME'}">
            <div class="row transaction-row">
                <div class="col-xs-2 list-group-item-success text-right">
                    <strong>${transaction.firstAmount}&nbsp;${transaction.firstSymbol}</strong>
                </div>
                <div class="col-xs-2 account-income">${transaction.secondAccountName}</div>
                <div class="col-xs-2 account-assets">${transaction.firstAccountName}</div>
                <div class="col-xs-5">${transaction.note}</div>
                <div class="col-xs-1">
                    <a id="transaction_edit_${transaction.transactionId}"
                       class="btn btn-link btn-xs pull-right transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=income&txId=${transaction.transactionId}">
                        <span class="glyphicon glyphicon-edit"/>
                    </a>
                </div>
            </div>
        </c:if>
        <c:if test="${transaction.type == 'EXPENSE'}">
            <div class="row transaction-row">
                <div class="col-xs-2 list-group-item-danger text-right">
                    <strong>${transaction.firstAmount}&nbsp;${transaction.firstSymbol}</strong>
                </div>
                <div class="col-xs-2 account-assets">${transaction.firstAccountName}</div>
                <div class="col-xs-2 account-expense">${transaction.secondAccountName}</div>
                <div class="col-xs-5">${transaction.note}</div>
                <div class="col-xs-1">
                    <a id="transaction_edit_${transaction.transactionId}"
                       class="btn btn-link btn-xs pull-right transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=expense&txId=${transaction.transactionId}">
                        <span class="glyphicon glyphicon-edit"/>
                    </a>
                </div>
            </div>
        </c:if>
    </c:forEach>
</c:forEach>
