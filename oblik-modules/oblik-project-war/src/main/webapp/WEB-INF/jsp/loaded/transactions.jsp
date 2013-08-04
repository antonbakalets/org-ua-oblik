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
<table class="table table-striped table-hover table-condensed">
    <thead>
        <tr>
            <th>${title_date}</th>
            <th>${title_ammount}</th>
            <th>${title_account}</th>
            <th>${title_category}</th>
            <th>${title_note}</th>
            <th> </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="transaction" items="${transaction_list}">
   		<c:if test="${transaction.type == 'TRANSFER'}">
	    	<tr>
	            <td>${transaction.date }</td>
	            <td>-${transaction.firstAmmount } 
	            	</br>
	            	${transaction.secondAmmount }
	            </td>
	            <td>${transaction.firstAccount.name } -> ${transaction.secondAccount.name }</td>
	            <td></td>
	            <td>${transaction.note }</td>
	            <td></td>
	        </tr>
	    </c:if>
	    <c:if test="${transaction.type == 'INCOME'}">
	    	<tr>
	            <td>${transaction.date }</td>
	            <td>+ ${transaction.firstAmmount }</td>
	            <td>${transaction.secondAccount.name }</td>
	            <td>${transaction.firstAccount.name }</td>
	            <td>${transaction.note }</td>
	            <td></td>
	        </tr>
	    </c:if>
	   <c:if test="${transaction.type == 'EXPENSE'}">
	    	<tr>
	            <td>${transaction.date }</td>
	            <td>-${transaction.firstAmmount }</td>
	            <td>${transaction.secondAccount.name }</td>
	            <td>${transaction.firstAccount.name }</td>
	            <td>${transaction.note }</td>
	            <td></td>
	        </tr>
	    </c:if>
    </c:forEach>
    </tbody>
</table>
