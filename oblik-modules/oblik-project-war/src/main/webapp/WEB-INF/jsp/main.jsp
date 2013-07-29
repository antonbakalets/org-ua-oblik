<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<spring:message var="tab_transactions_name" code="jsp.oblik.transactions"/>
<spring:message var="tab_accounts_name" code="jsp.oblik.accounts"/>
<spring:message var="tab_currencies_name" code="jsp.oblik.currencies"/>

<div class="container m-bot-25">
    <%--<div class="row">
        <div class="span12">
            <h1 class="page-title">Welcome, <security:authentication property="principal.username" /></h1>
        </div>
    </div>--%>
    <div class="row">
        <div class="span4">
            <section id="form-actions">
                <div class="tabbable"> <!-- Only required for left/right tabs -->
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab-expense" data-toggle="tab">Витрата</a></li>
                        <li><a href="#tab-transfer" data-toggle="tab">Переказ</a></li>
                        <li><a href="#tab-income" data-toggle="tab">Дохід</a></li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="tab-expense">

                        </div>
                        <div class="tab-pane" id="tab-transfer">

                        </div>
                        <div class="tab-pane" id="tab-income">

                        </div>
                    </div>
                </div>
            </section>
            <section id="total-by-currency">

            </section>
            <section id="total-by-account">

            </section>
            <section id="totalAmmount">

            </section>
        </div>
        
        <div class="span8">
            <div id="right-tabs" class="tabbable"> <!-- Only required for left/right tabs -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab-transactions" data-toggle="tab"> ${tab_transactions_name}</a></li>
                    <li><a href="#tab-accounts" data-toggle="tab">${tab_accounts_name}</a></li>
                    <li><a href="#tab-currecies" data-toggle="tab">${tab_currencies_name}</a></li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="tab-transactions">

                    </div>
                    <div class="tab-pane" id="tab-accounts">

                    </div>
                    <div class="tab-pane" id="tab-currecies">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- http://stackoverflow.com/questions/14045515/how-can-i-reuse-one-bootstrap-modal-div -->
<div id="common-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="modal-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="common-modal-label">Title</h2>
    </div>
    <div id="common-modal-body" class="modal-body">
        <p>One fine body…this is getting replaced with content that comes from passed-in href</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal"><spring:message code="jsp.oblik.button.cancel"/></button>
        <button id="common-modal-save" class="btn btn-primary"><spring:message code="jsp.oblik.button.save"/></button>
    </div>
</div>

<script>
    $(document).ready(function() {
        $("#tab-expense").load('${pageContext.request.contextPath}/formaction.html?type=expense', function() {
            $("#tab-expense").css({height: 'auto'});
        });

        $("#tab-transfer").load('${pageContext.request.contextPath}/formaction.html?type=transfer', function() {
            $("#tab-transfer").css({height: 'auto'});
        });

        $("#tab-income").load('${pageContext.request.contextPath}/formaction.html?type=income', function() {
            $("#tab-income").css({height: 'auto'});
        });

        $("#total-by-currency").load('${pageContext.request.contextPath}/total/currecy.html', function() {
            $("#total-by-currency").css({height: 'auto'});
        });

        $("#totalAmmount").load('${pageContext.request.contextPath}/total/ammount.html', function() {
            $("#totalAmmount").css({height: 'auto'});
        });

        $("#total-by-account").load('${pageContext.request.contextPath}/total/account.html', function() {
            $("#total-by-account").css({height: 'auto'});
        });

        $("#tab-transactions").load('${pageContext.request.contextPath}/transaction/list.html', function() {
            $("#tab-transactions").css({height: 'auto'});
        });

        $("#tab-accounts").load('${pageContext.request.contextPath}/account/list.html', function() {
            $("#tab-accounts").css({height: 'auto'});
        });

        $("#tab-currecies").load('${pageContext.request.contextPath}/currency/list.html', function() {
            $("#tab-currecies").css({height: 'auto'});
        });

        attachModal();
    });
</script>