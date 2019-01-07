package serverclasses;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Inventory {

	public Map<Resource, Integer> resources;
	
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

	public int getSize() {
		return resources.size();
	}
	
	/**
	 * This creates a new "Inventory" of resources using the Enums specified in
	 * game.resources.Resource
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
	public boolean hasEnough(ItemStack[] stacks) {
		for (ItemStack is : stacks)
			if (!hasEnough(is))
				return false;
		return true;
	}
}
