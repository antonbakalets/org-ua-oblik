<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="title_total" code="jsp.oblik.totalByCurrency"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<h3 class="title-block">${title_total }</h3>
<div class="content-container-white">
    <div class="m-all-5">
    <table class="table table-striped table-hover table-condensed">
        <thead>
            <tr>
                <th>${title_currency }</th>
                <th>${title_rate }</th>
                <th class="text-assets">${title_balance }</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${totalByCur}">
                <tr>
                    <td>${entry.key.symbol }</td>
                    <td>${entry.key.rate }</td>
                    <td class="text-assets">${entry.value }</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a id="add-currency-btn" class="btn currency" data-toggle="modal" data-target="#common-modal"
       href="${pageContext.request.contextPath}/currency/edit.html">
        <spring:message code="jsp.oblik.button.add.currency"/>
    </a>
    </div>
</div>
<div class="content-under-container-white"></div>
