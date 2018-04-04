package org.ua.oblik.rest.v1.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * POJO for authentication requests
 */

public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -1843461789521246620L;

    @ApiModelProperty(value = "Account UserName", required = true, position = 0)
    private String username;

    @ApiModelProperty(value = "Account password", required = true, position = 1)
    private String password;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
