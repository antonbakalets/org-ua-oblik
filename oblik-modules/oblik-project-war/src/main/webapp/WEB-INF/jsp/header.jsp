<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<header class="subhead" id="overview">
    <div class="container">
        <div class="row">
            <div class="header-container">
                <div class="span3">
                    Кошти: <a>85 659,56 грн.</a>
                </div>
                <div class="span6">
                </div>
                <div class="span3">
                    <div class="pull-right">
                        Привіт, <a><security:authentication property="principal.username" /></a>!
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>