package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import java.util.Locale;


@SpringBootApplication
public class Examspring01Application implements WebMvcConfigurer{
	private final LocaleChangeInterceptor localeChangeInterceptor;
	
	public Examspring01Application(LocaleChangeInterceptor localeChangeInterceptor) {
	    this.localeChangeInterceptor = localeChangeInterceptor;
	  }
	
  @Override
  public void addInterceptors(InterceptorRegistry interceptorRegistry) {
    interceptorRegistry.addInterceptor(localeChangeInterceptor);
  }
  
	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasenames("lang/messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    System.out.println(messageSource.getMessage("hello", null, Locale.ENGLISH));
		
		SpringApplication.run(Examspring01Application.class, args);
	}
	

}
