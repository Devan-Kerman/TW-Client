package server.api.bytes;

import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.zip.GZIPInputStream;

import main.DLogger;
import server.util.math.Bytes;

/**
 * A reader that can read objects and primitives from a sequence of bytes
 * @author devan
 *
 */
public class ByteReader {
	protected byte[] data;
	protected int index;
	
	public ByteReader(byte[] data) {
		this.data = data;
	}
	
	public byte read() {
		return data[index++];
	}
	
	public byte[] peekNext() {
		byte[] next = new byte[data.length-index];
		System.arraycopy(data, index, next, 0, next.length);
		return next;
	}
	
	public double readDouble() {
		return Bytes.toDouble(read(8));
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readObject() {
		int len = readInt();
		try {
			ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new ByteArrayInputStream(read(len))));
			return (T)ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean hasNext() {
		return index < data.length;
	}
	
	public String readString(Charset charset) {
		int ln = readInt();
		return new String(read(ln), charset);
	}
	
	public int readInt() {
		return Bytes.toInt(data[index++], data[index++], data[index++], data[index++]);
	}
	
	public float readFloat() {
		return Bytes.toFloat(data[index++], data[index++], data[index++], data[index++]);
	}
	
	public Point readPoint() {
		return Bytes.toPoint(data[index++], data[index++], data[index++], data[index++], data[index++], data[index++], data[index++], data[index++]);
	}
	
	public short readShort() {
		return Bytes.toShort(data[index++], data[index++]);
	}
	
	public <T extends Assembable> List<T> readList(Class<T> clas) {
		return Bytes.toList(this, clas);
	}
	
	public <K extends Assembable, V extends Assembable> Map<K, V> readMap(Class<K> kl, Class<V> vl) {
		return Bytes.toMap(this, kl, vl);
	}
	
	public void read(Consumer<Byte> cons, int amount) {
		for(int x = 0; x < amount; x++)
			cons.accept(read());
	}
	
	public byte[] read(int len) {
		byte[] arr = new byte[len];
		System.arraycopy(data, index, arr, 0, len);
		index+=len;
		return arr;
	}
	
	public <T extends Assembable> T read(Class<T> clas) {
		try {
			T t = clas.newInstance();
			t.from(this);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			DLogger.error("No Zero Parameter Constructor for: "+clas);
			e.printStackTrace();
		}
		return null;
	}
	
	public int len() {
		return data.length;
	}

}
