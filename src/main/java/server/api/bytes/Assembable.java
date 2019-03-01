package server.api.bytes;

/**
 * An object that can be "assembled" from a raw sequence of bytes
 * @author devan
 *
 */
public interface Assembable {
	
	/**
	 * "Initialize" this object given the following data
	 * @param src
	 * 		the sequence of bytes
	 * @param start
	 * 		where in that sequence to start from
	 * @return
	 * 		the number of bytes used
	 */
	public void from(ByteReader reader);
	
}
