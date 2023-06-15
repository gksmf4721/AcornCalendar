package acorn.calendar.com.mail.controller;

import acorn.calendar.com.mail.service.MailService;
import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.util.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.jfunc.json.Json;

import javax.servlet.http.HttpServletResponse;
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
    public void sendMailAuth(@RequestBody String json, HttpServletResponse response) throws Exception {

        try{
            AcornMap acornMap = JsonUtils.toAcornMap(json);
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
    public void confirmMailAuth(@RequestBody String json, HttpServletResponse response) throws Exception {
        //받을 값 : {mEmail, inputAuth, type(아디찾기 I, 비번찾기 P)}
        AcornMap acornMap = JsonUtils.toAcornMap(json);

        try{
            AcornMap authMap = mailService.selectMailAuthHistory(acornMap);

            if(null != authMap){

                String authDate = authMap.getString("HIS_DATE");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = sdf.parse(authDate);

                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MINUTE,3);

                if(new Date().after(new Date(cal.getTimeInMillis()))){
                    ResponseUtils.responseMap(response,"-1","인증 번호가 만료됐습니다.",null);
                }else{
                    if(authMap.getString("HIS_AUTH").equals(acornMap.getString("inputAuth"))){
                        if("I".equals(acornMap.getString("type"))){
                            ResponseUtils.responseMap(response,"1","",null, authMap.getString("M_ID"));
                        }else{
                            ResponseUtils.responseMap(response,"1","비밀번호를 재설정 해주세요.",null);
                        }
                    }else{
                        ResponseUtils.responseMap(response,"-1","인증 번호가 일치하지 않습니다.",null);
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
