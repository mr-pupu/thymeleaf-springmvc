package com.github.luksrn.conf;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nz.net.ultraq.web.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(
		basePackages={"com.github.luksrn"},
		excludeFilters= { 
				@ComponentScan.Filter( Configuration.class ) 
				} 
		) 
@ImportResource({
	"classpath*:/applicationContext-security.xml"
})
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

	
	@Bean
	public ServletContextTemplateResolver templateResolver(){
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setOrder(0);
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(){
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());		
		templateEngine.setAdditionalDialects( additionalDialects() );
		return templateEngine;
	}
	
	private Set<IDialect> additionalDialects() {
		Set<IDialect> dialects = new HashSet<IDialect>();
		dialects.add( new SpringSecurityDialect() );
		dialects.add( new LayoutDialect() );
		return dialects;
	}

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver(){
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}
 
	 @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 registry.addResourceHandler("/resources/**").addResourceLocations(
				 	"/resources/", 
				 	"/",
	                "classpath:/META-INF/resources/");
	}
	 

	 /**
	  * The PageableArgumentResolver will automatically resolve request parameters to build a PageRequest 
	  * instance. By default it will expect the following structure for the request parameters:

			Table 4.1. Request parameters evaluated by PageableArgumentResolver
			page	The page you want to retrieve
			page.size	The size of the page you want to retrieve
			page.sort	The property that should be sorted by
			page.sort.dir	The direction that should be used for sorting

	  * @return
	  */
	 @Override
	 public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

		 PageableArgumentResolver resolver = new PageableArgumentResolver();
		 resolver.setFallbackPagable(new PageRequest(1, 10));

		 argumentResolvers.add(new ServletWebArgumentResolverAdapter(resolver));
	 }

}
