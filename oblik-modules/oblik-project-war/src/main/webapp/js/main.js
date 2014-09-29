
$.fn.datepicker.defaults.format = "dd.mm.yyyy";
$.fn.datepicker.defaults.weekStart = 1;
$.fn.datepicker.defaults.todayBtn = "linked";

jQuery(function($) {
    'use strict';

    var App = {
        init: function(contextPath) {
            this.contextPath = contextPath;
            this.loadActionsForm(this.contextPath + '/formaction.html');
            this.loadTotalByCurrency();
            this.loadTotalByAccount();
            this.loadTransactions();
            this.loadAccounts();
        },
        loadActionsForm: function(href) {
            $("#section-actions").load(href, function() {
                App.initActionsForm(href);
            });
        },
        initActionsForm: function(href) {
            $("#actions-type li").click(function() {
                if (!$("#txId").val()) {
                    $(this).addClass('active');
                    $("#actions-type li").not(this).each(function() {
                        $(this).removeClass('active');
                    });
                    App.setActionsType($("#actions-type li").index($(this)));
                }
            });
            $("#form-actions .datepicker").datepicker();
            $("#form-actions .calculable").calculable();
            $('#action-button').click(function() {
                $('#form-actions').ajaxSubmit({
                    success: function(data)
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
        },
        loadTotalByCurrency: function() {
            $("#total-by-currency").load(this.contextPath + '/currency/list.html', function() {
                $("#total-by-currency .edit-link").ineditable({
                    success: function() {
                        reactor.dispatchEvent('currencyAdded');
                    }
                });
            });
        },
        loadTotalByAccount: function() {
            $("#total-by-account").load(this.contextPath + '/total/account.html', function() {
                $("#total-by-account .edit-link").ineditable({
                    success: function() {
                        reactor.dispatchEvent('accountEdited');
                    }
                });
            });
        },
        loadTransactions: function() {
            $("#tab-transactions").load(this.contextPath + '/transaction/list.html', function() {
                $('#tab-transactions a.transaction-edit').each(function() {
                    $(this).click(function(e) {
                        e.preventDefault();
                        reactor.dispatchEvent('transactionEdit', $(this).attr('href'));
                    });
                });
            });
        },
        loadAccounts: function() {
            $("#section-incomes").load(this.contextPath + '/account/incomes.html', function() {
                $("#section-incomes .edit-link").ineditable({
                    success: function() {
                        reactor.dispatchEvent('accountEdited');
                    }
                });
            });
            $("#section-expenses").load(this.contextPath + '/account/expenses.html', function() {
                $("#section-expenses .edit-link").ineditable({
                    success: function() {
                        reactor.dispatchEvent('accountEdited');
                    }
                });
            });
        },
        setActionsType: function(index) {
            if (index === 2) {
                $('#type').val("INCOME");
                // TODO reload accounts select elems
                //$('#second-ammount-div').hide('slow');
            } else if (index === 1) {
                $('#type').val("TRANSFER");
                // TODO reload accounts select elems
                //$('#second-ammount-div').show('slow');
            } else {
                $('#type').val("EXPENSE");
                // TODO reload accounts select elems
                //$('#second-ammount-div').hide('slow');
            }
        }
    };

    App.init($('#contextPath').text());



    function Event(name) {
        this.name = name;
        this.callbacks = [];
    }
    Event.prototype.registerCallback = function(callback) {
        this.callbacks.push(callback);
    };




    function Reactor() {
        this.events = {};
    }

    Reactor.prototype.registerEvent = function(eventName) {
        var event = new Event(eventName);
        this.events[eventName] = event;
    };

    Reactor.prototype.dispatchEvent = function(eventName, eventArgs) {
        if (eventName) {
            console.log('Dispathing event: ' + eventName);
            this.events[eventName].callbacks.forEach(function(callback) {
                callback(eventArgs);
            });
        } else {
            console.error('Unknown event name: ' + eventName);
        }
    };

    Reactor.prototype.addEventListener = function(eventName, callback) {
        this.events[eventName].registerCallback(callback);
    };




    var reactor = new Reactor();

    reactor.registerEvent('currencyAdded');
    reactor.registerEvent('currencyEdited');
    reactor.registerEvent('accountEdited');
    reactor.registerEvent('transactionSave');
    reactor.registerEvent('transactionEdit');
    reactor.registerEvent('transactionTypeChange');

    reactor.addEventListener('currencyAdded', function() {
        App.loadTotalByCurrency();
        App.loadExpenseForm();
    });

    reactor.addEventListener('currencyEdited', function() {
        App.loadTotalByCurrency();
    });

    reactor.addEventListener('accountEdited', function() {
        App.loadAccounts();
        App.loadTotalByAccount();
    });

    reactor.addEventListener('transactionSave', function() {
        App.loadTransactions();
        App.loadAccounts();
        App.loadTotalByAccount();
        App.loadTotalByCurrency();
    });

    reactor.addEventListener('transactionEdit', function(href) {
        App.loadActionsForm(href);
    });
});
