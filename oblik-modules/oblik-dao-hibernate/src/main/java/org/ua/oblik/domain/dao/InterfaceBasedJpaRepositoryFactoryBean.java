package org.ua.oblik.domain.dao;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

public class InterfaceBasedJpaRepositoryFactoryBean<T extends Repository<S, I>, S, I>
        extends JpaRepositoryFactoryBean<T, S, I> {

    public InterfaceBasedJpaRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new InterfaceBasedJpaRepositoryFactory(entityManager);
    }
}
