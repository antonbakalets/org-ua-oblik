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
import org.ua.oblik.domain.model.UserLoginEntity_;

/**
 * User Login DAO.
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
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserLoginEntity> cq = cb.createQuery(UserLoginEntity.class);
        Root<UserLoginEntity> root = cq.from(UserLoginEntity.class);
        cq.select(root).where(cb.equal(cb.lower(root.get(UserLoginEntity_.username)), username.toLowerCase()));
        Optional<UserLogin> userLoginOptional;
        try {
            userLoginOptional = Optional.of(getEntityManager().createQuery(cq).getSingleResult());
        } catch (NoResultException nre) {
            userLoginOptional = Optional.empty();
            LOGGER.trace("Could not find user by username.", nre);
            LOGGER.warn("Could not find user by username '{}'.", username);
        }
        return userLoginOptional;
    }
}
