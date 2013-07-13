<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>


<spring:message var="title_total" code="jsp.oblik.total"/>
<table class="table table-hover">
    <tbody>
    	<tr>
            <td><h5>${title_total}</h5></td>
            <td><h5>${defCurrency.symbol }</h5></td>
            <td><h5>${total }</h5></td>
        </tr>
    </tbody>
</table>