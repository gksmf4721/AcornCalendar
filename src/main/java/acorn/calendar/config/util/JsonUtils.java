package acorn.calendar.config.util;

import acorn.calendar.config.data.AcornMap;
import acorn.calendar.config.model.LoginSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class JsonUtils {

    public static Map<String,Object> toMap(JSONObject object) throws JSONException {
        Map<String,Object> map = new HashMap<>();
        Iterator<?> keys = object.keys();

        while(keys.hasNext()){
            String key = (String) keys.next();
            Object value = object.get(key);

            if(value instanceof Map){
                map.put(key,toMap((JSONObject)value));
            }else{
                map.put(key,value);
            }
        }
        return map;
    }


    public static AcornMap toAcornMap(JSONObject object) throws JSONException {
        AcornMap acornMap = new AcornMap();
        Iterator<?> keys = object.keys();

        AcornMap loginMap = RequestUtils.getLoginSession(LoginSession.getLoginSession());

        while (keys.hasNext()){
            String key = (String) keys.next();
            Object value = object.get(key);

            if (value instanceof AcornMap) {
                acornMap.put(key,toMap((JSONObject)value));
            }else{
                acornMap.put(key,value);
            }
        }
        acornMap.putAll(loginMap);
        log.info("JsonUtils Data : "+acornMap);

        return acornMap;
    }

    public static AcornMap toAcornMap(String jsonStr) throws JSONException {
        return toAcornMap(new JSONObject(jsonStr));
    }
}
