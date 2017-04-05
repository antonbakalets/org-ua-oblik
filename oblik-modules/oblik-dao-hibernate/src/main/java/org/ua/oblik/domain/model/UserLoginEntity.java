package org.ua.oblik.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author zita
 */
@Entity
@Table(name = "user_login")
public class UserLoginEntity implements Serializable, UserLogin {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "login_id")
    private Integer loginId;

    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @Column(name = "permit")
    private String permissions;

    @Column(name = "activation_uuid")
    private String activationUuid;

    @Override
    public Integer getId() {
        return loginId;
    }

    @Override
    public void setId(Integer id) {
        this.loginId = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getActivationUuid() {
        return activationUuid;
    }

    @Override
    public void setActivationUuid(String activationUuid) {
        this.activationUuid = activationUuid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserLogin)) {
            return false;
        }
        UserLoginEntity other = (UserLoginEntity) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserLoginEntity[ loginId=" + loginId + " ]";
    }
}
