package com.xzs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration  
@ComponentScan(basePackages={"com.xzs"})  
@SpringBootApplication 
public class RedisDemoApplication extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
	   @Override    
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {    
	        return application.sources(RedisDemoApplication.class);    
	    }    
	  
	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {  
    //  configurableEmbeddedServletContainer.setPort(9090);  
    }  
}
