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

	public static Chunk[][] getChunks(int cx, int cy) {
		logger.info("Grabbing new chunks");
		return request(new Integer[] {0, cx, cy}, Chunk[][].class);
	}
	public static int ping() {
		long start = System.currentTimeMillis();
		request(new Integer[] {3}, Integer.class);
		return (int) (System.currentTimeMillis()-start);
	}
	
	public static void setRenderDistance(int render) {
		int response = request(new Integer[] {2, render}, Integer.class);
		if(response == 0)
			logger.info("Render Distance Request Accepted!");
		else
			logger.warn("Render Distance Request Rejected!");
	}

	public static synchronized <T> T request(Integer[] input, Class<T> returntype) {
		for(Integer i : input) {
			k.writeObject(out, i);
			out.flush();
		}
		return k.readObject(in, returntype);
	}
}
