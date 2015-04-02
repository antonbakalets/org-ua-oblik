<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="altLoading" code="jsp.oblik.loading"/>
<c:set var="imgLoading">
    <div class="m-all-5 text-center">
        <img alt='${altLoading}...' src="${pageContext.request.contextPath}/img/loading.gif"/>
    </div>
</c:set>

<span id="contextPath" class="hide">${pageContext.request.contextPath}</span>

<div class="row">
    <div id="main-progress" class="progress">
        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0"
             aria-valuemax="100" style="width: 100%">
            <span class="sr-only">100% Complete</span>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-xs-6 col-sm-3">
        <section id="total-by-currency">
            <c:out value="${imgLoading}" escapeXml="false"/>
        </section>
        <section id="total-by-account">
            <c:out value="${imgLoading}" escapeXml="false"/>
        </section>
    </div>

    <div class="col-xs-6 col-sm-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h4 class="panel-title"><spring:message code="jsp.oblik.transactions"/></h4>
            </div>
            <div id="tab-transactions" class="panel-body">
                <c:out value="${imgLoading}" escapeXml="false"/>
            </div>
        </div>
    </div>

    <div class="col-xs-6 col-sm-3">
        <section id="section-actions">
            <c:out value="${imgLoading}" escapeXml="false"/>
        </section>
        <section id="section-incomes">
            <c:out value="${imgLoading}" escapeXml="false"/>
        </section>
        <section id="section-expenses">
            <c:out value="${imgLoading}" escapeXml="false"/>
        </section>
    </div>
</div>

