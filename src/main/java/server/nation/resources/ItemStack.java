package server.nation.resources;

import java.security.InvalidParameterException;

/**
 * This class represents a "collection" of 1 type of resource and a specified amount
 * @author devan
 *
 */
public class ItemStack {

	/**
	 * The resource's enum
	 */
	public final Resource r;
	
	/**
	 * The amount of said resource the item stack has, CANNOT BE NEGATIVE
	 */
	public int amount;

	/**
	 * Creates a new ItemStack
	 * @throws InvalidParameterException
	 * 		if amount is negative
	 * @param r
	 * 		Enum of the resource
	 * @param amount
	 * 		Amount of the resource
	 */
	public ItemStack(Resource r, int amount) {
		super();
		this.r = r;
		if(amount < 0)
			throw new InvalidParameterException("ItemStack cannot be negative");
		this.amount = amount;
	}
	
	/**
	 * Multiplies the current amount of resources by the multiplier
	 * @throws InvalidParameterException
	 * 		if multiplier is negative
	 * @param multiplier
	 * 		the multiplier
	 */
	public void multiply(int multiplier) {
		if(multiplier < 0)
			throw new InvalidParameterException("ItemStack cannot be negative");
		amount*=multiplier;
	}
	
	/**
	 * Adds the current amount of resources by the given amount, can take negative amounds
	 * @throws InvalidParameterException
	 * 		if multiplier is negative
	 * @param add
	 * 		the given amount
	 */
	public void add(int add) {
		if(amount+add < 0)
			throw new InvalidParameterException("ItemStack cannot be negative");
		amount += add;
	}
}