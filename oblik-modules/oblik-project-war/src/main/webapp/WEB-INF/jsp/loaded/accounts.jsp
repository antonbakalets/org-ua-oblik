<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>


<spring:message var="title_name" code="jsp.oblik.account.name"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<h4><spring:message code="jsp.oblik.account.assets"/></h4>
<display:table id="assetsAccounts"
               name="assetsAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    headerClass="hide" class="hide"/>
    <display:column property="name" title="${title_name}"
                    headerClass="span3 align-center" class="span3"/>
    <display:column property="currencySymbol" title="${title_currency }"
                    headerClass="span2 align-center" class="span2"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"
                    headerClass="span1 align-center" class="span1 align-right"/>
    <display:column class="span2 align-right">
        <a id="account_${assetsAccounts.accountId}" class="btn btn-mini"
           href="${pageContext.request.contextPath}/account/edit.html?accountId=${assetsAccounts.accountId}&type=ASSETS"
           data-target="#common-modal" data-toggle="modal">
            <i class="icon-edit"></i> 
        </a>
        <c:if test="${!assetsAccounts.used}">
            <a id="account_delete_${assetsAccounts.accountId}" class="btn btn-mini"
               href="${pageContext.request.contextPath}/account/delete.html?accountId=${assetsAccounts.accountId}"
               data-target="#common-modal" data-toggle="modal">
                <i class="icon-trash"></i>    
            </a>
        </c:if>
    </display:column>
    <display:setProperty name="basic.msg.empty_list">
        <div class="alert iconed-box alert-info">
            <spring:message code="jsp.oblik.account.assets.empty"/>
        </div>
    </display:setProperty>
</display:table>

<a id="add-assets" class="btn btn-transfer" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=ASSETS">
    <spring:message code="jsp.oblik.account.btn.assets"/>
</a>

<h4><spring:message code="jsp.oblik.account.income"/></h4>
<display:table id="incomeAccounts"
               name="incomeAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    headerClass="hide" class="hide"/>
    <display:column property="name" title="${title_name}"
                    headerClass="span3 align-center" class="span3"/>
    <display:column property="currencySymbol" title="${title_currency}"
                    headerClass="span2 align-center" class="span2"/>
    <display:column property="ammount" title="${title_balance}" value="ammount"
                    headerClass="span1 align-center" class="span1 align-right"/>
    <display:column class="span2 align-right">
        <a id="account_${incomeAccounts.accountId}" class="btn btn-mini"
           href="${pageContext.request.contextPath}/account/edit.html?accountId=${incomeAccounts.accountId}&type=INCOME"
           data-target="#common-modal" data-toggle="modal">
            <i class="icon-edit"></i> 
        </a>
        <c:if test="${!incomeAccounts.used}">
            <a id="account_delete_${incomeAccounts.accountId}" class="btn btn-mini"
               href="${pageContext.request.contextPath}/account/delete.html?accountId=${incomeAccounts.accountId}"
               data-target="#common-modal" data-toggle="modal">
                <i class="icon-trash"></i>    
            </a>
        </c:if>
    </display:column>
    <display:setProperty name="basic.msg.empty_list">
        <div class="alert iconed-box alert-info">
            <spring:message code="jsp.oblik.account.income.empty"/>
        </div>
    </display:setProperty>
</display:table>

<a id="add-income" class="btn btn-income" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=INCOME">
    <spring:message code="jsp.oblik.account.btn.income"/>
</a>

<h4><spring:message code="jsp.oblik.account.expence"/></h4>
<display:table id="expenseAccounts"
               name="expenseAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    headerClass="hide" class="hide"/>
    <display:column property="name" title="${title_name}"
                    headerClass="span3 align-center" class="span3"/>
    <display:column property="currencySymbol" title="${title_currency }"
                    headerClass="span2 align-center" class="span2"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"
                    headerClass="span1 align-center" class="span1 align-right"/>
    <display:column class="span2 align-right">
        <a id="account_${expenseAccounts.accountId}" class="btn btn-mini"
           href="${pageContext.request.contextPath}/account/edit.html?accountId=${expenseAccounts.accountId}&type=EXPENSE"
           data-target="#common-modal" data-toggle="modal">
            <i class="icon-edit"></i> 
        </a>
        <c:if test="${!expenseAccounts.used}">
            <a id="account_delete_${expenseAccounts.accountId}" class="btn btn-mini"
               href="${pageContext.request.contextPath}/account/delete.html?accountId=${expenseAccounts.accountId}"
               data-target="#common-modal" data-toggle="modal">
                <i class="icon-trash"></i>    
            </a>
        </c:if>
    </display:column>
    <display:setProperty name="basic.msg.empty_list">
        <div class="alert iconed-box alert-info">
            <spring:message code="jsp.oblik.account.expence.empty"/>
        </div>
    </display:setProperty>
</display:table>

<a id="add-expense" class="btn btn-expense" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=EXPENSE">
    <spring:message code="jsp.oblik.account.btn.expense"/>
</a>