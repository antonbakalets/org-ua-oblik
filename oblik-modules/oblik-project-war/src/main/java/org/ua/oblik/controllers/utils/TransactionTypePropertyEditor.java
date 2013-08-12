package org.ua.oblik.controllers.utils;

import java.beans.PropertyEditorSupport;
import org.ua.oblik.service.beans.TransactionType;

/**
 *
 * @author Anton Bakalets
 */
public class TransactionTypePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        TransactionType value = (TransactionType)getValue();
        return value.toString().toLowerCase();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(TransactionType.valueOf(text.toUpperCase()));
    }
    
}
