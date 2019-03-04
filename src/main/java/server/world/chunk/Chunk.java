package server.world.chunk;

import server.api.area.BPoint;
import server.api.area.SpaceMap;
import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;
import server.world.entity.Entity;
import server.world.tile.Tile;
import server.world.tile.TileEntity;

public class Chunk implements Packetable, Assembable {
	/**
	 * The dimensions of the chunk (size x size)
	 */
	public static final int CHUNKSIZE = 100;
	public static final Chunk NULL = new Chunk();
	static {
		for(int x = 0; x < NULL.tiles.length; x++)
			for(int y = 0; y < NULL.tiles[x].length; y++)
				NULL.tiles[x][y] = Tile.NULL;
	}
	public final Tile[][] tiles;

	public SpaceMap<TileEntity> tileEnts;
	public SpaceMap<Entity> entities;

	public int cx, cy;

	/**
	 * Sets itsef as a brand new chunk at the specified coordinate, This is not
	 * meant to be used anywhere other than in {@linkplain ChunkManager}, or for
	 * testing
	 * 
	 * @param cx Chunk x coordinate
	 * @param cy Chunk y coordinate
	 */
	public Chunk(final int cx, final int cy) {
		this();
		this.cx = cx;
		this.cy = cy;
		for (int r = 0; r < CHUNKSIZE; r++)
			for (int c = 0; c < CHUNKSIZE; c++)
				tiles[r][c] = Generator.generate(cx * CHUNKSIZE + (long) r, cy * CHUNKSIZE + (long) c);
	}

	/**
	 * For serialization! do not use!!
	 */
	public Chunk() {
		tiles = new Tile[CHUNKSIZE][CHUNKSIZE];
		entities = new SpaceMap<>(Entity.class);
		tileEnts = new SpaceMap<>(TileEntity.class);
	}

	@Override
	public void pack(Packer packer) {
		for (Tile[] row : tiles)
			for (Tile t : row)
				t.pack(packer);
		packer.autoPack(tileEnts, entities);
	}

	@Override
	public void from(ByteReader reader) {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[x].length; y++)
					tiles[x][y] = reader.read(Tile.class);
		tileEnts.from(reader);
		entities.from(reader);
	}
	
	public Tile get(byte x, byte y) {
		return tiles[x][y];
	}
	
	public Tile get(BPoint b) {
		return tiles[b.x][b.y];
	}
}
