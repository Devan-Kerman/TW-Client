package server.world.chunk;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import server.api.io.Input;

/**
 * Manages chunk cache use, generation, serialization and deserialization
 * @author devan
 *
 */
public class ChunkManager {
	/**
	 * Utility Class
	 */
	private ChunkManager() {
	}

	private static ChunkCache cache = new ChunkCache();

	/**
	 * Clears the cache
	 */
	public static void clear() {
		cache = new ChunkCache();
	}

	/**
	 * This method will first check if the specified chunk is already in the cache
	 * if not, it checks in the ChunkData folder if found, it inputs it into the
	 * cache and returns it, if all else fails, it generates a brand new chunk, and
	 * it inputs it into the cache and returns it.
	 * 
	 * @param x The Chunk's x coordinate
	 * @param y The Chunk's y coordinate
	 * @return The Chunk at specified position
	 */
	public static Chunk safeChunk(final int x, final int y) {
		Chunk c;
		if (cache.containsKey(x, y))
			c = cache.get(x, y);
		else
			try {c = read(x, y);} catch (Exception e) {e.printStackTrace();return null;}
		cache.put(c.cx, c.cy, c);
		return c;
	}

	/**
	 * calls {@link #safeChunk(int, int)} using safeChunk
	 * @param p
	 * 		Point to get chunk
	 * @return
	 * 		Chunk at the point
	 */
	public static Chunk safeChunk(Point p) {
		return safeChunk(p.x, p.y);
	}
	
	
	/**
	 * Reads a chunk from the disk
	 * @param x
	 * 		chunk x
	 * @param y
	 * 		chunk y
	 * @return
	 * 		Chunk
	 * @throws IOException
	 * TODO replace with network implementation!
	 */
	private static Chunk read(int x, int y) throws IOException {
		return new Input(new BufferedInputStream(new FileInputStream("Chunkdata/[" + x + "," + y + "].chunk"))).read(Chunk.class);
	}
}
