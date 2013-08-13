<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<spring:message var="title_symbol" code="jsp.oblik.currency.symbol"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/>
<spring:message var="title_total" code="jsp.oblik.currency.total"/>

<spring:message var="headerAdd" code="jsp.oblik.currency.header.add"/>
<spring:message var="headerEdit" code="jsp.oblik.currency.header.edit"/>

<h3 class="title-block"><spring:message code="jsp.oblik.currencies"/></h3>
<div class="content-container-white">
    <div class="m-all-5">

        <display:table id="currecyTable"
                       name="currencyList"
                       requestURI="/currency/list.html"
                       class="table table-striped table-hover table-condensed">
            <display:column property="currencyId" title="currencyId" 
                            class="hide" headerClass="hide"/>
            <display:column title="${title_symbol}" 
                            class="span2" headerClass="span2 align-center">
                <a id="currency_${currecyTable.currencyId}" title="${headerEdit}"
                   data-target="#common-modal" data-toggle="modal"
                   href="${pageContext.request.contextPath}/currency/edit.html?currencyId=${currecyTable.currencyId}">
                    ${currecyTable.symbol}
                </a>
            </display:column>
            <display:column property="rate" title="${title_rate}"
                            class="span2 align-right" headerClass="span2 align-center"/>
            <display:column property="total" title="${title_total}"
                            class="span2 align-right" headerClass="span2 align-center"/>
            <display:setProperty name="basic.msg.empty_list">
                <div class="alert iconed-box alert-info">
                    <spring:message code="jsp.oblik.currency.empty"/>
                </div>
            </display:setProperty>
        </display:table>

        <a id="add-currency-btn" class="btn currency" title="${headerAdd}"
           data-toggle="modal" data-target="#common-modal"
           href="${pageContext.request.contextPath}/currency/edit.html">
            <spring:message code="jsp.oblik.button.add.currency"/>
        </a>

    </div>
</div>
<div class="content-under-container-white"></div>