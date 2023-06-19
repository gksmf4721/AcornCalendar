package acorn.calendar.config.util;

import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class MailUtils{

    @Autowired
    private JavaMailSender postSender;

    private static JavaMailSender sender;

    @Autowired
    private SpringTemplateEngine template;

    private static SpringTemplateEngine templateEngine;

    @PostConstruct
    private void initStt(){templateEngine = this.template;}

    @PostConstruct
    private void initStatic(){
        sender = this.postSender;
    }

    public static String sendMail(AcornMap acornMap) throws Exception {

        String authCode = RandomStringUtils.randomAlphabetic(6);

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper messageHelper  = new MimeMessageHelper(message,true, "UTF-8");
        if(acornMap.getString("type").equals("I")){
            messageHelper.setSubject("[AcornCalendar] 아이디 인증 번호입니다.");
        }else if(acornMap.getString("type").equals("P")){
            messageHelper.setSubject("[AcornCalendar] 비밀번호 인증 번호입니다.");
        }else{
            messageHelper.setSubject("[AcornCalendar] 회원가입 인증 번호입니다.");
        }
        messageHelper.setFrom("acorn_calendar@naver.com");
        messageHelper.setTo(acornMap.getString("mEmail").toString());
        messageHelper.setText(createHtml(authCode),true);
        sender.send(message);

        return authCode;
    }

    private static String createHtml(String authCode){
        Context context = new Context();
        context.setVariable("code",authCode);
        return templateEngine.process("mail/sendAuth",context);
    }

}
