package serverclasses;

import java.io.Serializable;

public class Chunk implements Serializable {
	public static final int CHUNKSIZE = 100;
	private static final long serialVersionUID = 7297134163391801440L;
	public Tile[][] data;
	public int x, y;
}
