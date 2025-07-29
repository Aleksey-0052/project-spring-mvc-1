package com.javarush.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.javarush")
@PropertySource(value = "classpath:application.properties")
public class AppConfig {

    @Value("${spring.datasource.defaultUrl}")
    private String defaultUrl;

    @Value("${spring.datasource.dockerUrl}")
    private String dockerUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private String maxPoolSize;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String dialect;

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Value("${spring.liquibase.change-log}")
    private String changeLog;


    @Bean
    @Profile("!docker")
    public DataSource localDataSource() {
        return createDataSource(defaultUrl);

        // @Primary - в данном случае локальный бин используется по умолчанию без аннотации @Primary
    }

    @Bean
    @Profile("docker")
    public DataSource dockerDataSource() {
        return createDataSource(dockerUrl);
    }


    private DataSource createDataSource(String url) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        return dataSource;
    }


    @Bean(name = "entityManagerFactory")
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.javarush.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;

        // @Bean(name = "entityManagerFactory") - без указания имени бина Spring не мог найти в своем контексте этот бин
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, dialect);
        properties.put(Environment.HBM2DDL_AUTO, ddlAuto);
        properties.put(Environment.SHOW_SQL, showSql);
        return properties;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }



    // SpringBoot запускает liquibase без данного бина. Но в данном случае этот бин нужен для того, чтобы использовать
    // liquibase для инициализации базы данных как при старте приложения через IDEA, так и запуске приложения через docker

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        return liquibase;
    }

}
