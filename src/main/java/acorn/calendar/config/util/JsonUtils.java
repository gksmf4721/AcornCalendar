package acorn.calendar.config.util;

import acorn.calendar.config.data.AcornMap;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

        while (keys.hasNext()){
            String key = (String) keys.next();
            Object value = object.get(key);

            if (value instanceof AcornMap) {
                acornMap.put(key,toMap((JSONObject)value));
            }else{
                acornMap.put(key,value);
            }
        }
        return acornMap;
    }

    public static AcornMap toAcornMap(String jsonStr) throws JSONException {
        return toAcornMap(new JSONObject(jsonStr));
    }
}
