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

<div class="container">
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
            <section id="form-actions">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <ul class="nav nav-pills">
                            <li class="active">
                                <a href="#tab-expense" data-toggle="tab"><spring:message code="jsp.oblik.expense"/></a>
                            </li>
                            <li>
                                <a href="#tab-transfer" data-toggle="tab"><spring:message code="jsp.oblik.transfer"/></a>
                            </li>
                            <li>
                                <a href="#tab-income" data-toggle="tab"><spring:message code="jsp.oblik.income"/></a>
                            </li>
                        </ul>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div class="tab-pane active" id="tab-expense">
                                <c:out value="${imgLoading}" escapeXml="false"/>
                            </div>
                            <div class="tab-pane" id="tab-transfer">
                                <c:out value="${imgLoading}" escapeXml="false"/>
                            </div>
                            <div class="tab-pane" id="tab-income">
                                <c:out value="${imgLoading}" escapeXml="false"/>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
            <section id="section-incomes">
                <c:out value="${imgLoading}" escapeXml="false"/>
            </section>
            <section id="section-expenses">
                <c:out value="${imgLoading}" escapeXml="false"/>
            </section>
        </div>
    </div>
</div>

<!-- http://stackoverflow.com/questions/14045515/how-can-i-reuse-one-bootstrap-modal-div -->
<div id="common-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="common-modal-label"></h2>
    </div>
    <div id="common-modal-body" class="modal-body">
        <c:out value="${imgLoading}" escapeXml="false"/>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal"><spring:message code="jsp.oblik.button.cancel"/></button>
        <button id="common-modal-save" class="btn btn-primary"><spring:message code="jsp.oblik.button.save"/></button>
    </div>
    <span id="common-modal-event" class="hide"></span>
</div>