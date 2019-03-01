package server.errors;

import server.nation.resources.Inventory;

public class NotEnoughResourcesException extends RuntimeException {

	private static final long serialVersionUID = -24889688620699569L;

	/**
	 * Creates a new NotEnoughResourcesException with the specified message
	 * This exception is thrown when an ItemStack/Set of resources are attempted to been
	 * withdrawed from an inventory
	 * @see {@link Inventory#hasEnough(game.resources.ItemStack)}, {@link Inventory#hasEnough(game.resources.ItemStack[])}, {@link Inventory#hasEnough(game.resources.Resource, int)}
	 * @param s
	 * 		The message string
	 */
	public NotEnoughResourcesException(String s) {
		super(s);
	}
}
