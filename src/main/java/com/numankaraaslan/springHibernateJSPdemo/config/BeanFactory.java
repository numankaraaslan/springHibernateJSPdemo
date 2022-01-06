package com.numankaraaslan.springHibernateJSPdemo.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
// this is tx:annotation-driven
@EnableTransactionManagement
public class BeanFactory
{
	// All these can be defined in defaultservlet-servlet file
	// tx:annotation-driven and bean definitions ...etc.

	@Bean
	public ViewResolver viewResolver()
	{
		// If i return "wellcome" as modelandview object, the resolver will look for /jsp/wellcome.jsp inside the webapp folder
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	// For thymeleaf and HTML files this cold be:
	// @Bean
	// public ViewResolver viewResolver()
	// {
	// ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	// viewResolver.setTemplateEngine(templateEngine());
	// viewResolver.setViewNames(new String[]
	// { ".html", ".xhtml" });
	// return viewResolver;
	// }
	//
	// @Bean
	// public SpringTemplateEngine templateEngine()
	// {
	// SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	// templateEngine.setTemplateResolver(templateResolver());
	// templateEngine.setEnableSpringELCompiler(true);
	// return templateEngine;
	// }
	//
	// @Bean
	// public SpringResourceTemplateResolver templateResolver()
	// {
	// SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	// templateResolver.setApplicationContext(this.applicationContext);
	// templateResolver.setPrefix("/templates/");
	// templateResolver.setSuffix(".html");
	// templateResolver.setTemplateMode(TemplateMode.HTML);
	// templateResolver.setCacheable(true);
	// return templateResolver;
	// }

	@Bean(name = "datasource")
	@Profile(value = "default")
	public DataSource dataSource()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5433/postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("321321");
		return dataSource;
	}

	private final Properties hibernateProperties()
	{
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
		return hibernateProperties;
	}

	@Bean(name = "sessionFactory")
	@DependsOn(value = "datasource")
	public LocalSessionFactoryBean sessionFactory(@Autowired @Qualifier(value = "datasource") DataSource ds)
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(ds);
		// where are the model (@entity) classes
		sessionFactory.setPackagesToScan("com.numankaraaslan.springHibernateJSPdemo.model");
		sessionFactory.setHibernateProperties(hibernateProperties());
		// you can set configurations manually like
		// sessionFactory.getConfiguration().setProperty(propertyName, value);
		// OR from custom "/com/numankaraaslan/springjspdemo/persistence/hibernate.cfg.xml" like
		// SessionFactory SF = new org.hibernate.cfg.Configuration().configure("/com/numankaraaslan/springjspdemo/persistence/hibernate.cfg.xml").buildSessionFactory();
		return sessionFactory;
	}

	@Bean(name = "txManager")
	@DependsOn(value =
	{ "datasource", "sessionFactory" })
	public HibernateTransactionManager getManager(@Autowired @Qualifier(value = "datasource") DataSource ds, @Autowired @Qualifier(value = "sessionFactory") LocalSessionFactoryBean sf)
	{
		return new HibernateTransactionManager(sf.getObject());
	}
}