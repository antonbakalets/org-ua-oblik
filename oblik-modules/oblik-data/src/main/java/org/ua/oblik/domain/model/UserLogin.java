package org.ua.oblik.domain.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User login entity.
 */
@Entity
@Table(name = "user_login")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserLogin implements Serializable {

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
}
