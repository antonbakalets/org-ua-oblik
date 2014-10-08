
$.fn.datepicker.defaults.format = "dd.mm.yyyy";
$.fn.datepicker.defaults.weekStart = 1;
$.fn.datepicker.defaults.todayBtn = "linked";

jQuery(function ($) {
    'use strict';

    var App = {
        init: function (contextPath) {
            this.contextPath = contextPath;
            this.actionType = '';
            this.loadActionsForm(this.contextPath + '/formaction.html');
            this.loadTotalByCurrency();
            this.loadTotalByAccount();
            this.loadTransactions(this.contextPath + '/transaction/list.html');
            this.loadAccounts();
        },
        loadActionsForm: function (href) {
            $("#section-actions").load(href, function () {
                this.actionType = $('#type').val().toUpperCase();
                App.initActionsForm(href);
            });
        },
        initActionsForm: function (href) {
            $("#actions-type li").click(function () {
                if (!$("#txId").val()) {
                    $(this).addClass('active');
                    $("#actions-type li").not(this).each(function () {
                        $(this).removeClass('active');
                    });
                    App.setActionsType($("#actions-type li").index($(this)));
                }
            });
            $("#form-actions .datepicker").datepicker();
            $("#form-actions .calculable").calculable();
            $('#action-button').click(function () {
                $('#form-actions').ajaxSubmit({
                    success: function (data)
                    {
                        $('#section-actions').html(data);
                        if ($("#section-actions .alert").size() === 0) {
                            var txId = $("#section-actions #txId").val();
                            reactor.dispatchEvent('transactionSave', txId);
                        }
                        App.initActionsForm();
                    }
                });
            });
            $('#account-from').change(function () {
                if (!$('#account-to :selected').val()) {
                    App.loadSecondAccountOptions($('#account-from :selected').attr('currency'));
                }
            });
            $('#account-to').change(function () {
                if (!$('#account-from :selected').val()) {
                    App.loadFirstAccountOptions($('#account-to :selected').attr('currency'));
                }
            });
        },
        loadTotalByCurrency: function () {
            $("#total-by-currency").load(this.contextPath + '/currency/list.html', function () {
                $("#total-by-currency .edit-link").ineditable({
                    success: function () {
                        reactor.dispatchEvent('currencyAdded');
                    }
                });
            });
        },
        loadTotalByAccount: function () {
            $("#total-by-account").load(this.contextPath + '/total/account.html', function () {
                $("#total-by-account .edit-link").ineditable({
                    success: function () {
                        reactor.dispatchEvent('accountEdited');
                    }
                });
            });
        },
        loadTransactions: function (href) {
            $("#tab-transactions").load(href, function () {
                $('#transaction-prev').click(function(e) {
                    e.preventDefault();
                    App.loadTransactions($(this).attr('href'));
                });
                $('#transaction-curr').text(App.localizeMonth($('#transaction-curr').text()));
                $('#transaction-next').click(function(e) {
                    e.preventDefault();
                    App.loadTransactions($(this).attr('href'));
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
                    success: function () {
                        reactor.dispatchEvent('accountEdited');
                    }
                });
            });
            $("#section-expenses").load(this.contextPath + '/account/expenses.html', function () {
                $("#section-expenses .edit-link").ineditable({
                    success: function () {
                        reactor.dispatchEvent('accountEdited');
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
            $('#type').val(this.actionType);
            App.loadFirstAccountOptions();
            App.loadSecondAccountOptions();
        },
        loadFirstAccountOptions: function (currency) {
            var accountType = this.actionType === "INCOME" ? "INCOME" : "ASSETS";
            var optionsUrl = this.contextPath + '/account/options.json?type=' + accountType;
            if (this.actionType !== "TRANSFER" && currency) {
                optionsUrl = optionsUrl + "&currency=" + currency;
            }
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
        loadSecondAccountOptions: function (currency) {
            var accountType = this.actionType === "EXPENSE" ? "EXPENSE" : "ASSETS";
            var optionsUrl = this.contextPath + '/account/options.json?type=' + accountType;
            if (this.actionType !== "TRANSFER" && currency) {
                optionsUrl = optionsUrl + "&currency=" + currency;
            }
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

    App.init($('#contextPath').text());



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

    reactor.registerEvent('currencyAdded');
    reactor.registerEvent('currencyEdited');
    reactor.registerEvent('accountEdited');
    reactor.registerEvent('transactionSave');
    reactor.registerEvent('transactionEdit');
    reactor.registerEvent('transactionTypeChange');
    reactor.registerEvent('firstAccountOptionChange');
    reactor.registerEvent('secondAccountOptionChange');

    reactor.addEventListener('currencyAdded', function () {
        App.loadTotalByCurrency();
        App.loadExpenseForm();
    });

    reactor.addEventListener('currencyEdited', function () {
        App.loadTotalByCurrency();
    });

    reactor.addEventListener('accountEdited', function () {
        App.loadAccounts();
        App.loadTotalByAccount();
        App.loadFirstAccountOptions();
        App.loadSecondAccountOptions();
    });

    reactor.addEventListener('transactionSave', function () {
        App.loadTransactions();
        App.loadAccounts();
        App.loadTotalByAccount();
        App.loadTotalByCurrency();
    });

    reactor.addEventListener('transactionEdit', function (href) {
        App.loadActionsForm(href);
    });

    reactor.addEventListener('firstAccountOptionChange', function () {
        App.loadSecondAccountOptions();
    });

    reactor.addEventListener('secondAccountOptionChange', function () {
        App.loadFirstAccountOptions();
    });
});
