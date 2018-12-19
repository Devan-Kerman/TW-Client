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
	public static final String ADDRESS = "devtech.play.ai";
	public static final int PORT = 6702;
	public static final int CHUNKSIZE = 100;
	public static CFrame game = new CFrame("Tile Wars Client 1.0");
	public static void main(String[] args) {
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
			System.out.println("Accepted! ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Chunk[][] getChunks(int cx, int cy) {
		k.writeObject(out, 0);
		out.flush();
		k.writeObject(out, cx);
		out.flush();
		k.writeObject(out, cy);
		out.flush();
		return k.readObject(in, Chunk[][].class);
	}
}
