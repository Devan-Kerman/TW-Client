package server.nation;

import java.awt.Point;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;

/**
 * An instance of this class contains all the nessesary data to find a point within the game
 * it consists of 4 values, or 1 point (int x, int y) which represents what chunk this point is in
 * and 2 bytes, which represent the relitive coordinate of the tile, to the "0, 0" of the chunk
 * @author devan
 *
 */
public class TilePoint implements Assembable, Packetable {
	public Point chunk;
	public byte tx;
	public byte ty;
	/**
	 * This holds all the values nessesarry to find a tile within the game
	 * @param cx
	 * 		The tile's chunk coordinate x
	 * @param cy
	 * 		The tile's chunk coordinate y
	 * @param tx
	 * 		The tile's coordinate relitive to the 0 (x) of the chunk it's in
	 * @param ty
	 * 		The tile's coordinate relitive to the 0 (y) of the chunk it's in
	 */
	public TilePoint(int cx, int cy, byte tx, byte ty) {
		super();
		chunk = new Point(cx, cy);
		this.tx = tx;
		this.ty = ty;
	}
	/**
	 * This holds all the values nessesarry to find a tile within the game
	 * @param p
	 * 		The tile's chunk coordinate
	 * @param tx
	 * 		The tile's coordinate relitive to the 0 (x) of the chunk it's in
	 * @param ty
	 * 		The tile's coordinate relitive to the 0 (y) of the chunk it's in
	 */
	public TilePoint(Point p, byte tx, byte ty) {
		super();
		chunk = p;
		this.tx = tx;
		this.ty = ty;
	}
	
	/**
	 * Used for serialization, defaults to 0, 0 -> 0, 0
	 */
	public TilePoint() {
		this(new Point(0,0), (byte)0, (byte)0);
	}
	
	/**
	 * String.format("[%d, %d] -> [%d, %d]", chunk.x, chunk.y, tx, ty)
	 */
	@Override
	public String toString() {
		return String.format("[%d, %d] -> [%d, %d]", chunk.x, chunk.y, tx, ty);
	}
	
	@Override
	public void pack(Packer p) {
		p.packPoint(chunk);
		p.packV(tx, ty);
	}
	@Override
	public void from(ByteReader reader) {
		chunk = reader.readPoint();
		tx = reader.read();
		ty = reader.read();
	}
	
	public static Point diff(TilePoint a, TilePoint b) {
		return new Point((a.chunk.x-b.chunk.x)*100+(a.tx-b.tx), (a.chunk.y-b.chunk.y)*100+(b.tx-b.ty));
	}

}
