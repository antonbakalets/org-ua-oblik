package org.ua.oblik.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.ua.oblik.domain.model.*;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class InterfaceBasedJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;
    private final Map<Class<?>, Class<?>> interfaceToEntityClassMap;

    InterfaceBasedJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;

        interfaceToEntityClassMap = new HashMap<>();
        interfaceToEntityClassMap.put(Account.class, AccountEntity.class);
        interfaceToEntityClassMap.put(Currency.class, CurrencyEntity.class);
        interfaceToEntityClassMap.put(Txaction.class, TxactionEntity.class);
        interfaceToEntityClassMap.put(UserLogin.class, UserLoginEntity.class);
    }

    @Override
    public <T, I> JpaEntityInformation<T, I> getEntityInformation(Class<T> domainClass) {
        if (domainClass.isInterface()) {
            Class<T> domainInterface = domainClass;
            Class<?> entityClass = interfaceToEntityClassMap.get(domainInterface);
            return (JpaEntityInformation<T, I>) JpaEntityInformationSupport.getEntityInformation(entityClass, entityManager);
        } else {
            return (JpaEntityInformation<T, I>) JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
        }
    }

    @Override
    protected RepositoryMetadata getRepositoryMetadata(Class<?> repositoryInterface) {
        RepositoryMetadata ret = super.getRepositoryMetadata(repositoryInterface);
        Class<?> clazz = ret.getClass();
        try {
            Field f = clazz.getDeclaredField("domainType");
            boolean isAccessible = f.isAccessible();
            f.setAccessible(true);
            Class<?> actualValue = (Class<?>) f.get(ret);
            Class<?> newValue = this.interfaceToEntityClassMap.get(actualValue);
            f.set(ret, newValue);
            f.setAccessible(isAccessible);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
