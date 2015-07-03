package org.ua.oblik.controllers.utils;

import java.beans.PropertyEditorSupport;

import org.ua.oblik.controllers.beans.LoginAction;

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
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LoginAction.valueOf(text.toUpperCase()));
    }
}
