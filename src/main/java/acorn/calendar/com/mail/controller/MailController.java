package acorn.calendar.com.mail.controller;

import acorn.calendar.com.mail.service.MailService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.jfunc.json.Json;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping("/sendMailAuth.json")
    public void sendMailAuth(@RequestBody String json, HttpServletResponse response, HttpSession session) throws Exception {

        try{
            AcornMap acornMap = JsonUtils.toAcornMap(json);
            if(acornMap.getString("type").equals("J")){
                if(mailService.selectEmailCheck(acornMap) != 0){
                    ResponseUtils.responseMap(response, "-1", "중복된 이메일입니다.", null);
                    return;
                }
                String join = MailUtils.sendMail(acornMap);
                session.setAttribute(acornMap.getString("mEmail"),join);
                session.setMaxInactiveInterval(3*60);
                ResponseUtils.responseMap(response, "1", "인증 번호가 발송되었습니다.", null);
                return;
            }

            AcornMap seq = mailService.selectSeq(acornMap);
            if(null==seq){
                ResponseUtils.responseMap(response, "-1", "존재하지 않는 이메일입니다.", null);
                return;
            }
            acornMap.putAll(seq);
            String strCode = MailUtils.sendMail(acornMap);
            acornMap.put("hisAuth",strCode);
            mailService.insertMailAuthHistory(acornMap);
            ResponseUtils.responseMap(response, "1", "인증 번호가 발송되었습니다.", null);
        }catch (Exception e){
            e.printStackTrace();
            ResponseUtils.responseMap(response, "-1", "인증 번호 전송을 실패했습니다.", null);
        }
    }

    @RequestMapping("/confirmMailAuth.json")
    public void confirmMailAuth(@RequestBody String json, HttpServletResponse response, HttpSession session) throws Exception {
        //받을 값 : {mEmail, inputAuth, type(아디찾기 I, 비번찾기 P, 가입 J)}
        //join 이라면 인증번호 전송을 성공했을 때, 인증 완료 버튼이 보이게 해야 함. 그래야 자연스럽게 유효성 가능.

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            AcornMap acornMap = JsonUtils.toAcornMap(json);
            if("J".equals(acornMap.getString("type"))){
                String joinAuthCode = "";
                try{
                    joinAuthCode = session.getAttribute(acornMap.getString("mEmail")).toString();
                    String createJoinAuthCode = sdf.format(session.getCreationTime());
                    Date joinAuthCodeDate = sdf.parse(createJoinAuthCode);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(joinAuthCodeDate);
                    cal.add(Calendar.MINUTE,3);

                    // if(new Date().after(new Date(cal.getTimeInMillis()))){
                    //     ResponseUtils.responseMap(response,"-1","인증 번호가 만료됐습니다.",null);
                    //     return;
                    // }
                }catch (NullPointerException ne){
                    ne.printStackTrace();
                    ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg("auth.confirm.101"),null);
                    return;
                }
                if(joinAuthCode.equals(acornMap.getString("inputAuth"))){
                    ResponseUtils.responseMap(response,"1","인증 되었습니다.",null);
                    session.invalidate();
                }else{
                    ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg("auth.confirm.100"),null);
                }
                return;
            }
            AcornMap authMap = mailService.selectMailAuthHistory(acornMap);
            if(null != authMap){
                String authDate = authMap.getString("HIS_DATE");

                Date date = sdf.parse(authDate);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MINUTE,3);

                if(new Date().after(new Date(cal.getTimeInMillis()))){
                    ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg("auth.confirm.101"),null);
                }else{
                    if(authMap.getString("HIS_AUTH").equals(acornMap.getString("inputAuth"))){
                        if("I".equals(acornMap.getString("type"))){
                            ResponseUtils.responseMap(response,"1","",null, authMap.getString("M_ID"));
                        }else{
                            ResponseUtils.responseMap(response,"1","비밀번호를 재설정 해주세요.",null);
                        }
                    }else{
                        ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg("auth.confirm.100"),null);
                    }
                }
            }else{
                ResponseUtils.responseMap(response,"-1","인증 번호를 다시 전송 해주세요.",null);
            }
        }catch(Exception e){
            e.printStackTrace();
            ResponseUtils.responseMap(response,"-1","인증에 실패했습니다.",null);
        }
    }

//    private boolean certExpire(String authCreateDate) throws Exception {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = sdf.parse(authCreateDate);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.MINUTE,3);
//
//        if(new Date().after(new Date(cal.getTimeInMillis()))){
//            return false;
//        }
//        return true;
//    }

    @RequestMapping("/pwModify.json")
    public void pwModify(@RequestBody String json, HttpServletResponse response) throws Exception {

        AcornMap acornMap = JsonUtils.toAcornMap(json);
        String valCd = ValidateUtils.validate(acornMap,acornMap.getString("mPw"),"pwValid:true");
        if(!"".equals(valCd)){
            ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg(valCd),"");
            return;
        }
        if(!acornMap.getString("mPwChk").equals(acornMap.getString("mPw"))){
            ResponseUtils.responseMap(response,"-1",ValidateUtils.validMsg("join.check.password"),"");
            return;
        }
        acornMap.put("mPw", PasswordHashUtils.createHash(acornMap.get("mPw").toString()));
        mailService.updatePw(acornMap);
        ResponseUtils.responseMap(response,"1","패스워드가 변경되었습니다.","/");
    }

}
