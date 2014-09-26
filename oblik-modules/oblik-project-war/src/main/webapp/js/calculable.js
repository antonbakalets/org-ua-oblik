
$.widget("oblik.calculable", {
    options: {
        calculable: "calculable",
        pattern: /^[^a-zA-Z][-*+/()\d\s\.]*$/g,
        forbidden: /[^-*+/\d\.\,]/g
    },
    _create: function () {
        this.element.addClass(this.options.calculable);
        this._on(this.element, {keyup: "_keydown"});
        if (this.element.next().length > 0) {
            this.element.next().css('cursor', 'pointer');
            this._on(this.element.next(), {click: "_calc"});
        }
    },
    _keydown: function (e) {
        if (e.which === 13) { //Enter key
            this._calc();
        } else {
            this.element.val(this.element.val().replace(this.options.forbidden, ''));
        }
    },
    _calc: function () {
        this.element.val(this.calculate(this.element.val()));
    },
    calculate: function (expression) {
        var result = expression;
        try {
            var valid = this.options.pattern.test(expression);
            if (valid) {
                result = eval(expression);
            }
        } catch (e) {
            // do nothing
        }
        return result;
    },
    _destroy: function () {
        this.element.next().css('cursor', 'auto');
        this.element.removeClass(this.options.calculable);
    }
});
