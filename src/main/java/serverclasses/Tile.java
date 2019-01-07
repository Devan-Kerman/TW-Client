package serverclasses;

import java.io.Serializable;

public class Tile implements Serializable {

	public static final long serialVersionUID = 382610168546865107L;
	// 28 bytes (20 + 8)
	public int ownerid;
	public byte elevation;
	public byte ironOre;
	public byte bauxiteOre;
	public byte tinOre;
	public byte copperOre;
	public byte goldOre;
	public byte silverOre;
	public byte coalOre;
	public byte platinumOre;
	public byte natGas;
	public byte oil;
	public byte gems;
	public byte wildlife;
	public byte lumber;
	public byte humidity;
	public byte metalicOre;

}