package acorn.calendar.config.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 페이징을 위한 커스텀 어노테이션.
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandMap {
	
	boolean isPage() default false;
	
	int pageSize() default 0;
	
	int accessGrade() default 0;
	
	int[] customPageSize() default {10,20};
}
