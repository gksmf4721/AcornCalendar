package acorn.calendar.config.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class AcornMap extends HashMap<Object,Object>{
	
	private static final long serialVersionUID = 129312978401293812L;
	
	public AcornMap() {super();}
	
	public AcornMap(Map arg) {
		super();
	}
	//public Map<String, Object> getMap() { super(); }
	
	public Object get(String key) { return super.get(key); }
	
	public String getString(String key) {return getString(key,"");}
	
	public String getString(String key, String nullValue) {
		Object value = (Object)this.get(key);
		try {
			if("".equals(value.toString())) {
				return nullValue;
			}
			return value.toString();
		}catch(Exception e) {
			return nullValue;
		}
	}
	
	public void put(String key, Object value) { super.put(key, value); }
	
	//public void putAll(Map<? extends String, ? extends Object> m) { map.putAll(m); }
	
	public Object remove(String key) { return super.remove(key); }
	
	public boolean containsKey(String key) { return super.containsKey(key); }
	
	public boolean containsValue(Object value) { return super.containsValue(value); }
	
	public void clear() { super.clear(); }
	
	//public Set<Entry<String, Object>> entrySet() { return map.entrySet(); }
	
	//public Set<String> keySet() { return map.keySet(); }
	
	public boolean isEmpty() { return super.isEmpty(); }
	
	public void setMap(String mo_datail) {}
	
	
}
