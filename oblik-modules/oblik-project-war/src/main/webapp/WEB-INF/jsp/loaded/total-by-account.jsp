<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<spring:message var="title_total" code="jsp.oblik.totalByAccounts"/>
<spring:message var="title_name" code="jsp.oblik.account.name"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<h3 class="title-block">${title_total }</h3>
<div class="content-container-white">
    <display:table id="totalAccounts"
                   name="assetsAccounts"
                   requestURI="total/currency/list.html"
                   class="table table-striped table-hover table-condensed">
        <display:column property="currencyId" title="currencyId" 
                        class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
        <display:column property="name" title="${title_name}"/>
        <display:column property="currencySymbol" title="${title_currency }"/>
        <display:column property="ammount" title="${title_balance }" value="ammount"/>
    </display:table>
</div>
<div class="content-under-container-white"></div>
