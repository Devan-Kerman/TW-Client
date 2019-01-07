package serverclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Nation implements Serializable {
	private static final long serialVersionUID = -5982268049046188288L;
	public List<TilePoint> tiles;
	private Inventory inv;
	public int id;
	protected static final Map<Short, Resource> enummap = new HashMap<>();
	protected static final Map<Resource, Short> idmap = new EnumMap<>(Resource.class);
	static {
		for (Resource res : Resource.values()) {
			enummap.put(res.getID(), res);
			idmap.put(res, res.getID());
		}
	}
	/**
	 * Generic nation for serialization
	 */
	public Nation() {
		tiles = new ArrayList<>();
		inv = new Inventory();
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ItemStack itsa : inv.getStacks())
			sb.append(String.format("\t[%s, %d]", itsa.r.name(), itsa.amount));
		sb.append('\n');
		for (TilePoint point2 : tiles)
			sb.append("\t"+point2.toString());
		return String.format("%s : %s", super.toString(), sb.toString());
	}

	public Inventory getInventory() {
		if (inv == null)
			inv = new Inventory();
		return inv;
	}

	public void addTilePoint(TilePoint p) {
		tiles.add(p);
	}
}
