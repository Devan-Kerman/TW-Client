package serverclasses;

import java.io.Serializable;

import serverclasses.improves.WoodCutter;


public abstract class Improvement implements Serializable {
	private static final long serialVersionUID = 5435731092896677938L;
	protected int tier;
	protected TileEntity tile;

	public static Improvement getImprovement(int id) {
		if (id == 0)
			return new WoodCutter();
		return null;
	}

	public Improvement() {
		tier = 0;
	}

	public abstract void setTile(TileEntity tile);
	
	/**
	 * For serverside only
	 * @param n
	 */
	public abstract void execute(Nation n);

	/**
	 * For serverside only
	 * @param n
	 */
	public abstract void demolish(Nation n);

	/**
	 * For serverside only
	 */
	public abstract boolean tickable();

	/**
	 * For serverside only
	 */
	public abstract ItemStack[] defaultCost();

	/**
	 * For serverside only
	 * @param n
	 */
	public abstract void upgrade(Nation n);

	/**
	 * 
	 */
	public abstract ItemStack[] upgradeCost();

	/**
	 * 
	 */
	public abstract int getTier();
	
	/**
	 * For serverside only
	 */
	public abstract boolean canRun(Nation n);

}
