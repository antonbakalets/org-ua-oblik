<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<div class="well">
    <c:if test="${defaultCurrencyExists}">
        <span>
            <spring:message code="jsp.oblik.default.currency.total"/>:
            <strong>
                <span id="default-total" class="text-assets strong">
                    <c:out value="${defaultCurrencyTotal}"/>
                </span>
            </strong>
            <c:out value="${defaultCurrencySymbol}"/>
        </span>
    </c:if>
</div>
