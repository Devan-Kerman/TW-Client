package graphics;

import java.awt.Point;

public class LongPoint {

	public long x;
	public long y;

	public LongPoint(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return String.format("[%d, %d]", x, y);
	}
	
	public Point difference(LongPoint p) {
		return new Point((int)(p.x-x), (int)(p.y-y));
		
	}

}
