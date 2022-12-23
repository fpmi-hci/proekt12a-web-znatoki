package com.bsu.znatoki.config.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DbInitializer {
    @Autowired
    public void loadData(DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator =
                new ResourceDatabasePopulator(false, false,
                        "UTF-8", new ClassPathResource("sql_scripts/init_tables.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }
}
