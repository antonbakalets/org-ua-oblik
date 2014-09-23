<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="list-group">
    <li class="list-group-item active">
        <h4 class="panel-title"><spring:message code="jsp.oblik.currencies"/></h4>
    </li>
    <c:forEach var="entry" items="${currencyList}">
        <li class="list-group-item">
            <div class="edit-link">
                <span class="hidden"><c:out value="${entry.currencyId}"/></span>
                <a id="currency_${entry.currencyId}" title="${headerEdit}" class="info"
                   href="${pageContext.request.contextPath}/currency/edit.html?currencyId=${entry.currencyId}">
                    <c:out value="${entry.total}"/>
                    <c:out value="${entry.symbol}"/>
                </a>
                (<c:out value="${entry.rate}"/>)
            </div>
        </li>
    </c:forEach>
    <li class="list-group-item">
        <div class="edit-link">
            <a id="add-currency-btn" class="btn btn-link"
               href="${pageContext.request.contextPath}/currency/edit.html">
                <span class="glyphicon glyphicon-plus"/>
                <spring:message code="jsp.oblik.button.add.currency"/>
            </a>
        </div>
    </li>
</ul>
