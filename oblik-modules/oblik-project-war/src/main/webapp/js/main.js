$(document).ready(function() {
    $(".datepicker").addDatepicker();
});

$.fn.addDatepicker = function() {
    this.datepicker({
        altFormat: 'dd.mm.yy',
        dateFormat: 'dd.mm.yy',
        showOn: "button",
        buttonImage: "../img/ico_calendar.gif",
        buttonText: '',
        buttonImageOnly: true
    });
}

function changeLinksInSelector(selector, functionName) {
    $(selector).each(function() {
        linkData = $(this).attr("href");
        if (linkData) {
            newLink = "JavaScript:" + functionName + "('" + linkData + "');"; // on pagination, sort change
            $(this).attr("href", newLink);
        }
    });
}

$.fn.restrictToNumbers = function() {
    $(this).keyup(function() {
        this.value = this.value.replace(/[^0-9\.]/g, '');
    });
}

function split(val) {
    return val.split(/,\s*/);
}

function extractLast(term) {
    return split(term).pop();
}
function attachModal() {
    $('a[data-toggle="modal"]').click(function() {
        $('#common-modal-body').load(
                $(this).attr('href'),
                function(response, status, xhr) {
                    if (status === 'error') {
                        $('#common-modal-body').html('<h2>Oh boy</h2><p>Sorry, but there was an error:' + xhr.status + ' ' + xhr.statusText + '</p>');
                    }
                    return this;
                }
        );
        $('#common-modal-label').html($(this).attr('title'));
    });

    $('#common-modal-save').click(function() {
        $('#common-modal form').ajaxSubmit({
            success: function(responseText, statusText, xhr, $form) {
                $('#common-modal-body').html('');
                $('#common-modal-body').html(responseText);
                if ($("#common-modal-body .alert").size() === 0) {
                    $("#common-modal").modal('hide');
                }
                // TODO $('#right-tabs li.active').load();
            }
        });
    });
}

