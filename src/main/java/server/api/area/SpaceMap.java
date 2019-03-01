package server.api.area;

import java.awt.Point;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;

/**
 * A virtual map of an area, rather than storing each object in an 2d array, it mearly stores the coordinates of each object
 * @author devan
 *
 * @param <T>
 */
public class SpaceMap<T extends Locatable & Packetable & Assembable> implements Packetable, Assembable {

	Map<Point, T> locations;
	Class<T> tclass;

	public SpaceMap(Class<T> tclass) {
		locations = new ConcurrentHashMap<>();
		this.tclass = tclass;
	}
	
	/**
	 * Gets the object at the exact coordinate on the map
	 * @param x
	 * 		the x coor
	 * @param y
	 *		the y coor
	 * @return
	 * 		null if not found, or the object
	 */
	public T at(int x, int y) {
		return locations.get(new Point(x, y));
	}
	

	/**
	 * Finds the closest object in the map to the point
	 * @param point
	 * 		the location
	 * @return
	 * 		the object or null
	 */
	private T closest(Point point) {
		Loc ya = new Loc(null, Integer.MAX_VALUE);
		locations.forEach((p, t) -> {
			double di = p.distance(point);
			if (di < ya.dist) {
				ya.t = t;
				ya.dist = di;
			}
		});
		return ya.t;
	}

	private class Loc {
		T t;
		double dist;

		Loc(T t, double dist) {
			this.t = t;
			this.dist = dist;
		}
	}

	public T closest(int x, int y) {
		return closest(new Point(x, y));
	}

	public T put(Point location, T object) {
		return locations.put(location, object);
	}

	public void forEach(Consumer<T> cons) {
		locations.forEach((l, t) -> cons.accept(t));
	}

	@Override
	public void from(ByteReader reader) {
		int c = reader.readInt();
		for(int x = 0; x < c; x++)
			locations.put(reader.readPoint(), reader.read(tclass));
	}

	@Override
	public void pack(Packer p) {
		p.packInt(locations.size());
		locations.forEach((poi, t) -> {
			p.packPoint(poi);
			p.autoPack(t);
		});
	}

}
