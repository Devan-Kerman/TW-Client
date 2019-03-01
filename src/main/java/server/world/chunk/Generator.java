package server.world.chunk;

import server.util.CurveGen;
import server.world.tile.Tile;

public class Generator {
	private Generator() {}
	public static final Simplexor TG = new Simplexor(7, 0.6f, 0.0050f); // Terrain generator
	public static final Simplexor HG = new Simplexor(7, 0.6f, 0.0050f); // Humidity generator

	public static final CurveGen IRONGEN = new CurveGen((byte)10, (byte) 10);
	public static final CurveGen BAUXGEN = new CurveGen((byte)10, (byte)100);
	public static final CurveGen TINGEN = new CurveGen((byte)10, (byte)-50);
	public static final CurveGen COALGEN = new CurveGen((byte)10, (byte)150);
	public static final CurveGen OILGEN = new CurveGen((byte)10, (byte)-150);
	
	/**
	 * Generates a tile at the specified coordinate
	 * @param x
	 * 		The x absolute coordinate relitive to 0,0
	 * @param y
	 * 		The y absolute coordinate relitive to 0,0
	 * @return
	 * 		The newly generated tile
	 */
	public static Tile generate(long x, long y) {
		Tile t = new Tile();
		t.elevation = (byte) (TG.genPoint(x, y) * 125);
		t.ironlike = IRONGEN.calculate(t.elevation);
		t.bauxite = BAUXGEN.calculate(t.elevation);
		t.tinlike = TINGEN.calculate(t.elevation);
		t.coal = COALGEN.calculate(t.elevation);
		t.oil = OILGEN.calculate(t.elevation);
		if (t.elevation < -25)
			t.wildlife = 50;
		else if (t.elevation < 25)
			t.wildlife = 15;
		else {
			t.wildlife = (byte) Math.abs(HG.genPoint(x, y) * 25);
			t.lumber = t.elevation>10?(byte) (t.wildlife * Math.random()):0;
		}
		return t;
	}
}
