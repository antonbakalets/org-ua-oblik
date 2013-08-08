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
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="${title_name}"/>
    <display:column property="currencySymbol" title="${title_currency }"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"/>
    <display:column class="align-right">
        <a id="account_${assetsAccounts.accountId}" class="btn btn-mini"
           href="${pageContext.request.contextPath}/account/edit.html?accountId=${assetsAccounts.accountId}&type=ASSETS"
           data-target="#common-modal" data-toggle="modal">
            <i class="icon-edit"></i> 
        </a>
    </display:column>
    <c:if test="${!accountBean.used}">
    	<display:column>
    		<a id="account_delete_${accountBean.accountId}" class="btn btn-mini"
				href="${pageContext.request.contextPath}/account/delete.html?accountId=${assetsAccounts.accountId}"
		        data-target="#common-modal" data-toggle="modal">
		        <i class="icon-trash"></i> 
		    </a>
    	</display:column>
    </c:if>
</display:table>

<a id="add-assets" class="btn transfer" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=ASSETS">
    <spring:message code="jsp.oblik.account.btn.assets"/>
</a>

<h4><spring:message code="jsp.oblik.account.income"/></h4>
<display:table id="incomeAccounts"
               name="incomeAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="${title_name}"/>
    <display:column property="currencySymbol" title="${title_currency }"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"/>
    <display:column>
        <a id="account_${incomeAccounts.accountId}" class="btn btn-mini"
           href="${pageContext.request.contextPath}/account/edit.html?accountId=${incomeAccounts.accountId}&type=INCOME"
           data-target="#common-modal" data-toggle="modal">
            <i class="icon-edit"></i> 
        </a>
    </display:column> />
    <c:if test="${accountBean.used}">
    	<display:column>
    		<a id="account_delete_${accountBean.accountId}" class="btn btn-mini"
				href="${pageContext.request.contextPath}/account/delete.html?accountId=${incomeAccounts.accountId}"
		        data-target="#common-modal" data-toggle="modal">
		        <i class="icon-trash"></i> 
		    </a>
    	</display:column>
    </c:if>
</display:table>

<a id="add-income" class="btn income" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=INCOME">
    <spring:message code="jsp.oblik.account.btn.income"/>
</a>
    
<h4><spring:message code="jsp.oblik.account.expence"/></h5>
    <display:table id="expenseAccounts"
                   name="expenseAccounts"
                   requestURI="/accounts/list.html"
                   class="table table-striped table-hover table-condensed">
        <display:column property="accountId" title="accountId" 
                        class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
        <display:column property="name" title="${title_name}"/>
        <display:column property="currencySymbol" title="${title_currency }"/>
        <display:column property="ammount" title="${title_balance }" value="ammount"/>
        <display:column>
	        <a id="account_${expenseAccounts.accountId}" class="btn btn-mini"
	           href="${pageContext.request.contextPath}/account/edit.html?accountId=${expenseAccounts.accountId}&type=EXPENSE"
	           data-target="#common-modal" data-toggle="modal">
	            <i class="icon-edit"></i> 
	        </a>
    	</display:column> />
    	<c:if test="${accountBean.used}">
    	<display:column>
    		<a id="account_delete_${accountBean.accountId}" class="btn btn-mini"
				href="${pageContext.request.contextPath}/account/delete.html?accountId=${expenseAccounts.accountId}"
		        data-target="#common-modal" data-toggle="modal">
		        <i class="icon-trash"></i> 
		    </a>
    	</display:column>
    	</c:if>
</display:table>

<a id="add-expense" class="btn expense" data-toggle="modal" data-target="#common-modal"
   href="${pageContext.request.contextPath}/account/edit.html?type=EXPENSE">
    <spring:message code="jsp.oblik.account.btn.expense"/>
</a>