package acorn.calendar.config.firebase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.rpc.context.AttributeContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {

//    private String API_URL = "https://fcm.googleapis.com/v1/projects/Acorn/messages:send";
//    private final ObjectMapper objectMapper;
//
//    private String getAccessToken() throws Exception {
//        String firebaseConfigPath = "acorn-c1983-firebase-adminsdk-qb3z7-3300b87d40.json";
//        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath)
//                .getInputStream()).createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
//        googleCredentials.refreshIfExpired();
//        return googleCredentials.getAccessToken().getTokenValue();
//    }
//
//    public void sendMessageTo(String targetToken, String title, String body) throws Exception {
//        String message = makeMessage(targetToken, title, body);
//
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//        Request request = new AttributeContext.Request.Builder()
//                .url(API_URL)
//                .post(requestBody)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        log.info(response.body().string());
//    }
//
//    private String makeMessage(String targetToken, String title, String body) throws Exception {
//        FCMMessage fcmMessage = FCMMessage.builder()
//                .message(FCMMessage.Message.builder()
//                        .token(targetToken)
//                        .notification(FCMMessage.Notification.builder()
//                                .title(title)
//                                .body(body)
//                                .image(null)
//                                .build()
//                        )
//                        .build()
//                )
//                .validate_only(false)
//                .build();
//        return objectMapper.writeValueAsString(fcmMessage);
//    }
//    }

}
