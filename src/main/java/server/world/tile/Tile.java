package server.world.tile;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;


/**
 * This is 1 unit of space on the map, it represents roughly ~2.2 acres of space
 * @author devan
 *
 */
public class Tile implements Packetable, Assembable {
	
	/**
	 * Number of bytes this object takes to serialize
	 */
	public static final int MASS = 13;
	
	/**
	 * Nation that has claimed it
	 */
	public int ownerid;
	
	/**
	 * The height of that tile's terrain
	 */
	public byte elevation; // 'Z' (Elevation)
	
	/**
	 * Iron, Nickle, and Titanium
	 */
	public byte ironlike;
	
	/**
	 * Aluminum
	 */
	public byte bauxite;
	/**
	 * The amount of tin ore in the tile
	 */
	public byte tinlike;
	/**
	 * The amount of copper ore in the tile
	 */
	public byte copperlike;
	/**
	 * The amount of coal ore in the tile
	 */
	public byte coal;
	
	/**
	 * The amount of oil in the tile
	 */
	public byte oil;
	
	/**
	 * The amount of nature, life in the tile
	 */
	public byte wildlife;
	/**
	 * The amount of lumber, wood, trees, etc. in the tile
	 */
	public byte lumber;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Owner: ").append(ownerid).append('\n');
		builder.append("Elevation: ").append(elevation).append('\n');
		builder.append("Iron: ").append(ironlike).append('\n');
		builder.append("Bauxite: ").append(bauxite).append('\n');
		builder.append("Copper: ").append(copperlike).append('\n');
		builder.append("Coal: ").append(coal).append('\n');
		builder.append("Oil: ").append(oil).append('\n');
		builder.append("Wildlife: ").append(wildlife).append('\n');
		builder.append("Lumber: ").append(lumber).append('\n');
		return builder.toString();
	}
	
	@Override
	public void pack(Packer packer) {
		packer.packInt(ownerid);
		packer.packV(elevation, ironlike, bauxite, copperlike, coal, oil, wildlife, lumber);
	}
	
	@Override
	public void from(ByteReader reader) {
		ownerid = reader.readInt();
		elevation = reader.read();
		ironlike = reader.read();
		bauxite = reader.read();
		copperlike = reader.read();
		coal = reader.read();
		oil = reader.read();
		wildlife = reader.read();
		lumber = reader.read();
	}
	
}
