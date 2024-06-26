package acorn.calendar.config.util;

//import java.util.Locale;

//import javax.annotation.PostConstruct;

//import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class MessageUtils {

    @Autowired
	public static MessageSourceAccessor messageSource;

    @Autowired
	public static MessageSourceAccessor messageSourceAccessor;

	@Autowired
	public static MessageSource messageSrc;

	//의존성 주입 후 초기화. 클래스 내 Autowired를 붙여서 객체를 사용할 때,
	//생성자가 필요하다면 PostConstruct를 사용하면 될 듯. (bean 등록되고 사용 가능)
	@PostConstruct
	private void initMessageSource() {
		messageSource = this.messageSourceAccessor;
	}

	public static String getMessage(String code) {
		return messageSource.getMessage(code, Locale.getDefault());
	}

	public static String getMessage(String code, String desc) {
		return messageSource.getMessage(code, Locale.getDefault());
	}
}
