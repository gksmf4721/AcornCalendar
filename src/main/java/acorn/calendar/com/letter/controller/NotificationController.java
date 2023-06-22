package acorn.calendar.com.letter.controller;

import acorn.calendar.com.letter.service.LetterService;
import acorn.calendar.com.letter.service.NotificationService;
import acorn.calendar.config.data.AcornMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class NotificationController {

    @Autowired
    private LetterService letterService;

    private static final Map<String,SseEmitter> CLIENTS = new ConcurrentHashMap<>();

    @GetMapping("/api/subscribe")
    public SseEmitter subscribe(String id){
        SseEmitter emitter = new SseEmitter(1000L * 60L * 15L);
        CLIENTS.put(id,emitter);
        emitter.onTimeout(() -> CLIENTS.remove(id));
        emitter.onCompletion(() -> CLIENTS.remove(id));
        return emitter;
    }

    @GetMapping("/api/publish")
    public void publish(AcornMap acornMap) throws Exception {

        String msg = "[메일]" + acornMap.getString("session_m_nickname") + " 님이 메일을 전송했습니다.";
        String message = URLEncoder.encode(msg,"utf-8");

        //message = URLEncoder.encode(msg,"utf-8");

        String userId = letterService.selectId(acornMap);

        Set<String> deadIds = new HashSet<>();
        CLIENTS.forEach((id,emitter) -> {
            if(id.equals(userId)){
                try{
                    log.info("메세지 : "+message);
//                    emitter.send(message, MediaType.APPLICATION_JSON);
                    emitter.send(message, MediaType.APPLICATION_JSON);
                }catch(Exception e){
                    e.printStackTrace();
                    deadIds.add(id);
                    log.warn("disconnected id : {}",id);
                }
            }
        });
        deadIds.forEach(CLIENTS::remove);
    }


}
