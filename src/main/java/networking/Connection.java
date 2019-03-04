package networking;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import main.DLogger;
import server.api.bytes.Packer;
import server.util.math.Bytes;

public class Connection {

	private GZIPInputStream in;
	private GZIPOutputStream out;
	private Socket socket;

	public Connection() {
		int_alloc = new byte[4];
		locked = new AtomicBoolean(false);
	}

	public void open(String host, int port) {
		try {
			socket = new Socket(host, port);
			in = new GZIPInputStream(socket.getInputStream(), 4096);
			out = new GZIPOutputStream(socket.getOutputStream(), 4086, true);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			in.close();
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private byte[] int_alloc;
	private AtomicBoolean locked;
	public byte[] queue(int opcode, byte[] data) {
		while(locked.get());
		locked.set(true);
		try {
			out.write(Bytes.fromInt(opcode));
			out.write(Bytes.fromInt(data.length));
			out.write(data);
			out.flush();
			int len = in.read(int_alloc);
			if (len != 4)
				throw new IOException("Packet length was corrupted!");
			int reed = Bytes.toInt(int_alloc);
			Packer p = new Packer();
			DLogger.warn("Reading...");
			p.read(in, reed);
			DLogger.relief("Read!");
			locked.set(false);
			return p.unpack();
		} catch (IOException e) {
			e.printStackTrace();
		}
		locked.set(false);
		return new byte[0];
	}
}
