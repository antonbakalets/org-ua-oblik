<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<form:form id="form-account" modelAttribute="transaction" method="POST" 
           action="${pageContext.request.contextPath}/transaction/delete.html">

		<form:hidden path="transactionId"/>
        <label><spring:message code="jsp.oblik.transaction.delete.dialog"/></label>

</form:form>