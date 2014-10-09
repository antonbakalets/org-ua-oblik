package org.ua.oblik.domain.dao;

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
public class UserLoginDaoImpl extends AbstractDao<Integer, UserLoginEntity, UserLogin> implements UserLoginDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginDaoImpl.class);
    
    public UserLoginDaoImpl() {
        super(UserLogin.class);
    }

    @Override
    public UserLoginEntity loadUserLogin(String username) throws UserNotFoundException {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserLogin> cq = cb.createQuery(UserLogin.class);
        Root<UserLogin> root = cq.from(UserLogin.class);
        cq.select(root).where(cb.equal(cb.lower(root.<String>get("username")), username.toLowerCase()));
        try {
            return getEntityManager().createQuery(cq).getSingleResult();
        } catch (NoResultException nre) {
            final String message = "Cannot find user " + username + ".";
            LOGGER.error(message);
            throw new UserNotFoundException(message, nre);
        }
    }
}
