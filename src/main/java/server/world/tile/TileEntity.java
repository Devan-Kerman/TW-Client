package server.world.tile;

import java.awt.Point;

import server.api.area.BPoint;
import server.api.area.Locatable;
import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;
import server.nation.Nation;
import server.world.chunk.Chunk;

public abstract class TileEntity implements Locatable, Assembable, Packetable {

	Point chunk;
	BPoint location;

	public TileEntity() {
		location = new BPoint((byte) 0, (byte) 0);
		chunk = new Point();
	}

	public TileEntity(Point chunk, BPoint location) {
		this.chunk = chunk;
		this.location = location;
	}

	public abstract int getId();

	public abstract <T extends Assembable & Packetable> T data();

	public abstract void run(Nation n, Chunk c, Tile t);

	@Override
	public float getX() {
		return location.x;
	}

	@Override
	public float getY() {
		return location.y;
	}

	@Override
	public void pack(Packer p) {
		p.packPoint(chunk);
		p.autoPack(location);
		p.autoPack(data());
	}

	@Override
	public void from(ByteReader reader) {
		chunk = reader.readPoint();
		location = reader.read(BPoint.class);
		data().from(reader);
	}

}
