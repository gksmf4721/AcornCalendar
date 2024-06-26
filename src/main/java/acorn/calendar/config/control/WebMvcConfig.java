package acorn.calendar.config.control;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import acorn.calendar.config.interceptor.AuthenticInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticInterceptor())
				.addPathPatterns("/index")
				.addPathPatterns("/*")
				.addPathPatterns("/**/*")
				.excludePathPatterns("/css/**")
				.excludePathPatterns("/img/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/json/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);

		registry.addResourceHandler("/css/**")
				.addResourceLocations("classpath:/static/css/");

		registry.addResourceHandler("/img/**")
				.addResourceLocations("classpath:/static/img/");

		registry.addResourceHandler("/js/**")
				.addResourceLocations("classpath:/static/js/");

		registry.addResourceHandler("/json/**")
				.addResourceLocations("classpath:/static/json/");

	}
}
