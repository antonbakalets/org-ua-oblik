package org.ua.oblik.soap;

public class RedirectException extends Exception {

    final private int httpStatus;

    public RedirectException(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
