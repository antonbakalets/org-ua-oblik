<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="security"    uri="http://www.springframework.org/security/tags" %>

<header class="subhead" id="overview">
    <div class="page-header">

        <!-- <h1>putt title here</h1> -->

        <div class="row">
            <div class="col-xs-6 col-sm-3">
                <div class="well">
                    <spring:message code="jsp.oblik.hi"/>,
                    <a><security:authentication property="principal.username" /></a>!
                </div>
            </div>
            <div id="default-total" class="col-xs-6 col-sm-9">
                <div class="well"></div>
            </div>
        </div>
    </div>
</header>