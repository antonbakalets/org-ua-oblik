$(document).ready(function() {
        $(".datepicker").addDatepicker(); 
});

$.fn.addDatepicker = function() {
    this.datepicker({
        altFormat : 'dd.mm.yy',
        dateFormat : 'dd.mm.yy',
        showOn : "button",
        buttonImage : "../img/ico_calendar.gif",
        buttonText : '',
        buttonImageOnly : true
    });
}

function changeLinksInSelector(selector, functionName) {
    $(selector).each(function() {
        linkData = $(this).attr("href");
        if (linkData) {
            newLink = "JavaScript:" + functionName + "('"+linkData+"');"; // on pagination, sort change
            $(this).attr("href", newLink);
        }
    });
}
    
$.fn.restrictToNumbers = function() {
    $(this).keyup(function () { 
        this.value = this.value.replace(/[^0-9\.]/g,'');
    });
}

function split( val ) {
    return val.split( /,\s*/ );
}

function extractLast( term ) {
    return split( term ).pop();
}

