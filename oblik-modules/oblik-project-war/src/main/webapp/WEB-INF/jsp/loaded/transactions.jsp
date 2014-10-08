<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>



<spring:message var="title_date" code="jsp.oblik.date"/>
<spring:message var="title_ammount" code="jsp.oblik.ammount"/>
<spring:message var="title_account" code="jsp.oblik.account"/>
<spring:message var="title_category" code="jsp.oblik.category"/>
<spring:message var="title_note" code="jsp.oblik.note"/>

<h3 class="title-block"><spring:message var="title_transactions" code="jsp.oblik.transactions"/></h3>

<ul class="pager">
    <li>
        <a id="transaction-prev" href="${pageContext.request.contextPath}/transaction/list.html?month=${monthArray[0]}">
            <span class="glyphicon glyphicon-backward"/>
        </a></li>
    <li class="disabled">
        <a id="transaction-curr" href="#">${monthArray[1]}</a>
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

    <ul class="list-group">
        <li class="list-group-item active"><c:out value="${date}"/></li>
            <c:forEach var="transaction" items="${list}">
                <c:if test="${transaction.type == 'TRANSFER'}">

                <li class="list-group-item list-group-item-info">

                    -${transaction.firstAmmount } 

                    ${transaction.secondAmmount }

                    ${transaction.firstAccount.name } -> ${transaction.secondAccount.name }</td>

                    ${transaction.note }

                    <a id="transaction_edit_${transaction.transactionId}" class="btn btn-mini transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=transfer&txId=${transaction.transactionId}"
                       data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                        <span class="glyphicon glyphicon-edit"/>

                        <a id="transaction_delete_${transaction.transactionId}" class="btn btn-mini"
                           href="${pageContext.request.contextPath}/transaction/delete.html?transactionId=${transaction.transactionId}"
                           data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                            <span class="glyphicon glyphicon-trash"/> 
                        </a>

                </li>
            </c:if>
            <c:if test="${transaction.type == 'INCOME'}">
                <li class="list-group-item list-group-item-success">

                    + ${transaction.firstAmmount }
                    ${transaction.firstAccount.name }
                    ${transaction.secondAccount.name }
                    ${transaction.note }

                    <a id="transaction_edit_${transaction.transactionId}" class="btn btn-mini transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=income&txId=${transaction.transactionId}"
                       data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                        <span class="glyphicon glyphicon-edit"/>
                    </a>
                    <a id="transaction_delete_${transaction.transactionId}" class="btn btn-mini"
                       href="${pageContext.request.contextPath}/transaction/delete.html?transactionId=${transaction.transactionId}"
                       data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                        <span class="glyphicon glyphicon-trash"/> 
                    </a>

                </li>
            </c:if>
            <c:if test="${transaction.type == 'EXPENSE'}">
                <li class="list-group-item list-group-item-danger">

                    -${transaction.firstAmmount }
                    ${transaction.firstAccount.name }
                    ${transaction.secondAccount.name }
                    ${transaction.note }

                    <a id="transaction_edit_${transaction.transactionId}" class="btn btn-mini transaction-edit"
                       href="${pageContext.request.contextPath}/formaction.html?type=expense&txId=${transaction.transactionId}"
                       data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                        <span class="glyphicon glyphicon-edit"/>
                    </a>
                    <a id="transaction_delete_${transaction.transactionId}" class="btn btn-mini"
                       href="${pageContext.request.contextPath}/transaction/delete.html?transactionId=${transaction.transactionId}"
                       data-target="#common-modal" data-toggle="modal" save-event="transactionEdited">
                        <span class="glyphicon glyphicon-trash"/> 
                    </a>

                </li>
            </c:if>
        </c:forEach>
    </ul>
</c:forEach>
