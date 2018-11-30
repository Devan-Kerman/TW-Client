package main;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.swing.JFrame;

import serverclasses.Chunk;
import serverclasses.Packet;

public class App {
	public static Socket s;
	public static ObjectInputStream in;
	public static ObjectOutputStream out;
	public static final String address = "localhost";
	public static final int port = 6702;
	
	public static void main(String[] args) {
		try {
			s = new Socket(address, port);
			out = new ObjectOutputStream(new BufferedOutputStream(s.getOutputStream()));
			out.flush();
			in = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
			System.out.println("Accepted! ");
			visualize(1, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void visualize(int cx, int cy) throws ClassNotFoundException, IOException {
		Packet p = new Packet();
		p.request = "position";
		HashMap<String, Object> h = new HashMap<>();
		h.put("X", cx);
		h.put("Y", cy);
		p.data = h;
		out.writeObject(p);
		out.flush();
		System.out.println("Packet Sent!");
		Chunk[][] data = (Chunk[][]) in.readObject();
		System.out.println("Packet Recived!");
		JFrame jf = new JFrame("Chunk viewer");
		jf.add(new VPanel(data[1][1].data));
		jf.pack();
		jf.setVisible(true);
		jf.setSize(new Dimension(100, 100));
	}

}
