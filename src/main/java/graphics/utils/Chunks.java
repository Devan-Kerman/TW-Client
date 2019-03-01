package graphics.utils;

import main.Clientside;
import server.world.chunk.Chunk;
import server.world.tile.Tile;
import server.world.tile.TileEntity;

public class Chunks {
	private Chunk[][] chunks = new Chunk[3][3];
	
	public Tile get(int x, int y) {
		return chunks[x/100][y/100].get((byte)(x%100), (byte)(y%100));
	}
	
	public void update(int cx, int cy) {
		chunks = Clientside.getChunks(cx, cy);
	}
	
	public TileEntity getTE(int x, int y) {
		return chunks[x/100][y/100].tileEnts.at(x%100, y%100);
	}
	
	public int len() {
		return Chunk.CHUNKSIZE;
	}
	
	public Chunk getCH(int cx, int cy) {
		return chunks[cx][cy];
	}
}
