package server;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Registry<T> {
	private Map<Integer, Class<? extends T>> registry;
	
	public Registry() {
		registry = new HashMap<>();
	}
	
	public void register(int id, Class<? extends T> t) {
		registry.put(id, t);
	}
	
	public T getInstance(int id) {
		try {
			return registry.get(id).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void forEach(BiConsumer<Integer, Class<? extends T>> registryWalker) {
		registry.forEach(registryWalker);
	}

	
}
