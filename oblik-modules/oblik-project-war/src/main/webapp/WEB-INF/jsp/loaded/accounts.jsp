<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<h4>Assets Accounts</h4>
<display:table id="assetsAccounts"
               name="assetsAccounts"
               requestURI="/accounts/list.html"
               >
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="account name"/>
    <display:column property="currencySymbol" title="currency"/>

</display:table>
<h4>Income Accounts</h4>
<display:table id="incomeAccounts"
               name="incomeAccounts"
               requestURI="/accounts/list.html"
               >
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="account name"/>
    <display:column property="currencySymbol" title="currency"/>

</display:table>
<h4>Expence Accounts</h5>
<display:table id="expenseAccounts"
               name="expenseAccounts"
               requestURI="/accounts/list.html"
               >
    <display:column property="accountId" title="accountId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="name" title="account name"/>
    <display:column property="currencySymbol" title="currency"/>

</display:table>
<a href="#myModal" role="button" class="btn" data-toggle="modal">Додати рахунок</a>
 
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Modal header</h3>
  </div>
  <div class="modal-body">
    <p>One fine body…</p>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    <button class="btn btn-primary">Save changes</button>
  </div>
</div>