package org.ua.oblik.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class InterfaceBasedJpaRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager entityManager;
    private final Map<? extends Class<?>, ? extends Class<?>> interfaceToEntityClassMap;

    InterfaceBasedJpaRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;

        interfaceToEntityClassMap = entityManager
                .getMetamodel()
                .getEntities()
                .stream()
                .flatMap(et -> Arrays.stream(et.getJavaType().getInterfaces())
                        .map(it -> new AbstractMap.SimpleImmutableEntry<>(it, et.getJavaType())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (possibleDuplicateInterface, v) -> v));
    }

    @Override
    public <T, I> JpaEntityInformation<T, I> getEntityInformation(Class<T> domainClass) {
        if (domainClass.isInterface()) {
            Assert.isTrue(domainClass.isInterface(), "You are using interface based jpa repository support. " +
                    "The entity type used in DAO should be an interface");

            Class<T> domainInterface = domainClass;

            Class<?> entityClass = interfaceToEntityClassMap.get(domainInterface);

            Assert.notNull(entityClass, "Entity class for a interface" + domainInterface + " not found!");

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
