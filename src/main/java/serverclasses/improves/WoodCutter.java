package serverclasses.improves;


import serverclasses.Improvement;
import serverclasses.ItemStack;
import serverclasses.Nation;
import serverclasses.Resource;
import serverclasses.TileEntity;

public class WoodCutter extends Improvement {
	private static final long serialVersionUID = 2756588653745585335L;
	private static ItemStack[][] upgradeCosts = new ItemStack[][] {
			new ItemStack[] { new ItemStack(Resource.MONEY, 1000) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 2500), new ItemStack(Resource.RAWWOOD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 5000), new ItemStack(Resource.STONE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 10000), new ItemStack(Resource.TIN, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 100000), new ItemStack(Resource.BRONZE, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 250000), new ItemStack(Resource.LEAD, 250) },
			new ItemStack[] { new ItemStack(Resource.MONEY, 500000), new ItemStack(Resource.IRON, 250) }, };
	
			
	public WoodCutter() {
		super();
	}
	
	public void setTile(TileEntity tile) {/*serverside implementation*/}
	
	@Override
	public void execute(Nation owner) {/*serverside implementation*/}

	@Override
	public void upgrade(Nation owner) {/*serverside implementation*/}

	@Override
	public ItemStack[] upgradeCost() {
		if(tier+1 < upgradeCosts.length)
			return upgradeCosts[tier + 1];
		return new ItemStack[] {new ItemStack(Resource.MONEY, Integer.MIN_VALUE)};
	}

	@Override
	public boolean tickable() {return true;}

	@Override
	public int getTier() {
		return tier;
	}

	@Override
	public ItemStack[] defaultCost() {return upgradeCosts[0];}

	@Override
	public void demolish(Nation owner) {/*serverside implementation*/}

	@Override
	public boolean canRun(Nation owner) {
		return owner.getInventory().hasEnough(Resource.FOOD, tile.getData("population"));
	}

}
