package api.modules;


import server.Registry;
import server.world.entity.Entity;
import server.world.tile.TileEntity;

public abstract class Mod {
	public abstract void init();
	public abstract void registerTiles(Registry<TileEntity> registryTE);
	public abstract void registerEntities(Registry<Entity> registryE);
}
