<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="title_total" code="jsp.oblic.totalByCurrency"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<h3 class="title-block">${title_total }</h3>


<table class="table table-hover">
    <thead>
        <tr>
            <th>${title_currency }</th>
            <th>${title_rate }</th>
            <th>${title_balance }</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${totalByCur}">
    	<tr>
            <td>${entry.key.symbol }</td>
            <td>${entry.key.rate }</td>
            <td>${entry.value }</td>
        </tr>
    </c:forEach>
    </tbody>
</table>



