<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<spring:message var="title_total" code="jsp.oblik.totalByAccounts"/>
<spring:message var="title_name" code="jsp.oblik.account.name"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<spring:message var="headerAdd" code="jsp.oblik.account.assets.header.add"/>
<spring:message var="headerEdit" code="jsp.oblik.account.assets.header.edit"/>

<h3 class="title-block">${title_total }</h3>
<div class="content-container-white">
    <div class="m-all-5">
        <display:table id="totalAccounts"
                       name="assetsAccounts"
                       requestURI="total/account.html"
                       class="table table-striped table-hover table-condensed">
            <display:column property="accountId" title="accountId" 
                            headerClass="hide" class="hide"/>
            <display:column title="${title_name}"
                            headerClass="span2 align-center" class="span2">
                <a id="account_${totalAccounts.accountId}" title="${headerEdit}"
                   data-target="#common-modal" data-toggle="modal"
                   href="${pageContext.request.contextPath}/account/edit.html?accountId=${totalAccounts.accountId}&type=ASSETS">
                    ${totalAccounts.name}
                </a>
            </display:column>
            <display:column property="currencySymbol" title="${title_currency}"
                            headerClass="span1 align-center" class="span1"/>
            <display:column property="ammount" title="${title_balance }" value="ammount"
                            headerClass="span1 align-center" class="span1 align-right"/>
            <display:setProperty name="basic.msg.empty_list">
                <div class="alert iconed-box alert-info">
                    <spring:message code="jsp.oblik.account.assets.empty"/>
                </div>
            </display:setProperty>
        </display:table>

        <a id="add-assets" class="btn btn-transfer" title="${headerAdd}"
           data-toggle="modal" data-target="#common-modal"
           href="${pageContext.request.contextPath}/account/edit.html?type=ASSETS">
            <spring:message code="jsp.oblik.account.btn.assets"/>
        </a>
    </div>
</div>
<div class="content-under-container-white"></div>
