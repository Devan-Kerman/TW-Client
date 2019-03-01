package server.nation.resources;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;
import server.errors.NotEnoughResourcesException;
import server.util.math.Bytes;

public class Inventory implements Packetable, Assembable {

	@Override
	public void from(ByteReader reader) {
		int src = reader.readInt();
		for(int x = 0; x < src; x++)
			resources.put(Resource.values()[(int)Bytes.toShort(reader.read(2))], reader.readInt());
	}

	@Override
	public void pack(Packer p) {
		p.packInt(resources.size());
		resources.forEach((r, c) -> {p.packShort(r.getID()); p.packInt(c);});
	}
	
	
	/**
	 * The raw data of the inventory
	 */
	final Map<Resource, Integer> resources;
	
	/**
	 * This method was sorta brute forced, think of a better way
	 * @return
	 * 		a list of the itemstacks
	 */
	public List<ItemStack> getStacks() {
		List<ItemStack> is = new ArrayList<>();
		resources.forEach((t, u) -> is.add(new ItemStack(t, u)));
		return is;
	}

	/**
	 * Returns the size of the inventory
	 * @return
	 */
	public int getSize() {
		return resources.size();
	}
	
	/**
	 * This creates a new "Inventory" of resources using the Enums specified in
	 * {@link Resource}
	 */
	public Inventory() {
		resources = new EnumMap<>(Resource.class);
	}

	/**
	 * This checks whether the specified item stack can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param r      The Enum of the resource
	 * @param amount The amount of the resource
	 * @return if the inventory has equal/surplus of that resource
	 */
	public boolean hasEnough(Resource r, int amount) {
		return resources.getOrDefault(r, 0) >= amount;
	}

	/**
	 * This checks whether the specified item stack can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param stacks the item stack
	 * @return if the inventory has equal/surplus of that resource
	 */
	public boolean hasEnough(ItemStack stacks) {
		return resources.getOrDefault(stacks.r, 0) >= stacks.amount;
	}

	/**
	 * This checks whether the specified {@link ItemStack}s can be taken out of the
	 * inventory without throwing a {@link NotEnoughResourcesException}
	 * 
	 * @param stacks The array of item stacks
	 * @return if the inventory has equal/surplus of those resources
	 */
	public boolean hasEnough(ItemStack...stacks) {
		for (ItemStack is : stacks)
			if (!hasEnough(is))
				return false;
		return true;
	}

	/**
	 * Removes the specified resources from the inventory,
	 * throws a {@link NotEnoughResourcesException} if the target inventory
	 * does not contain enough resources
	 * @see {@link #hasEnough(ItemStack) hasEnough}, {@link #hasEnough(ItemStack[]) hasEnough}, {@link #hasEnough(Resource, int) hasEnough}
	 * @throws {@link NotEnoughResourcesException}
	 * @param resource
	 * 		Enum of the resource
	 * @param amount
	 * 		Amount of the resource
	 */
	public void take(Resource resource, int amount) {
		if (amount <= 0)
			throw new IllegalArgumentException("Amount must be greater than zero");

		int current = resources.getOrDefault(resource, 0);
		
		if (current >= amount)
			resources.put(resource, current - amount);
		else
			throw new NotEnoughResourcesException("Not enough " + resource + " current amount: " + current);
	}
	
	/**
	 * Removes the specified resources from the inventory,
	 * throws a {@link NotEnoughResourcesException} if the target inventory
	 * does not contain enough resources
	 * @see {@link #hasEnough(ItemStack) hasEnough}, {@link #hasEnough(ItemStack[]) hasEnough}, {@link #hasEnough(Resource, int) hasEnough}
	 * @throws {@link NotEnoughResourcesException}
	 * @param i
	 * 		{@link ItemStack}
	 */
	public void take(ItemStack i) {
		take(i.r, i.amount);
	}

	/**
	 * Adds the amount of resources to the inventory
	 * @param resource
	 * 		Enum of {@link Resource}
	 * @param amount
	 * 		Amount of resource (int)
	 */
	public void put(Resource resource, int amount) {
		resources.merge(resource, amount, (a, b) -> a + b);
	}

	/**
	 * Short method for adding multiple resources at once from the inventory
	 * @param resources
	 * 		{@link List} of {@link ItemStack}s
	 */
	public void putAll(List<ItemStack> resources) {
		for (ItemStack stack : resources)
			put(stack.r, stack.amount);
	}

	/**
	 * Short method for adding multiple resources at once from the inventory
	 * @param resources
	 * 		Array of {@link ItemStack}s
	 */
	public void putAll(ItemStack[] resources) {
		for (ItemStack stack : resources)
			put(stack.r, stack.amount);
	}

	/**
	 * Short method for taking multiple resources at once from the inventory
	 * throws a {@link NotEnoughResourcesException} if the target inventory
	 * does not contain enough resources
	 * @see {@link #hasEnough(ItemStack) hasEnough}, {@link #hasEnough(ItemStack[]) hasEnough}, {@link #hasEnough(Resource, int) hasEnough}
	 * @throws {@link NotEnoughResourcesException}
	 * @param resources
	 * 		{@link List} of {@link ItemStack}s
	 */
	public void takeAll(List<ItemStack> resources) {
		for (ItemStack stack : resources)
			take(stack.r, stack.amount);
	}

	/**
	 * Short method for taking multiple resources at once from the inventory
	 * throws a {@link NotEnoughResourcesException} if the target inventory
	 * does not contain enough resources
	 * @see {@link #hasEnough(ItemStack) hasEnough}, {@link #hasEnough(ItemStack[]) hasEnough}, {@link #hasEnough(Resource, int) hasEnough}
	 * @throws {@link NotEnoughResourcesException}
	 * @param resources
	 * 		Array of {@link ItemStack}s
	 */
	public void takeAll(ItemStack[] resources) {
		for (ItemStack stack : resources)
			take(stack.r, stack.amount);
	}

	

}
