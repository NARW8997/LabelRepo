package com.example.demo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LabelWebsiteApplication {
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1.定义一个converters转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2.添加fastjson的配置信息，比如: 是否需要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 3.在converter中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4.将converter赋值给HttpMessageConverter
		HttpMessageConverter<?> converter = fastConverter;
		// 5.返回HttpMessageConverters对象
		return new HttpMessageConverters(converter);
	}

//	@Configuration
//	@EnableSpringHttpSession
//	public class HttpSessionConfig {
//	}

	@Configuration
	public class InterceptorConfig implements WebMvcConfigurer {

		@Override
		public void addInterceptors(InterceptorRegistry registry) {// 登录校验
			List<String> excludePaths = Arrays.asList("/dashboard/login", "/dashboard/error");
			registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/dashboard/**").excludePathPatterns(excludePaths);
		}

	}


	public static void main(String[] args) {
		SpringApplication.run(LabelWebsiteApplication.class, args);
	}

}
