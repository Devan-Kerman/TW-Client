package server.world.chunk;

import java.awt.Point;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;

import main.Clientside;
import main.DLogger;

/**
 * Manages chunk cache use, generation, serialization and deserialization
 * 
 * @author devan
 *
 */
public class ChunkManager {
	/**
	 * Utility Class
	 */
	private ChunkManager() {
	}

	private static Cache<Point, Chunk> cache;
	private static Executor exec = Executors.newSingleThreadExecutor();
	
	static {
		Cache2kBuilder<Point, Chunk> lc = new Cache2kBuilder<Point, Chunk>() {
		};
		lc.loader(ChunkManager::read);
		cache = lc.build();
	}

	/**
	 * Clears the cache
	 */
	public static void clear() {
		cache.clear();
	}
	
	public static int size() {
		return cache.asMap().size();
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
		Point p = new Point(x, y);
		if(cache.containsKey(p))
			return cache.get(p);
		else {
			exec.execute(() -> {if(!cache.containsKey(p)) cache.get(p);});
			return Chunk.NULL;
		}
	}

	/**
	 * calls {@link #safeChunk(int, int)} using safeChunk
	 * 
	 * @param p Point to get chunk
	 * @return Chunk at the point
	 */
	public static Chunk safeChunk(Point p) {
		return safeChunk(p.x, p.y);
	}

	/**
	 * Reads a chunk from the disk
	 * 
	 * @param x chunk x
	 * @param y chunk y
	 * @return Chunk
	 * @throws IOException
	 */
	private static Chunk read(int x, int y) {
		DLogger.info(String.format("Reading new chunk: [%d, %d]", x, y));
		return Clientside.getChunk(x, y);
	}
	
	private static Chunk read(Point p) {
		return read(p.x, p.y);
	}
}
