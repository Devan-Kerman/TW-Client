package server.util;


//TODO REPLACE WITH e^(-x)^2
public class CurveGen {
	private final byte multiplier;
	private final byte shift;
	
	/**
	 * This is a class that generates a "normal like" distribution
	 * @param i
	 * 		Multiplier (vertical stretch)
	 * @param j
	 * 		Shift (x-j)
	 */
	public CurveGen(final byte i, final byte j) {
		this.multiplier = i;
		this.shift = j;
	}
	
	/**
	 * Calculates the y coordinate based on the previously given distribution
	 * @param input
	 * 		The x coordinate
	 * @return
	 * 		The y coordinate
	 */
	public byte calculate(final byte input) {
		byte x = (byte) (input - shift);
		return (byte) (multiplier * ((Math.abs(x) + 14) / (Math.pow((.1 * (x) - 1), 2) + 5)));
	}

}
