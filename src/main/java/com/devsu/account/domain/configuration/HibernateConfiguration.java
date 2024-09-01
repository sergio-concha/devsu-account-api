package com.devsu.account.domain.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
	
	private static final Logger LOG = LogManager.getLogger(HibernateConfiguration.class);

	//@Autowired
    private Environment env;

//	@Value("${spring.profiles.active}")
//	private String activeProfile;

	// Constructor
	public HibernateConfiguration(Environment env) {
		this.env = env;
	}

    @Bean(name = "dataSource")
    DataSource getDataSource() {
    	//LOG.info("Currently active profile - {}", activeProfile);
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();

    	if(env != null) {
    		// See: application.properties
    		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
    		dataSource.setUrl(env.getProperty("spring.datasource.url"));
    		dataSource.setUsername(env.getProperty("spring.datasource.username"));
    		dataSource.setPassword(env.getProperty("spring.datasource.password"));
    		LOG.info("Database: {}", env.getProperty("app.message"));
    	}
 
 
        return dataSource;
    }
    
    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {

      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setGenerateDdl(true);

      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setJpaVendorAdapter(vendorAdapter);
      factory.setPackagesToScan("com.devsu.account.domain.dto");
      factory.setDataSource(getDataSource());
      return factory;
    }


    /**
     * <h2>Link:</h2>
     * http://acodigo.blogspot.com/2017/03/spring-data-jpa-acceso-datos-simple-y.html 
     */
    @Bean
    PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
