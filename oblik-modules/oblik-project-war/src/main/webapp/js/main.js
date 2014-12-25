$.fn.datepicker.defaults.format = "dd.mm.yyyy";
$.fn.datepicker.defaults.weekStart = 1;
$.fn.datepicker.defaults.todayBtn = "linked";

jQuery(function ($) {
    'use strict';

    var application = {
        init: function (contextPath) {
            this.contextPath = contextPath;
            this.actionType = '';
            this.page = contextPath + '/transaction/list.html';
            this.loadTotal();
            this.loadActionsForm();
            this.loadTotalByCurrency();
            this.loadTotalByAccount();
            this.loadTransactions();
            this.loadAccounts();
        },
        loadTotal: function () {
            $("#default-total").load(this.contextPath + '/total/header.html', function () {

            });
        },
        loadActionsForm: function (href) {
            if (!href) {
                href = this.contextPath + '/formaction.html';
            }
            $("#section-actions").load(href, function () {
                application.initActionsForm();
            });
        },
        initActionsForm: function () {
            $("#actions-type li").click(function () {
                if (!$("#txId").val()) {
                    $(this).addClass('active');
                    $("#actions-type li").not(this).each(function () {
                        $(this).removeClass('active');
                    });
                }
                application.setActionsType($("#actions-type li").index($(this)));
                application.loadFirstAccountOptions();
                application.loadSecondAccountOptions();
            });
            application.setActionsType($("#actions-type li").index($(this)));
            application.loadFirstAccountOptions();
            application.loadSecondAccountOptions();
            $("#form-actions .datepicker").datepicker();
            $("#form-actions .calculable").calculable();
            $('#action-button').click(function (e) {
                e.preventDefault();
                $('#form-actions').ajaxSubmit({
                    success: function (data) {
                        $('#section-actions').html(data);
                        if ($("#section-actions .alert").size() === 0) {
                            var txId = $("#section-actions #txId").val();
                            reactor.dispatchEvent('transactionSave', txId);
                        }
                        application.initActionsForm();
                    }
                });
            });
            $('#action-cancel').click(function (e) {
                e.preventDefault();
                application.loadActionsForm();
            });
            $('#action-delete').confirmation({singleton: true,
                onConfirm: function () {
                    var deleteRef = $('#action-delete').attr('href');
                    $("#default-total").load(deleteRef, function (response, status, xhr) {
                        if (response === "deleted") {
                            reactor.dispatchEvent('transactionDelete');
                        }
                    });
                }
            });
            $('#account-from').change(function () {
                application.loadSecondAccountOptions();
            });
            $('#account-to').change(function () {
                application.loadFirstAccountOptions();
            });
        },
        loadTotalByCurrency: function () {
            $("#total-by-currency").load(this.contextPath + '/currency/list.html', function () {
                $("#total-by-currency .edit-link").ineditable({
                    onSuccess: function () {
                        reactor.dispatchEvent('currencySave');
                    },
                    onEdit: function () {
                        reactor.dispatchEvent('currencyEdit');
                    }
                });
            });
        },
        loadTotalByAccount: function () {
            $("#total-by-account").load(this.contextPath + '/total/account.html', function () {
                $("#total-by-account .edit-link").ineditable({
                    onSuccess: function () {
                        reactor.dispatchEvent('accountSave');
                    },
                    onEdit: function () {
                        reactor.dispatchEvent('accountEdit');
                    }
                });
            });
        },
        loadTransactions: function (href) {
            if (!href) {
                href = this.page;
            }
            $("#tab-transactions").load(href, function () {
                this.page = $('#transaction-curr').attr('href');
                $('#transaction-prev').click(function (e) {
                    e.preventDefault();
                    application.loadTransactions($(this).attr('href'));
                });
                $('#transaction-curr').text(application.localizeMonth($('#transaction-curr').text()));
                $('#transaction-next').click(function (e) {
                    e.preventDefault();
                    application.loadTransactions($(this).attr('href'));
                });
                $('#tab-transactions a.transaction-edit').each(function () {
                    $(this).click(function (e) {
                        e.preventDefault();
                        reactor.dispatchEvent('transactionEdit', $(this).attr('href'));
                    });
                });
            });
        },
        loadAccounts: function () {
            $("#section-incomes").load(this.contextPath + '/account/incomes.html', function () {
                $("#section-incomes .edit-link").ineditable({
                    onSuccess: function () {
                        reactor.dispatchEvent('accountSave');
                    },
                    onEdit: function () {
                        reactor.dispatchEvent('accountEdit');
                    }
                });
            });
            $("#section-expenses").load(this.contextPath + '/account/expenses.html', function () {
                $("#section-expenses .edit-link").ineditable({
                    onSuccess: function () {
                        reactor.dispatchEvent('accountSave');
                    },
                    onEdit: function () {
                        reactor.dispatchEvent('accountEdit');
                    }
                });
            });
        },
        setActionsType: function (index) {
            if (index === 2) {
                this.actionType = "INCOME";
                $('#second-ammount-div').hide('blind');
            } else if (index === 1) {
                this.actionType = "TRANSFER";
                $('#second-ammount-div').show('blind');
            } else {
                this.actionType = "EXPENSE";
                $('#second-ammount-div').hide('blind');
            }
        },
        loadFirstAccountOptions: function () {
            var accountType = this.actionType === "INCOME" ? "INCOME" : "ASSETS";
            var optionsUrl = this.contextPath + '/account/options.json?type=' + accountType;
            $.getJSON(optionsUrl, function (data) {
                $('#account-from option').not('#account-from :first').remove();
                for (var i in data) {
                    var id = data[i].id;
                    var name = data[i].name;
                    var currId = data[i].currency;
                    var option = $('<option>' + name + '</option>');
                    option.attr('currency', currId);
                    option.val(id);
                    $('#account-from').append(option);
                }
            });
        },
        loadSecondAccountOptions: function () {
            var accountType = this.actionType === "EXPENSE" ? "EXPENSE" : "ASSETS";
            var optionsUrl = this.contextPath + '/account/options.json?type=' + accountType;
            /*var currency = $('#account-from :selected').attr('currency');
            if (currency) {
                optionsUrl = optionsUrl + "&currency=" + currency;
            }*/
            $.getJSON(optionsUrl, function (data) {
                $('#account-to option').not('#account-to :first').remove();
                for (var i in data) {
                    var id = data[i].id;
                    var name = data[i].name;
                    var currId = data[i].currency;
                    var option = $('<option>' + name + '</option>');
                    option.attr('currency', currId);
                    option.val(id);
                    $('#account-to').append(option);
                }
            });
        },
        localizeMonth: function (month) {
            var index = parseInt(month.substring(0, 2)) - 1;
            return $.fn.datepicker.dates['ua'].months[index] + " " + month.substring(2, 6);
        }
    };

    application.init($('#contextPath').text());


    function Event(name) {
        this.name = name;
        this.callbacks = [];
    }

    Event.prototype.registerCallback = function (callback) {
        this.callbacks.push(callback);
    };


    function Reactor() {
        this.events = {};
    }

    Reactor.prototype.registerEvent = function (eventName) {
        var event = new Event(eventName);
        this.events[eventName] = event;
    };

    Reactor.prototype.dispatchEvent = function (eventName, eventArgs) {
        if (eventName) {
            console.log('Dispathing event: ' + eventName);
            this.events[eventName].callbacks.forEach(function (callback) {
                callback(eventArgs);
            });
        } else {
            console.error('Unknown event name: ' + eventName);
        }
    };

    Reactor.prototype.addEventListener = function (eventName, callback) {
        this.events[eventName].registerCallback(callback);
    };


    var reactor = new Reactor();

    reactor.registerEvent('currencyEdit');
    reactor.registerEvent('currencySave');
    reactor.registerEvent('accountEdit');
    reactor.registerEvent('accountSave');
    reactor.registerEvent('transactionEdit');
    reactor.registerEvent('transactionSave');
    reactor.registerEvent('transactionDelete');
    reactor.registerEvent('transactionTypeChange');
    reactor.registerEvent('firstAccountOptionChange');
    reactor.registerEvent('secondAccountOptionChange');

    reactor.addEventListener('currencyEdit', function () {
        application.loadActionsForm();
    });

    reactor.addEventListener('currencySave', function () {
        application.loadTotal();
        application.loadAccounts();
        application.loadTransactions();
        application.loadTotalByAccount();
    });

    reactor.addEventListener('accountEdit', function () {
        application.loadActionsForm();
    });

    reactor.addEventListener('accountSave', function () {
        application.loadTotal();
        application.loadAccounts();
        application.loadTransactions();
        application.loadTotalByAccount();
        application.loadFirstAccountOptions();
        application.loadSecondAccountOptions();
    });

    reactor.addEventListener('transactionEdit', function (href) {
        $(".ineditable").ineditable("closeInEditing");
        application.loadActionsForm(href);
    });

    reactor.addEventListener('transactionSave', function () {
        application.loadActionsForm();
        application.loadTotal();
        application.loadTransactions();
        application.loadAccounts();
        application.loadTotalByAccount();
        application.loadTotalByCurrency();
    });

    reactor.addEventListener('transactionDelete', function () {
        application.loadActionsForm();
        application.loadTotal();
        application.loadTransactions();
        application.loadAccounts();
        application.loadTotalByAccount();
        application.loadTotalByCurrency();
    });

    reactor.addEventListener('firstAccountOptionChange', function () {
        application.loadSecondAccountOptions();
    });

    reactor.addEventListener('secondAccountOptionChange', function () {
        application.loadFirstAccountOptions();
    });
});
