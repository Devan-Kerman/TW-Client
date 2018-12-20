package graphics;

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

}
