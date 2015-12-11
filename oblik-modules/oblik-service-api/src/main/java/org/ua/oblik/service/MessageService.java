package org.ua.oblik.service;

public interface MessageService {

    void sendMessage(String from, String to, String subject, String msg);
}
