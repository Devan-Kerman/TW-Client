package server.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaxMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 200779303091620632L;
	private final int maxSize;

    public MaxMap(int maxSize) {
        this.maxSize = maxSize;
    }


	@Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
	
	public int getMaxSize() {
		return maxSize;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof MaxMap<?, ?>)
			return false;
		MaxMap<K, V> m = (MaxMap<K, V>)arg0;
		return super.equals(m) && m.getMaxSize() == maxSize;
	}
}
