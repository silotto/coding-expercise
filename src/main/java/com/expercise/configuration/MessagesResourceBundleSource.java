package com.expercise.configuration;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.ResourceBundle;

public class MessagesResourceBundleSource extends ResourceBundleMessageSource {

    public ResourceBundle getMessagesResourceBundle() {
        return getResourceBundle("messages", LocaleContextHolder.getLocale());
    }

    public ResourceBundle getEmailMessagesResourceBundle() {
        return getResourceBundle("messagesForEmails", LocaleContextHolder.getLocale());
    }

}
