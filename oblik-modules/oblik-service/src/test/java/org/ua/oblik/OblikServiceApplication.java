package org.ua.oblik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.ua.oblik.domain.dao.InterfaceBasedJpaRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = InterfaceBasedJpaRepositoryFactoryBean.class)
public class OblikServiceApplication {
    /**
     * Service module main method for SpringBoot test.
     */
    public static void main(String[] args) {
        SpringApplication.run(OblikServiceApplication.class, args);
    }
}
