package com.bsu.znatoki.config.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:db.properties"})
@ComponentScan({"com.leverx.blog"})
@EnableJpaRepositories(basePackages = "com.leverx.blog.repository")
public class JpaConfig {
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_USE_SEC_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String HIBERNATE_USE_QUERY_CACHE = "hibernate.cache.use_query_cache";

    @Value("${jdbc.driverClassName}")
    private String jdbcDriverClassname;
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.username}")
    private String jdbcUsername;
    @Value("${jdbc.password}")
    private String jdbcPassword;
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.cache.use_second_level_cache}")
    private String hibernateSecLevelCache;
    @Value("${hibernate.cache.use_query_cache}")
    private String hibernateUseQueryCache;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.leverx.blog.entity");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(jdbcDriverClassname));
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(HIBERNATE_DIALECT, hibernateDialect);
        hibernateProperties.setProperty(HIBERNATE_USE_SEC_CACHE, hibernateSecLevelCache);
        hibernateProperties.setProperty(HIBERNATE_USE_QUERY_CACHE, hibernateUseQueryCache);
        return hibernateProperties;
    }
}