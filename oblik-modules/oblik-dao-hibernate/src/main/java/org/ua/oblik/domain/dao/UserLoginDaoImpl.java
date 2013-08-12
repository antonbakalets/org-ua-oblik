package org.ua.oblik.domain.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.ua.oblik.domain.model.UserLogin;

/**
 *
 * @author Anton Bakalets
 */
public class UserLoginDaoImpl extends AbstractDao<Integer, UserLogin> implements UserLoginDao {

    public UserLoginDaoImpl() {
        super(UserLogin.class);
    }

    @Override
    public UserLogin loadUserLogin(String username) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserLogin> cq = cb.createQuery(UserLogin.class);
        Root<UserLogin> root = cq.from(UserLogin.class);
        cq.select(root).where(cb.equal(cb.lower(root.<String>get("username")), username.toLowerCase()));
        return entityManager.createQuery(cq).getSingleResult();
    }
}
