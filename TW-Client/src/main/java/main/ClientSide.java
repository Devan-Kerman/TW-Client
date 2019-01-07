package main;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;

import graphics.CFrame;
import graphics.LoginPanel;
import serverclasses.Chunk;
import serverclasses.Improvement;
import serverclasses.Tile;
import serverclasses.TileEntity;
import serverclasses.TilePoint;
import serverclasses.TileUpdate;
import serverclasses.improves.WoodCutter;

public class ClientSide {
	public static Socket s;
	public static Input in;
	public static Output out;
	public static Kryo kryo;
	public static final String ADDRESS = "localhost";
	public static final int PORT = 6702;
	public static final int CHUNKSIZE = 100;
	public static CFrame game;
	public static DLogger logger;
	public static LoginPanel panel;
	public static JFrame frame;
	public static int id;

	public static void main(String[] args) {
		init();
		frame = new JFrame ("Login");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new LoginPanel());
        frame.pack();
        frame.setVisible (true);
	}
	public static void init() {
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
			kryo.register(WoodCutter.class);
			kryo.register(TileUpdate.class);
			kryo.register(TilePoint.class);
			kryo.register(Improvement.class);
			kryo.register(HashMap.class);
			kryo.register(String.class);
			MapSerializer serializer = new MapSerializer();
			kryo.register(HashMap.class, serializer);
			kryo.register(LinkedHashMap.class, serializer);
			serializer.setKeyClass(String.class, kryo.getSerializer(String.class));
			serializer.setKeysCanBeNull(false);
			serializer.setKeyClass(String.class, kryo.getSerializer(String.class));
			for (Class<? extends Improvement> improv : getClasses(ClientSide.class.getClassLoader(), "serverclasses/improves"))
				kryo.register(improv);
			out = new Output(s.getOutputStream());
			out.flush();
			in = new Input(s.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Chunk[][] getChunks(int cx, int cy) {
		DLogger.info("Grabbing new chunks");
		return request(new Integer[] {0, cx, cy}, Chunk[][].class);
	}
	public static void setRenderDistance(int render) {
		int response = request(new Integer[] {2, render}, Integer.class);
		if(response == 0)
			DLogger.info("Render Distance Request Accepted!");
		else
			DLogger.warn("Render Distance Request Rejected!");
	}

	public static synchronized <T> T request(Object[] input, Class<T> returntype) {
		for(Object i : input) {
			kryo.writeObject(out, i);
			out.flush();
		}
		return returntype.cast(kryo.readClassAndObject(in));
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
	
	public static List<TileUpdate> getUpdates() {
		List<TileUpdate> updates = new ArrayList<>();
		kryo.writeObject(out, 1);
		out.flush();
		for(int x = 0; x < (Integer)kryo.readClassAndObject(in); x++)
			updates.add((TileUpdate)kryo.readClassAndObject(in));
		return updates;
	}
}
