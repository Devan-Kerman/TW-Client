package main;

import java.awt.Point;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import graphics.CFrame;
import graphics.FirstPanel;
import networking.Connection;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.util.math.Bytes;
import server.world.chunk.Chunk;
import util.math.Locations;

public class Clientside {
	public static CFrame game;
	public static FirstPanel panel;
	public static JFrame login;
	public static int id;
	private static Connection connection;

	public static void main(String[] args) {
		connection = new Connection();
		connection.open("localhost", 3456);
		connection.queue(0, new byte[] {1});
		login = new JFrame("Login");
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.getContentPane().add(new FirstPanel());
		login.pack();
		login.setVisible(true);
	}
	
	static class CF {
		Future<Chunk> f;
	}
	
	public static Chunk getChunk(int x, int y) {
		Packer p = new Packer(8);
		p.packPoint(new Point(x, y));
		ByteReader reader = new ByteReader(connection.queue(2, p.unpack()));
		return reader.read(Chunk.class);
	}
	
	public static boolean claim(long x, long y) {
		Packer p = new Packer(10);
		p.packPoint(Locations.getChunk(x, y));
		p.autoPack(Locations.getArray(x, y));
		return connection.queue(4, p.unpack())[0] == 1;
	}
	
	public static int login(String user, String pass) {
		Packer p = new Packer();
		p.packString(user, StandardCharsets.US_ASCII);
		p.packString(pass, StandardCharsets.US_ASCII);
		return Bytes.toInt(connection.queue(3, p.unpack()));
	}
	
	public static int register(String user, String pass) {
		Packer p = new Packer();
		p.packString(user, StandardCharsets.US_ASCII);
		p.packString(pass, StandardCharsets.US_ASCII);
		return Bytes.toInt(connection.queue(1, p.unpack()));
	}
	//public static List<TileUpdates> getUpdates();
}
