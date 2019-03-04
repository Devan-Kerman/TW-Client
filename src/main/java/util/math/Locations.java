package util.math;

import java.awt.Point;

import server.api.area.BPoint;

public class Locations {
	
	public static Point getChunk(long x, long y) {
		return new Point((int) (Math.floorDiv(x, 100l)), (int) (Math.floorDiv(y, 100l)));
	}
	
	public static BPoint getArray(long x, long y) {
		byte mx = (byte) (x % 100), my = (byte) (y % 100);
		return new BPoint((byte)(mx < 0 ? mx + 100 : mx), (byte)(my < 0 ? my + 100 : my));
	}
}