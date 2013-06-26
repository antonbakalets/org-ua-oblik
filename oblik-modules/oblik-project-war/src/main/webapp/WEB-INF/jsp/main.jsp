<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row">
        <div class="span12">
            <h1 class="page-title">Welcome, <security:authentication property="principal.username" /></h1>
        </div>
    </div>
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
        </div>
        <div class="span8">
            <div class="tabbable"> <!-- Only required for left/right tabs -->
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#tab-transactions" data-toggle="tab">Transactions</a></li>
                    <li><a href="#tab-accounts" data-toggle="tab">Accounts</a></li>
                    <li><a href="#tab-currecies" data-toggle="tab">Currencies</a></li>
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
    });
</script>