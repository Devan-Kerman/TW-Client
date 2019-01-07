package serverclasses;


/**
 * Contains the data needed to send an update to a client, including the position and the new tile
 * @author devan
 *
 */
public class TileUpdate {

	public TilePoint point;
	public Tile tile;
	/**
	 * Used to send updates to the client if they need it
	 * @param point
	 * 		Position of update
	 * @param tile
	 * 		Tile of data
	 */
	public TileUpdate(TilePoint point, Tile tile) {
		super();
		this.point = point;
		this.tile = tile;
	}
	
	public TileUpdate() {
		
	}
}
