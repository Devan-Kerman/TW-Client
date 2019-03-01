package server.nation.resources;

/**
 * Contains all the enums of the resources a player/nation can have
 * @author devan
 *
 */
public enum Resource {
	COAL(0), OIL(1), IRON(2),
	MONEY(3), RAWWOOD(4), STONE(5), 
	TIN(8), COPPER(7), BRONZE(6),
	ALUMINUM(9), STEEL(10), TUNGSTEN(11),
	GOLD(12), SILVER(13), LEAD(14), MERCURY(15),
	PLATINUM(16), COBALT(17), FOOD(18);
	private final short id;
	Resource(int id) {this.id = (short) id;}
	public short getID() {return id;}
}
