package com.example.scheduler.processor.config;

import com.example.scheduler.processor.conzt.DBConnection;
import com.example.scheduler.processor.conzt.StartUpConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = DBConnection.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = DBConnection.TRANSACTION_MANAGER_FACTORY_BEAN)
@PropertySource({"classpath:db/mysql-config-${spring.profiles.active}.properties"})
public class DatabaseConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url(environment.getProperty(DBConnection.URL_PROPERTY))
                .username(environment.getProperty(DBConnection.USER_NAME_PROPERTY))
                .password(environment.getProperty(DBConnection.PASSWORD_PROPERTY))
                .driverClassName(environment.getProperty(DBConnection.DRIVE_CLASS_PROPERTY))
                .build();
    }

    @Bean(name = DBConnection.ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(StartUpConfiguration.DBConfig.HIBERNATE_INITIAL_OPERATION, StartUpConfiguration.DBConfig.HIBERNATE_INITIAL_OPERATION_ACTION);

        return entityManagerFactoryBuilder
                .dataSource(getDataSource())
                .packages(StartUpConfiguration.DBConfig.BASE_ENTITY_PACKAGE)
                .persistenceUnit(StartUpConfiguration.DBConfig.HIBERNATE_PERSISTANCE_UNIT)
                .properties(properties)
                .build();
    }

    @Bean(name = DBConnection.TRANSACTION_MANAGER_FACTORY_BEAN)
    public PlatformTransactionManager getPlatformTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(getDataSource());
        jpaTransactionManager.setPersistenceUnitName(StartUpConfiguration.DBConfig.HIBERNATE_PERSISTANCE_UNIT);
        return jpaTransactionManager;
    }

}
