package server.world.chunk;

public class Simplexor {
	private final int octaves;
	private final double roughness;
	private final double scale;
	/**
	 * Creates new simplexor map thing
	 * @param octaves
	 * 		number of layers to add to point
	 * @param roughness
	 * 		the disturbance in the terrain
	 * @param scale
	 * 		how far appart each point should be
	 */
	public Simplexor(int octaves, double roughness, double scale) {
		this.octaves = octaves;
		this.roughness = roughness; 
		this.scale = scale;
	}
	
	/**
	 * creates a smooth elevation point
	 * @param x
	 * 		tile coordinate x relitive to 0, 0
	 * @param y
	 * 		tile coordinate x relitive to 0, 0
	 * @return
	 * 		an elevation
	 */
	public double genPoint(long x, long y) {
		double total = 0;
		double layerFrequency = scale;
		double layerWeight = 1;
		for(int octave = 0; octave < octaves; octave++) {
			total += SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
			layerFrequency *= 2;
			layerWeight *= roughness;
		}
		return total;
	}
}
