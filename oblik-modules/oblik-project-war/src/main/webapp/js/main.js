
$.fn.datepicker.defaults.format = "dd.mm.yyyy";
$.fn.datepicker.defaults.weekStart = 1;
$.fn.datepicker.defaults.todayBtn = "linked";

$.fn.attachEditForm = function () {
    $(this).find('a[data-toggle="modal"]').click(function (e) {
        e.preventDefault();
        var ahref = $(this).attr('href');
        $(this).before('<div/>');
        $(this).prev().load(ahref,
                function (response, status, xhr) {
                    if (status === 'error') {
                        alert('error loading from: ' + ahref);
                    }
                    return this;
                }
        );
    });
};

$.fn.attachModal = function () {
    $(this).find('a[data-toggle="modal"]').click(function () {
        $('#common-modal-label').text($(this).attr('title'));
        $('#common-modal-event').text($(this).attr('save-event'));
        $('#common-modal-body').load(
                $(this).attr('href'),
                function (response, status, xhr) {
                    if (status === 'error') {
                        $('#common-modal-body').html('<h2>Oh boy</h2><p>Sorry, but there was an error:' + xhr.status + ' ' + xhr.statusText + '</p>');
                    }
                    return this;
                }
        );
    });
};

jQuery(function ($) {
    'use strict';

    var App = {
        init: function (contextPath) {
            this.contextPath = contextPath;
            this.loadExpenseForm();
            this.loadTransferForm();
            this.loadIncomeForm();
            this.loadTotalByCurrency();
            this.loadTotalByAccount();
            this.loadTransactions();
            this.loadAccounts();
            this.modalSaveEvent();
        },
        loadExpenseForm: function () {
            $("#tab-expense").load(this.contextPath + '/formaction.html?type=expense', function () {
                App.initExpenseForm();
            });
        },
        loadTransferForm: function () {
            $("#tab-transfer").load(this.contextPath + '/formaction.html?type=transfer', function () {
                App.initTransferForm();
            });
        },
        loadIncomeForm: function () {
            $("#tab-income").load(this.contextPath + '/formaction.html?type=income', function () {
                App.initIncomeForm();
            });
        },
        initExpenseForm: function (id) {
            $("#form-expense .datepicker").datepicker();
            $("#form-expense .calculable").calculable();
            $('#form-expense-button').click(function () {
                $('#form-expense').ajaxSubmit({
                    success: function (data)
                    {
                        $('#tab-expense').html(data);
                        if ($("#tab-expense .alert").size() === 0) {
                            reactor.dispatchEvent('transactionSave', id);
                        }
                        App.initExpenseForm();
                    }
                });
            });
        },
        initTransferForm: function () {
            $("#form-transfer .datepicker").datepicker();
            $("#form-transfer .calculable").calculable();
            $('#form-transfer-button').click(function () {
                $('#form-transfer').ajaxSubmit({
                    success: function (data)
                    {
                        $('#tab-transfer').html(data);
                        if ($("#tab-transfer .alert").size() === 0) {
                            reactor.dispatchEvent('transactionSave');
                        }
                        App.initTransferForm();
                    }
                });
            });

            /* TODO
             * $('#account-from, #account-to').change(function() {
             if ($('#account-from').filter(':selected').attr('currency') === $('#account-to option:selected').attr('currency')) {
             $('#second-ammount-div').hide('slow');
             } else {
             $('#second-ammount-div').show('slow');
             }
             });*/
        },
        initIncomeForm: function () {
            $("#form-income .datepicker").datepicker();
            $("#form-income .calculable").calculable();
            $('#form-income-button').click(function () {
                $('#form-income').ajaxSubmit({
                    success: function (data)
                    {
                        $('#tab-income').html(data);
                        if ($("#tab-income .alert").size() === 0) {
                            reactor.dispatchEvent('transactionSave');
                        }
                        App.initIncomeForm();
                    }
                });
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
        loadTransactions: function () {
            $("#tab-transactions").load(this.contextPath + '/transaction/list.html', function () {
                $(a['.transactionEdit']).each(function () {
                    $(this).click(function (e) {
                        e.preventDefault();
                        reactor.dispatchEvent('accountEdit', $(this).attr('id'));
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
        modalSaveEvent: function () {
            $('#common-modal-save').click(function () {
                $('#common-modal form').ajaxSubmit({
                    success: function (responseText, statusText, xhr, $form) {
                        $('#common-modal-body').html('');
                        $('#common-modal-body').html(responseText);
                        if ($("#common-modal-body .alert").size() === 0) {
                            $("#common-modal").modal('hide');
                            reactor.dispatchEvent($('#common-modal-event').text());
                        }
                    }
                });
            });
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
    });

    reactor.addEventListener('transactionSave', function () {
        App.loadTransactions();
        App.loadAccounts();
        App.loadTotalByAccount();
        App.loadTotalByCurrency();
    });
});
