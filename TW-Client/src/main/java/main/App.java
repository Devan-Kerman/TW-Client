package main;

import java.awt.Dimension;
import java.net.Socket;

import javax.swing.JFrame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

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
	
	public static Chunk[][] getChunk(int cx, int cy) {
		k.writeObject(out, 0);
		out.flush();
		k.writeObject(out, cx);
		out.flush();
		k.writeObject(out, cy);
		out.flush();
		return k.readObject(in, Chunk[][].class);
	}

	public static void visualize(int cx, int cy) {
		k.writeObject(out, 0);
		out.flush();
		k.writeObject(out, cx);
		out.flush();
		k.writeObject(out, cy);
		out.flush();
		System.out.println("Packet Sent!");
		Chunk[][] data = k.readObject(in, Chunk[][].class);
		System.out.println("Packet Recived!");
		JFrame jf = new JFrame("Chunk viewer");
		jf.add(new VPanel(data[1][1].data));
		jf.pack();
		jf.setVisible(true);
		jf.setSize(new Dimension(100, 100));
	}
	
}
