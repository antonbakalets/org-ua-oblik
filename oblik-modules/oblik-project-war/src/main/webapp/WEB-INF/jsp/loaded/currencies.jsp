<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="title_symbol" code="jsp.oblik.currency.symbol"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/>
<spring:message var="title_total" code="jsp.oblik.currency.total"/>

<spring:message var="headerAdd" code="jsp.oblik.currency.header.add"/>
<spring:message var="headerEdit" code="jsp.oblik.currency.header.edit"/>

<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title"><spring:message code="jsp.oblik.currencies"/></h3>
    </div>
    <div class="panel-body">
        <ul class="list-group">
            <c:forEach var="entry" items="${currencyList}">
                <li class="list-group-item">        
                    <div class="edit-link">
                        <span class="hidden"><c:out value="${entry.currencyId}"/></span>

                        <a id="currency_${entry.currencyId}" title="${headerEdit}"
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
                    <a id="add-currency-btn" class="btn currency"

                       href="${pageContext.request.contextPath}/currency/edit.html">
                        <span class="glyphicon glyphicon-plus"></span>
                        <spring:message code="jsp.oblik.button.add.currency"/>
                    </a>
                </div>
            </li>
        </ul>
    </div>
</div>
