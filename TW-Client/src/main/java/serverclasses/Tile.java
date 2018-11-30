package serverclasses;

import java.io.Serializable;

public class Tile implements Serializable {
	public static final long serialVersionUID = 382610168546865107L;
	// 19 bytes
	public int ownerid;
	public Improvement i;

	public byte elevation; // 'Z' (Elevation)
	public byte iron_ore;
	public byte bauxite_ore;
	public byte tin_ore;
	public byte copper_ore;
	public byte gold_ore;
	public byte silver_ore;
	public byte coal_ore;
	public byte platinum_ore;
	public byte nat_gas;
	public byte oil;
	public byte gems;
	public byte wildlife;
	public byte lumber;
	public byte humidity;

}
