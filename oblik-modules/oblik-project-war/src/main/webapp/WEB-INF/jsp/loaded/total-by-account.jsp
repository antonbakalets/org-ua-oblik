<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="list-group">
    <li class="list-group-item active">
        <h4 class="panel-title"><spring:message code="jsp.oblik.totalByAccounts"/></h4>
    </li>
    <c:forEach var="entry" items="${assetsAccounts}">
        <li class="list-group-item">
            <div class="edit-link">
                <span class="hidden"><c:out value="${entry.accountId}"/></span>
                <a id="account_${entry.accountId}"
                   href="${pageContext.request.contextPath}/account/edit.html?accountId=${entry.accountId}&type=ASSETS">
                    ${entry.name}
                </a>
                <c:out value="${entry.ammount}"/>
                <c:out value="${entry.currencySymbol}"/>
            </div>
        </li>
    </c:forEach>
    <li class="list-group-item">
        <div class="edit-link">
            <a id="add-assets" class="btn btn-link"
               href="${pageContext.request.contextPath}/account/edit.html?type=ASSETS">
                <span class="glyphicon glyphicon-plus"/>
                <spring:message code="jsp.oblik.account.btn.assets"/>
            </a>
        </div>
    </li>
</ul>
