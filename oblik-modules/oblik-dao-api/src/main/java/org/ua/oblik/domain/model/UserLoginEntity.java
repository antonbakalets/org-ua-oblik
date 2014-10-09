package org.ua.oblik.domain.model;

import org.ua.oblik.domain.beans.Identifiable;

/**
 *
 */
public interface UserLoginEntity extends Identifiable<Integer> {

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
