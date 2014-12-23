
$.widget("oblik.ineditable", {
    options: {
        ineditable: "ineditable",
        inediting: "inediting",
        effect: "blind",
        save: "<span class='glyphicon glyphicon-ok'></span>",
        close: "<span class='glyphicon glyphicon-remove'></span>",
        onSuccess: function () {
        },
        onEdit: function () {
        }
    },
    _create: function () {
        this.element.addClass(this.options.ineditable);

        this.ahref = this.element.find('a').attr('href');
        this._on(this.element, {click: "_inediting"});
        this._createContainer();
    },
    _createContainer: function () {
        var exists = typeof this.container !== 'undefined';
        if (!exists) {
            this.container = $("<div/>");
            this.container.attr("style", "display: none;");
            this.element.before(this.container);
            this.formDiv = $("<div/>");
            this.container.append(this.formDiv);

            var btnContainer = $("<div/>");
            this.saveBtn = $("<button type='button' class='btn btn-link btn-sm'/>");
            this.saveBtn.append(this.options.save);
            btnContainer.append(this.saveBtn);

            this.closeBtn = $("<button type='button' class='btn btn-link btn-sm'/>");
            this.closeBtn.append(this.options.close);
            btnContainer.append(this.closeBtn);
            this._on(this.closeBtn, {click: "close"});

            this.container.append(btnContainer);
            this.container.append($("<div class='clearfix'/>"));
        }
    },
    _show: function () {
        var reference = this;
        this.element.hide(this.options.effect, function () {
            reference.container.show(reference.options.effect);
        });
        this.element.addClass(this.options.inediting);
    },
    _hide: function () {
        var reference = this;
        this.container.hide(this.options.effect, function () {
            reference.element.show(reference.options.effect);
        });
        this.element.removeClass(this.options.inediting);
    },
    _inediting: function (e) {
        e.preventDefault();
        this.closeInEditing();
        this.element.addClass(this.options.inediting);
        this._show();
        this._load();
    },
    _load: function () {
        var reference = this;
        this.formDiv.load(this.ahref, function () {
            if ($.isFunction(reference.options.onEdit)) {
                reference.options.onEdit.call();
            }
        });
        this._on(this.saveBtn, {click: "_save"});
    },
    _save: function () {
        var reference = this;
        this.formDiv.find('form').ajaxSubmit({
            success: function (responseText, statusText, xhr, $form) {
                reference.formDiv.html(responseText);
                if (reference.formDiv.find(".alert").size() === 0) {
                    reference.close();
                    if ($.isFunction(reference.options.onSuccess)) {
                        reference.options.onSuccess.call();
                    }
                }
            }
        });
    },
    closeInEditing: function () {
        $('.' + this.options.inediting).not(this.element).each(function () {
            var el = $(this).data("oblik-ineditable");
            el.close();
        });
    },
    close: function () {
        console.log("closing: " + this.ahref);
        this._hide();
    },
    _destroy: function () {
        this._hide();
        this.element.removeClass(this.options.ineditable);
    }
});
