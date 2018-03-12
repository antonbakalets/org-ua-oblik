package org.ua.oblik.soap;

import javax.xml.ws.Endpoint;

public class RedirectPublisher {

    public static void main(String[] args) {
        String url = "http://localhost:8888/budget";
        System.out.println("Publishing RedirectService at endpoint: " + url);
        Endpoint.publish(url, new RedirectServiceImpl());
    }
}
