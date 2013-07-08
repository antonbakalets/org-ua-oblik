<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<spring:message var="title_name" code="jsp.oblik.account.name"/>
<spring:message var="title_currency" code="jsp.oblik.account.currency"/>
<spring:message var="title_balance" code="jsp.oblik.account.balance"/> 

<h4><spring:message code="jsp.oblic.account.assets"/></h4>
<display:table id="assetsAccounts"
               name="assetsAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="${title_name}"/>
    <display:column property="currencySymbol" title="${title_currency }"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"/>
        <display:column>
    	<a id="account_${assetsAccounts.accountId}" data-toggle="modal" href="${pageContext.request.contextPath}/account/edit.html?accountId=${assetsAccounts.accountId}" data-target="#account-add-modal" class="btn">
    		<i class="icon-edit"></i> 
		</a>
	</display:column> />

</display:table>
<h4><spring:message code="jsp.oblic.account.income"/></h4>
<display:table id="incomeAccounts"
               name="incomeAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="${title_name}"/>
    <display:column property="currencySymbol" title="${title_currency }"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"/>
        <display:column>
    	<a id="account_${incomeAccounts.accountId}" data-toggle="modal" href="${pageContext.request.contextPath}/account/edit.html?accountId=${incomeAccounts.accountId}" data-target="#account-add-modal" class="btn">
    		<i class="icon-edit"></i> 
		</a>
	</display:column> />

</display:table>
<h4><spring:message code="jsp.oblic.account.expence"/></h5>
<display:table id="expenseAccounts"
               name="expenseAccounts"
               requestURI="/accounts/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
     <display:column property="name" title="${title_name}"/>
    <display:column property="currencySymbol" title="${title_currency }"/>
    <display:column property="ammount" title="${title_balance }" value="ammount"/>
    <display:column>
    	<a id="account_${expenseAccounts.accountId}" data-toggle="modal"  href="${pageContext.request.contextPath}/account/edit.html?accountId=${expenseAccounts.accountId}" data-target="#account-add-modal" class="btn">
    		<i class="icon-edit"></i> 
		</a>
	</display:column> />
</display:table>

<a id="file_attach" data-toggle="modal" href="${pageContext.request.contextPath}/account/edit.html" data-target="#account-add-modal" class="btn">
    <spring:message code="jsp.oblik.button.add.account"/>
</a>

<!-- Modal -->
<div id="account-add-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="account-add-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="account-add-label"><spring:message code="jsp.oblik.currency.add.header"/></h2>
    </div>
    <div class="modal-body" id="account-add-body">
        <p>One fine body…this is getting replaced with content that comes from passed-in href</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal"><spring:message code="jsp.oblik.button.cancel"/></button>
        <button id="account-add-save" class="btn btn-primary"><spring:message code="jsp.oblik.button.save"/></button>
    </div>
</div>


<script>
    $(document).ready(function() {

        $('a[data-toggle="modal"]').on('click', function() {
            $('#account-add-body').load(
                    $(this).attr('href'),
                    function(response, status, xhr) {
                        if (status === 'error') {
                            //console.log('got here');
                            $('#account-add-body').html('<h2>Oh boy</h2><p>Sorry, but there was an error:' + xhr.status + ' ' + xhr.statusText + '</p>');
                        }
                        return this;
                    }
            );
        });


        $('#account-add-save').click(function currencyFormSubmit() {
            $('#form-account').ajaxSubmit({
                success: function(responseText, statusText, xhr, $form) {
                    $('#account-add-body').html('');
                    $('#account-add-body').html(responseText);
                    if ($("#account-add-body .alert").size() === 0) {
                        $("#account-add-modal").modal('hide');
                    }
                }
            });
        });

    });

<!-- http://stackoverflow.com/questions/14045515/how-can-i-reuse-one-bootstrap-modal-div -->
</script>