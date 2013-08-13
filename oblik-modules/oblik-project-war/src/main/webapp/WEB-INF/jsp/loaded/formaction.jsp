<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>

<form:form id="form-${formActionBean.type}" cssClass="form-horizontal m-top-10"
           modelAttribute="formActionBean" method="POST" 
           action="${pageContext.request.contextPath}/formaction.html">
    <form:hidden path="txId"/>
    <form:hidden path="type"/>

    <div class="control-group">
        <label class="control-label" for="account-from"><spring:message code="jsp.oblik.account"/></label>
        <div class="controls">
            <form:select id="account-from" path="firstAccount">
                <form:option value="" label=""/>
                <c:forEach var="account" items="${accountFromItems}">
                    <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
                </c:forEach>
            </form:select>
        </div>
        <form:errors path="firstAccount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <label class="control-label" for="firstAmmount"><spring:message code="jsp.oblik.ammount"/></label>
        <div class="controls">
            <div class="input-append">
                <form:input id="firstAmmount" path="firstAmmount"/>
                <span class="add-on"><i class="icon-list-alt"></i></span>
            </div>
        </div>
        <form:errors path="firstAmmount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <label class="control-label" for="date"><spring:message code="jsp.oblik.date"/></label>
        <div class="controls">
            <div class="input-append">
                <form:input id="date" path="date" cssClass="datepicker"/>
                <span class="add-on"><i class="icon-calendar"></i></span>
            </div>
        </div>
        <form:errors path="date" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <label class="control-label" for="account-to"><spring:message code="jsp.oblik.account"/></label>
        <div class="controls">
            <form:select id="account-to" path="secondAccount">
                <form:option value="" label=""/>
                <c:forEach var="account" items="${accountToItems}">
                    <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
                </c:forEach>
            </form:select>
        </div>
        <form:errors path="secondAccount" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <c:if test="${formActionBean.type == 'TRANSFER'}">
        <div id="second-ammount-div" class="control-group">
            <label class="control-label" for="secondAmmount"><spring:message code="jsp.oblik.expense.ammount"/></label>
            <div class="controls">
                <form:input id="secondAmmount" path="secondAmmount"/>
            </div>
            <form:errors path="secondAmmount" element="div" cssClass="alert iconed-box alert-error"/>
        </div>
    </c:if>

    <div class="control-group">
        <label class="control-label" for="note"><spring:message code="jsp.oblik.note"/></label>
        <div class="controls">
            <form:textarea id="note" path="note"/>
        </div>
        <form:errors path="note" element="div" cssClass="alert iconed-box alert-error"/>
    </div>

    <div class="control-group">
        <div class="controls">
            <button id="form-${formActionBean.type}-button" type="button" 
                    class="btn btn-${fn:toLowerCase(formActionBean.type)}">Submit</button>
        </div>
    </div>

</form:form>

<script>
    $(document).ready(function() {

        

        
    });
</script>
