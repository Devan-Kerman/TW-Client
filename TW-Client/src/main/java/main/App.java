package main;

import java.net.Socket;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import graphics.CFrame;
import serverclasses.Chunk;
import serverclasses.Improvement;
import serverclasses.Tile;

public class App {
	public static Socket s;
	public static Input in;
	public static Output out;
	public static Kryo k;
	public static final String ADDRESS = "localhost";
	public static final int PORT = 6702;
	public static final int CHUNKSIZE = 100;
	public static CFrame game;
	public static DLogger logger;
	public static void main(String[] args) {
		logger = new DLogger();
		try {
			s = new Socket(ADDRESS, PORT);
			k = new Kryo();
			k.register(Chunk[][].class);
			k.register(Chunk[].class);
			k.register(Chunk.class);
			k.register(Tile[].class);
			k.register(Tile[][].class);
			k.register(Tile.class);
			k.register(Improvement.class);
			out = new Output(s.getOutputStream());
			out.flush();
			in = new Input(s.getInputStream());
			game = new CFrame("Tile Wars Client 1.0");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public static void setRenderDistance(int render) {
		k.writeObject(out, 2);
		out.flush();
		k.writeObject(out, render);
		out.flush();
	}
	
	
	public static Chunk[][] getChunks(int cx, int cy) {
		k.writeObject(out, 0);
		out.flush();
		k.writeObject(out, cx);
		out.flush();
		k.writeObject(out, cy);
		out.flush();
		logger.info("Grabbing new chunks");
		return k.readObject(in, Chunk[][].class);
	}
}
