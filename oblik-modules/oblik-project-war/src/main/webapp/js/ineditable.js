$.widget("oblik.ineditable", {
    options: {
        ineditable: "ineditable",
        inediting: "inediting",
        removable: "removable",
        effect: "blind",
        save: "<span class='glyphicon glyphicon-ok'></span>",
        close: "<span class='glyphicon glyphicon-remove'></span>",
        remove: "<span class='glyphicon glyphicon-trash'></span>",
        onSuccess: function () {
        },
        onEdit: function () {
        },
        onRemove: function () {
        },
        onRemoveFails: function () {
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
            this.closeBtn = $("<button type='button' class='btn btn-link btn-sm pull-right'/>");
            this.closeBtn.append(this.options.close);
            btnContainer.append(this.closeBtn);
            this._on(this.closeBtn, {click: "close"});

            this.saveBtn = $("<button type='button' class='btn btn-link btn-sm pull-right'/>");
            this.saveBtn.append(this.options.save);
            btnContainer.append(this.saveBtn);
            this._on(this.saveBtn, {click: "_save"});

            this.removeBtn = $("<button type='button' class='btn btn-link btn-sm'/>");
            this.removeBtn.append(this.options.remove);
            btnContainer.append(this.removeBtn);
            this._on(this.removeBtn, {click: "_remove"});

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
            if (reference.formDiv.find('input[name="' + reference.options.removable + '"]').val() === "true") {
                reference.removeBtn.show();
            } else {
                reference.removeBtn.hide();
            }
        });

    },
    _save: function () {
        var reference = this;
        this.formDiv.find('form').ajaxSubmit({
            success: function (responseText) {
                reference.formDiv.html(responseText);
                if (reference.formDiv.find(".alert").size() === 0) {
                    if ($.isFunction(reference.options.onSuccess)) {
                        reference.options.onSuccess.call();
                    }
                    reference.close();
                }
            }
        });
    },
    _remove: function () {
        var reference = this;
        var urlValue = reference.ahref.replace("edit.html", "delete.json");
        $.ajax({ method: "GET", url: urlValue })
            .done(function (data) {
                if (data) {
                    if ($.isFunction(reference.options.onRemove())) {
                        reference.options.onRemove.call();
                    }
                    var parent = reference.element.parent();
                    parent.hide(reference.options.effect, function () {
                        parent.remove();
                    });
                } else {
                    if ($.isFunction(reference.options.onRemoveFails())) {
                        reference.options.onRemoveFails.call();
                    }
                }
            })
            .fail(function () {
                reference.close();
                if ($.isFunction(reference.options.onRemoveFails())) {
                    reference.options.onRemoveFails.call();
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
})
;
