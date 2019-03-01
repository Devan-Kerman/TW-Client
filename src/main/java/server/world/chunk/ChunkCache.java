package server.world.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;

/**
 * Stores chunks in memory for faster access
 * @author devan
 *
 */
public class ChunkCache {
	final Long2ObjectLinkedOpenHashMap<Chunk> map;
	private final int maxSize;
	
	/**
	 * Default size 5000
	 */
	public ChunkCache() {
		this(5000);
	}
	
	/**
	 * Custom size
	 * @param maxSize
	 * 		Max size of the cache
	 */
	public ChunkCache(final int maxSize) {
		this.map = new Long2ObjectLinkedOpenHashMap<>(5000);
		this.maxSize = maxSize;
	}
	
	/**
	 * Gets the chunk in the coordinates if not found returns null
	 * @param x
	 * 		Chunk x int
	 * @param y
	 * 		Chunk y int
	 * @return
	 * 		Chunk at coordinate could be null!
	 */
	public Chunk get(final int x, final int y) {
		return map.getAndMoveToFirst(twoIntsToLong(x, y));
	}
	
	/**
	 * Adds a chunk to the cache
	 * @param x
	 * 		chunk x
	 * @param y
	 * 		chunk y
	 * @param chunk
	 * 		Chunk
	 */
	public void put(final int x, final int y, final Chunk chunk) {
		map.putAndMoveToFirst(twoIntsToLong(x, y), chunk);
		if(map.size() > maxSize)
			map.removeLast();
	}
	
	
	/**
	 * Checks if the Cache has the data for the specified coordinates
	 * @param x
	 * 		chunk x
	 * @param y
	 * 		chunk y
	 * @return
	 * 		true/false
	 */
	public boolean containsKey(final int x, final int y) {
		return map.containsKey(twoIntsToLong(x, y));
	}
	
	/**
	 * Converts 2 integers into 1 long with bitwise operations
	 * @param x
	 * 		integer 1
	 * @param y
	 * 		integer 2
	 * @return
	 * 		long
	 */
	public static long twoIntsToLong(final int x, final int y) {
		return (((long)x) << 32) | (y & 0xffffffffL);
	}
}
