<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-${formActionBean.type}" modelAttribute="formActionBean" method="POST" 
           action="${pageContext.request.contextPath}/formaction.html">
    <fieldset>
        <form:hidden path="txId"/>
        <form:hidden path="type"/>

        <label><spring:message code="jsp.oblik.account"/></label>
        <form:select id="account-from" path="firstAccount">
            <c:forEach var="account" items="${accountFromItems}">
                <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="firstAccount" element="div" cssClass="alert alert-error"/>

        <label><spring:message code="jsp.oblik.ammount"/></label>
        <form:input path="firstAmmount"/>
        <form:errors path="firstAmmount" element="div" cssClass="alert alert-error"/>

        <label><spring:message code="jsp.oblik.date"/></label>
        <form:input path="date"/>
        <form:errors path="date" element="div" cssClass="alert alert-error"/>

        <label><spring:message code="jsp.oblik.account"/></label>
        <form:select id="account-to" path="secondAccount">
            <c:forEach var="account" items="${accountToItems}">
                <form:option value="${account.accountId}" label="${account.name}" currency="${account.currencySymbol}"/>
            </c:forEach>
        </form:select>
        <form:errors path="secondAccount" element="div" cssClass="alert alert-error"/>

        <div id="second-ammount-div">
            <c:if test="${formActionBean.type == 'TRANSFER'}">
                <label><spring:message code="jsp.oblik.expense.ammount"/></label>
                <form:input path="secondAmmount"/>
                <form:errors path="secondAmmount" element="div" cssClass="alert alert-error"/>
            </c:if>
        </div>

        <label><spring:message code="jsp.oblik.note"/></label>
        <form:textarea path="note"/>
        <form:errors path="note" element="div" cssClass="alert alert-error"/>
    </fieldset>

    <button id="form-${formActionBean.type}-button" type="button" class="btn">Submit</button>
</form:form>

<script>
    $(document).ready(function() {

        $('#form-${formActionBean.type}-button').click(function() {
            $('#form-${formActionBean.type}').ajaxSubmit({
                success: function(data)
                {
                    $('#tab-${formActionBean.type}').html(data);
                    $("#tab-${formActionBean.type}").css({height: 'auto'});
                }
            });
        });

        $('#account-from, #account-to').change(function() {
            if ($('#account-from').attr('currency') === $('#account-to').attr('currency')) {
                $('#second-ammount-div').hide('slow', function() {
                });
            } else {
                $('#second-ammount-div').show('slow', function() {
                });
            }
        });
    });
</script>
