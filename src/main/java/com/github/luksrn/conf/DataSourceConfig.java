package com.github.luksrn.conf;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
 
@Configuration
public class DataSourceConfig {

	private final Logger LOG = LoggerFactory.getLogger(DataSourceConfig.class);
	
	@Bean(destroyMethod="shutdown")
	public  DataSource dataSource()  {
		
		LOG.debug("Iniciando construção do EmbeddedDatabase H2...");		
		EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();		
		factory.setDatabaseType(EmbeddedDatabaseType.H2);
		factory.setDatabasePopulator( databasePopulator() );
		DataSource ds = factory.getDatabase();		
		return ds;		
	}
	
	private DatabasePopulator databasePopulator() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("test-script.sql"));
		return populator;
	}
}
