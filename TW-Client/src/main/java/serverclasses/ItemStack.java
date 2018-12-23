package serverclasses;

public class ItemStack {

	public Resource r;
	public int amount;

	public ItemStack(Resource r, int amount) {
		super();
		this.r = r;
		this.amount = amount;
	}
	
	/**
	 * Multiplies the current amount of resources by the multiplier
	 * @param multiplier
	 * 		the multiplier
	 */
	public void multiply(int multiplier) {
		amount*=multiplier;
	}
	
	/**
	 * Adds the current amount of resources by the given amount
	 * @param add
	 * 		the given amount
	 */
	public void add(int add) {
		amount += add;
	}
}