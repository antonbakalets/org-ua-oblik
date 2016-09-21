package org.ua.oblik.domain.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ua.oblik.domain.model.UserLogin;
import org.ua.oblik.domain.model.UserLoginEntity;

/**
 *
 * @author Anton Bakalets
 */
public class UserLoginDaoImpl extends AbstractDao<Integer, UserLogin, UserLoginEntity> implements UserLoginDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginDaoImpl.class);
    
    public UserLoginDaoImpl() {
        super(UserLoginEntity.class);
    }

    @Override
    public Optional<UserLogin> loadUserLogin(String username) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserLoginEntity> cq = cb.createQuery(UserLoginEntity.class);
        Root<UserLoginEntity> root = cq.from(UserLoginEntity.class);
        cq.select(root).where(cb.equal(cb.lower(root.<String>get("username")), username.toLowerCase()));
        UserLoginEntity loginEntity = null;
        try {
            loginEntity = getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            final String message = "Cannot find user " + username + ".";
            LOGGER.error(message);
        }
        return Optional.ofNullable(loginEntity);
    }
}
