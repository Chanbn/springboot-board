package com.board.configruation;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfiguration implements WebMvcConfigurer {

	@Override
public void addInterceptors(InterceptorRegistry registry) {
	// TODO Auto-generated method stub
	WebMvcConfigurer.super.addInterceptors(registry);
}
	@Bean
	public CommonsMultipartResolver MultipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSizePerFile(5*1024*1024);
		return multipartResolver;
	}
}
