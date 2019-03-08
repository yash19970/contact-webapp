/**
 *
 */
package com.contact.contactwebapp.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * @author ansharpasha
 *
 */
@Configuration
@ComponentScan
public class DataSourceConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;
	@Value("${spring.jpa.properties.hibernate.query.plan_cache_max_size}")
	private Integer cacheQueryPlanSize;
	@Value("${spring.jpa.properties.hibernate.format_sql}")
	private String fmtSql;
	@Value("${spring.jpa.show-sql}")
	private String showSql;
	@Value("${spring.jpa.database-platform}")
	private String dialect;

	private Map<String, Object> jpaProperties() {
		Map<String, Object> jpaProperties = new HashMap<>();
		jpaProperties.put("hibernate.dialect", dialect);
		jpaProperties.put("hibernate.show_sql", showSql);
		jpaProperties.put("hibernate.format_sql", fmtSql);
		jpaProperties.put("hibernate.hbm2ddl.auto", ddlAuto);
		jpaProperties.put("hibernate.query.plan_cache_max_size", cacheQueryPlanSize);
		return jpaProperties;
	}

	@Primary
	@Bean("masterDataSourceProperties")
	@ConfigurationProperties("master.spring.datasource")
	public DataSourceProperties masterDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean(name = "masterDataSourcePersonal")
	@ConfigurationProperties(prefix = "master.spring.datasource")
	public DataSource masterDataSourceContactApp(@Qualifier("masterDataSourceProperties") DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Primary
	@Bean(name = "masterEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(final EntityManagerFactoryBuilder builder,
			@Qualifier("masterDataSourcePersonal") DataSource dataSource) {
		return builder.dataSource(dataSource).properties(jpaProperties()).packages("com.contact.contactwebapp").persistenceUnit("masterContactwebapp")
				.build();
	}

	@Bean("slaveDataSourceProperties")
	@ConfigurationProperties("slave.spring.datasource")
	public DataSourceProperties slaveDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "slaveDataSourcePersonal")
	@ConfigurationProperties(prefix = "slave.spring.datasource")
	public DataSource slaveDataSourceContactApp(@Qualifier("slaveDataSourceProperties") DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactory(final EntityManagerFactoryBuilder builder,
			@Qualifier("slaveDataSourcePersonal") DataSource dataSource) {
		return builder.dataSource(dataSource).properties(jpaProperties()).packages("com.contact.contactwebapp").persistenceUnit("slaveContactwebapp")
				.build();
	}

}
