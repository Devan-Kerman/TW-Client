package serverclasses;

import java.io.Serializable;
import java.util.HashMap;

public class Packet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8064403805841699770L;
	public String request;
	public HashMap<String, Object> data;
}
