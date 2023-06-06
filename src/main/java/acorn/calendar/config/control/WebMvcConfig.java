package acorn.calendar.config.control;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import acorn.calendar.config.interceptor.AuthenticInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticInterceptor())
			.addPathPatterns("/index")
			.addPathPatterns("/*")
			.addPathPatterns("/**/*")
			.excludePathPatterns("/test.json")
			.excludePathPatterns("/test");
	}
	
}
