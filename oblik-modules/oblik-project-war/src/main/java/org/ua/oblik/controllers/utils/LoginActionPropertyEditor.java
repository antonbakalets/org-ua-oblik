package org.ua.oblik.controllers.utils;

import org.ua.oblik.controllers.beans.LoginAction;

import java.beans.PropertyEditorSupport;

/**
 *
 */
public class LoginActionPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        LoginAction value = (LoginAction) getValue();
        return value.toString().toLowerCase();
    }

    @Override
    public void setAsText(String text) {
        setValue(LoginAction.valueOf(text.toUpperCase()));
    }
}
