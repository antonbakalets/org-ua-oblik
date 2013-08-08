<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-account" modelAttribute="accountBean" method="POST" 
           action="${pageContext.request.contextPath}/account/delete.html">

		<form:hidden path="accountId"/>
        <label><spring:message code="jsp.oblik.account.delete.dialog"/> '${accountBean.name}' </label>

</form:form>