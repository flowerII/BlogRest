package com.qianshu.blogrest;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Controller
@SpringBootApplication
@ComponentScan(basePackages={"com.qianshu.blogrest.service","com.qianshu.blogrest.controller"})
public class BlogRestApplication {

	public static void main(String[] args) {
		System.setProperty("spring.jpa.hibernate.ddl-auto", "update");
		SpringApplication.run(BlogRestApplication.class, args);
	}
	
	/**
	 * 配置数据源
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource=new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		
		/*dataSource.setUrl("jdbc:mysql://172.17.0.2:3306/db_blog_v1?useSSL=false");
		dataSource.setPassword("888888");*/
		dataSource.setUrl("jdbc:mysql://localhost:3306/db_blog_v1?useSSL=false");
		dataSource.setPassword("123456");
		
		return dataSource;
	}	
	
	/**
	 * 配置拦截器
	 */
	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurerAdapter() {
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/error/**")
				.excludePathPatterns("/user/init").excludePathPatterns("/user/auth").excludePathPatterns("/")
				.excludePathPatterns("/email/code").excludePathPatterns("/user/registry").excludePathPatterns("/user/repassword");
			}
			
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
				registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
				super.addResourceHandlers(registry);
			}
			
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("index");
				super.addViewControllers(registry);
			}
		};
	}
	
	/**
	 * 身份验证拦截器
	 */
	@Bean
	public AuthInterceptor getAuthInterceptor() {
		return new AuthInterceptor();
	}
	
    /*@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(80);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8080);
        return connector;
    }*/
	
}
