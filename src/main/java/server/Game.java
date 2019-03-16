package server;


import java.util.LinkedList;
import java.util.List;

import api.modules.Mod;
import server.world.entity.Entity;
import server.world.tile.TileEntity;

public class Game {

	protected static Registry<TileEntity> teRegister;
	protected static Registry<Entity> eRegister;
	protected static List<Mod> mods;
	public static Game instance;
	
	public Game() {
		instance = this;
		mods = new LinkedList<>();
		teRegister = new Registry<>();
		eRegister = new Registry<>();
	}
	
	public void initMods() {
		mods.forEach(Mod::init);
	}
	
	public TileEntity instOfTE(int id) {
		return teRegister.getInstance(id);
	}
	
	public void print() {
		System.out.println("Modules: ");
		mods.forEach(System.out::println);
		System.out.println("TileEntities: ");
		teRegister.forEach((i, t) -> System.out.println(t));
		System.out.println("Entities: ");
		eRegister.forEach((i, e) -> System.out.println(e));
	}
	
	public void registerTiles() {
		mods.forEach(m -> m.registerTiles(teRegister));
	}
	
	public void registerEntities() {
		mods.forEach(m -> m.registerEntities(eRegister));
	}
	
	public void addMod(Mod m) {
		mods.add(m);
	}

}
