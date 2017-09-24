package org.ua.oblik.domain.model;

import org.ua.oblik.domain.beans.Identifiable;

import java.io.Serializable;

/**
 *
 */
public interface UserLogin extends Identifiable<Integer>, Serializable {

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);

    String getPermissions();

    void setPermissions(String permissions);

    String getActivationUuid();

    void setActivationUuid(String activationUuid);
}
