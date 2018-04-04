package org.ua.oblik.rest.v1.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

/**
 * POJO for successful authentication responses
 */
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = -8939405999907830967L;

    @ApiModelProperty(position = 0)
    private final String token;

    @ApiModelProperty(position = 1)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    private final Date expiration;

    public AuthenticationResponse(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public AuthenticationResponse() {
        this("", new Date());
    }

    public String getToken() {
        return this.token;
    }

    public Date getExpiration() {
        return expiration;
    }
}