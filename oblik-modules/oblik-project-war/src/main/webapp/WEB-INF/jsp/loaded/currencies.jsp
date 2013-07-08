<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags" %>

<spring:message var="title_symbol" code="jsp.oblik.currency.symbol"/>
<spring:message var="title_rate" code="jsp.oblik.currency.rate"/> 

<display:table id="currecy-table"
               name="currencyList"
               requestURI="/currency/list.html"
               class="table table-striped table-hover table-condensed">
    <display:column property="currencyId" title="currencyId" 
                    class="ui-helper-hidden" headerClass="ui-helper-hidden"/>
    <display:column property="symbol" title="${title_symbol}"/>
    <display:column property="rate" title="${title_rate}"/>

</display:table>

<a id="file_attach" data-toggle="modal" href="${pageContext.request.contextPath}/currency/edit.html" data-target="#currency-add-modal" class="btn">
    <spring:message code="jsp.oblik.button.add.currency"/>
</a>

<!-- Modal -->
<div id="currency-add-modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="currency-add-label" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h2 id="currency-add-label"><spring:message code="jsp.oblik.currency.add.header"/></h2>
    </div>
    <div class="modal-body" id="currency-add-body">
        <p>One fine body…this is getting replaced with content that comes from passed-in href</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal"><spring:message code="jsp.oblik.button.cancel"/></button>
        <button id="currency-add-save" class="btn btn-primary"><spring:message code="jsp.oblik.button.save"/></button>
    </div>
</div>

<script>
    $(document).ready(function() {

        $('a[data-toggle="modal"]').on('click', function() {
            $('#currency-add-body').load(
                    $(this).attr('href'),
                    function(response, status, xhr) {
                        if (status === 'error') {
                            //console.log('got here');
                            $('#currency-add-body').html('<h2>Oh boy</h2><p>Sorry, but there was an error:' + xhr.status + ' ' + xhr.statusText + '</p>');
                        }
                        return this;
                    }
            );
        });


        $('#currency-add-save').click(function currencyFormSubmit() {
            $('#form-currency').ajaxSubmit({
                success: function(responseText, statusText, xhr, $form) {
                    $('#currency-add-body').html('');
                    $('#currency-add-body').html(responseText);
                    if ($("#currency-add-body .alert").size() === 0) {
                        $("#currency-add-modal").modal('hide');
                    }
                }
            });
        });

    });

<!-- http://stackoverflow.com/questions/14045515/how-can-i-reuse-one-bootstrap-modal-div -->
</script>