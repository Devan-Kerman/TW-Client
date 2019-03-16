package server.api.area;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;

/**
 * A point, but only uses 2 bytes
 * @author devan
 *
 */
public class LocalPoint implements Assembable, Packetable{
	public byte x, y;
	public LocalPoint(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public LocalPoint() {
		/*For serialization*/
	}
	
	@Override
	public void pack(Packer p) {
		p.packV(x, y);
	}
	
	@Override
	public void from(ByteReader reader) {
		x = reader.read();
		y = reader.read();
	}
}
