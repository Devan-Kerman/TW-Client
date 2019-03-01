package server.world.entity;

import java.awt.geom.Point2D;

import server.api.area.Locatable;
import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;

public abstract class Entity implements Locatable, Packetable, Assembable {
	
	Point2D location;
	
	public Entity(Point2D loc) {
		location = loc;
	}
	
	public Entity() {
		location = new Point2D.Double(0, 0);
	}

	@Override
	public float getX() {
		return (float) location.getX();
	}

	@Override
	public float getY() {
		return (float) location.getY();
	}
	
	@Override
	public void from(ByteReader reader) {
		location.setLocation(reader.readDouble(), reader.readDouble());
	}
	
	@Override
	public void pack(Packer p) {
		p.packDouble(location.getX());
		p.packDouble(location.getY());
	}

}
