<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="security"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"      uri="http://www.springframework.org/tags" %>

<c:set var="defaultCurrencyBlock">
    <c:if test="${defaultCurrencyExists}">
        <span>
            <spring:message code="jsp.oblik.default.currency.total"/>:
            <span class="text-assets strong">
                <strong>
                    <c:out value="${defaultCurrencyTotal}"/>
                </strong>
            </span> 
            <c:out value="${defaultCurrencySymbol}"/>
        </span>
    </c:if>
</c:set>

<header class="subhead" id="overview">
    <div class="page-header">

        <span id="default-currency-exists" class="hidden">${defaultCurrencyExists}</span>
        <span id="default-currency-symbol" class="hidden">${defaultCurrencySymbol}</span>
        <span id="default-currency-total" class="hidden">${defaultCurrencyTotal}</span>

        <a href="https://github.com/antonbakalets/org-ua-oblik">
            <img style="position: absolute; top: 0; right: 0; border: 0; z-index: 1;"
                 src="https://s3.amazonaws.com/github/ribbons/forkme_right_gray_6d6d6d.png"
                 alt="Fork me on GitHub">
        </a>

        <!-- <h1>putt title here</h1> -->

        <div class="row">
            <div class="col-xs-6 col-sm-3">
                <div class="well">
                    <spring:message code="jsp.oblik.hi"/>,
                    <a><security:authentication property="principal.username" /></a>!
                </div>
            </div>
            <div class="col-xs-6 col-sm-9">
                <div class="well">
                    <c:out escapeXml="false" value="${defaultCurrencyBlock}"/>
                </div>
            </div>
        </div>
    </div>
</header>