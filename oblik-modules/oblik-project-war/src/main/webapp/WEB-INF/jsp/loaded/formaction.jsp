<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="fabType" value="${fn:toLowerCase(formActionBean.type)}"/>


<form:form id="form-${fabType}" cssClass="form-horizontal"
           modelAttribute="formActionBean" method="POST" 
           action="${pageContext.request.contextPath}/formaction.html">
    <form:hidden path="txId"/>
    <form:hidden path="type"/>

    <div class="control-group">
        <label class="control-label" for="account-from-${fabType}"><spring:message code="jsp.oblik.account"/></label>
        <div class="">
            <form:select id="account-from-${fabType}" path="firstAccount" cssClass="form-control">
                <c:if test="${empty formActionBean.txId}">
                    <form:option value="" label=""/>
                </c:if>
                <c:forEach var="account" items="${accountFromItems}">
                    <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
                </c:forEach>
            </form:select>
        </div>
        <form:errors path="firstAccount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <label class="control-label" for="firstAmmount-${fabType}"><spring:message code="jsp.oblik.ammount"/></label>
        <div class="">
            <div class="input-group">
                <form:input id="firstAmmount-${fabType}" path="firstAmmount" cssClass="form-control calculable"/>
                <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
            </div>
        </div>
        <form:errors path="firstAmmount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <label class="control-label" for="date-${fabType}"><spring:message code="jsp.oblik.date"/></label>
        <div class="">
            <div class="input-group">
                <form:input id="date-${fabType}" path="date" cssClass="form-control datepicker"/>
                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"/></span>
            </div>
        </div>
        <form:errors path="date" element="div" cssClass="alert iconed-box alert-error"/>
    </div>
    
    <div class="control-group">
        <label class="control-label" for="account-to-${fabType}"><spring:message code="jsp.oblik.account"/></label>
        <div class="">
            <form:select id="account-to-${fabType}" path="secondAccount" cssClass="form-control">
                <c:if test="${empty formActionBean.txId}">
                    <form:option value="" label=""/>
                </c:if>
                <c:forEach var="account" items="${accountToItems}">
                    <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
                </c:forEach>
            </form:select>
        </div>
        <form:errors path="secondAccount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <c:if test="${formActionBean.type == 'TRANSFER'}">
        <div id="second-ammount-div-${fabType}" class="control-group">
            <label class="control-label" for="secondAmmount-${fabType}"><spring:message code="jsp.oblik.expense.ammount"/></label>
            <div class="">
                <div class="input-group">
                    <form:input id="secondAmmount-${fabType}" path="secondAmmount" cssClass="form-control calculable"/>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-list-alt"/></span>
                </div>
            </div>
            <form:errors path="secondAmmount" element="div" cssClass="alert iconed-box alert-error"/>
        </div>
    </c:if>

    <div class="control-group">
        <label class="control-label" for="note-${fabType}"><spring:message code="jsp.oblik.note"/></label>
        <div class="">
            <form:textarea id="note-${fabType}" path="note" cssClass="form-control"/>
        </div>
        <form:errors path="note" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <c:if test="${empty formActionBean.txId}">
        <div class="control-group">
            <div class="">
                <button id="form-${fabType}-button" type="button" 
                        class="btn btn-${fabType}">Submit</button>
            </div>
        </div>
    </c:if>

</form:form>