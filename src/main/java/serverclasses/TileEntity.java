package serverclasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Litterally just a tile with an improvment/thing
 * @author devan
 *
 */
public class TileEntity extends Tile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129487004281541842L;
	public Improvement i;
	private Map<String, Object> data;
	@SuppressWarnings("unchecked")
	public <T> T getData(String key) {
		return (T) data.get(key);
	}
	public void demolishImprovment(Nation n) {
		if(i!=null)
			i.demolish(n);
	}
	
	@SuppressWarnings("unchecked")
	public <T> void editData(String key, Editor<T> editor) {
		data.put(key, editor.edit((T) data.remove(key)));
	}
	
	public void deleteData(String key) {
		data.remove(key);
	}
	
	public Object addData(String key, Object o) {
		return data.put(key, o);
	}
	/**
	 * Creates a tile entity using the tile
	 * @param t
	 */
	public TileEntity(Tile t) {
		super();
		this.elevation = t.elevation;
		this.ironOre = t.ironOre;
		this.bauxiteOre = t.bauxiteOre;
		this.tinOre = t.tinOre;
		this.copperOre = t.copperOre;
		this.goldOre = t.goldOre;
		this.silverOre = t.silverOre;
		this.coalOre = t.coalOre;
		this.platinumOre = t.platinumOre;
		this.natGas = t.natGas;
		this.oil = t.oil;
		this.gems = t.gems;
		this.wildlife = t.wildlife;
		this.lumber = t.lumber;
		this.humidity = t.humidity;
		this.metalicOre = t.metalicOre;
		data = new HashMap<>();
	}
	/**
	 * For serialization, nothing else!
	 */
	public TileEntity() {
		data = new HashMap<>();
	}
	
	
}
