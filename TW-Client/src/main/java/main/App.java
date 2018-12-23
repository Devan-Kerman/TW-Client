package main;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;


import graphics.CFrame;
import serverclasses.Chunk;
import serverclasses.Improvement;
import serverclasses.Tile;
import serverclasses.TileEntity;

public class App {
	public static Socket s;
	public static Input in;
	public static Output out;
	public static Kryo kryo;
	public static final String ADDRESS = "localhost";
	public static final int PORT = 6702;
	public static final int CHUNKSIZE = 100;
	public static CFrame game;
	public static DLogger logger;

	public static void main(String[] args) {
		logger = new DLogger();
		try {
			s = new Socket(ADDRESS, PORT);
			kryo = new Kryo();
			kryo.register(Chunk[][].class);
			kryo.register(Chunk[].class);
			kryo.register(Chunk.class);
			kryo.register(Tile.class);
			kryo.register(Tile[].class);
			kryo.register(Tile[][].class);
			kryo.register(TileEntity.class);
			kryo.register(Improvement.class);
			kryo.register(HashMap.class);
			kryo.register(String.class);
			for (Class<? extends Improvement> improv : getClasses(App.class.getClassLoader(), "serverclasses/improves"))
				kryo.register(improv);
			
			out = new Output(s.getOutputStream());
			out.flush();
			
			in = new Input(s.getInputStream());
			game = new CFrame("Tile Wars Client 1.0");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Chunk[][] getChunks(int cx, int cy) {
		DLogger.info("Grabbing new chunks");
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
			DLogger.info("Render Distance Request Accepted!");
		else
			DLogger.warn("Render Distance Request Rejected!");
	}

	public static synchronized <T> T request(Integer[] input, Class<T> returntype) {
		for(Integer i : input) {
			kryo.writeObject(out, i);
			out.flush();
		}
		return kryo.readObject(in, returntype);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static List<Class<? extends Improvement>> getClasses(ClassLoader cl, String pack) {
		String dottedPackage = pack.replaceAll("[/]", ".");
		List<Class<? extends Improvement>> classes = new ArrayList<>();
		try {
			URL upackage = cl.getResource(pack);
			DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
			String line = null;
			while ((line = dis.readLine()) != null) {
				if (line.endsWith(".class")) {
					classes.add((Class<? extends Improvement>) Class
							.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
				}
			}
		} catch (Exception e) {
			DLogger.error(e.getMessage());
		}
		return classes;
	}
}
