<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="security"    uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="defaultCurrencyBlock">
    <c:if test="${defaultCurrencyExists}">
        <span>
            Ваші кошти згідно з курсами обміну: <span class="text-assets strong"><strong>85 659,56</strong></span> грн.
        </span>
    </c:if>
</c:set>

<header class="subhead" id="overview">
    <span id="default-currency-exists" class="hidden">${defaultCurrencyExists}</span>
    <span id="default-currency-symbol" class="hidden">${defaultCurrencySymbol}</span>
    <span id="default-currency-total" class="hidden">${defaultCurrencyTotal}</span>

    <div class="container">
        <div class="row">
            <div class="header-top-container">
                <div class="span12">
                    <div class="pull-right">
                        Привіт, <a><security:authentication property="principal.username" /></a>!
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="header-bot-container">
                <div class="span12">
                    <div class="pull-right">
                        <c:out escapeXml="false" value="${defaultCurrencyBlock}"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>