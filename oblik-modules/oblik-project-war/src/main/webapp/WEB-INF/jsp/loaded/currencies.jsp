<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<spring:message var="title_symbol" code="jsp.oblik.currency.symbol"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/> 

<display:table id="currecy-table"
               name="currencyList"
               requestURI="/currency/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="currencyId" title="currencyId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="symbol" title="${title_symbol}"/>
    <display:column property="rate" title="${title_rate}"/>

</display:table>

<a id="add-currency-btn" class="btn" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/currency/edit.html">
    <spring:message code="jsp.oblik.button.add.currency"/>
</a>