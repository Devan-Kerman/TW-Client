package server.util.math;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;
import server.api.bytes.Packer;
import server.api.bytes.Packetable;

public class Bytes {

	private Bytes() {
		/* Utility class */
	}

	public static <K extends Assembable, V extends Assembable> Map<K, V> toMap(ByteReader reader, Class<K> k,
			Class<V> v) {
		Map<K, V> map = new HashMap<>();
		int len = reader.readInt();
		for (int x = 0; x < len; x++)
			map.put(reader.read(k), reader.read(v));
		return map;
	}

	public static <K extends Packetable, V extends Packetable> void fromMap(Map<K, V> map, Packer packer) {
		packer.packAll(Bytes.fromInt(map.size()));
		map.forEach(packer::autoPack);
	}

	public static <T extends Packetable> void fromList(List<T> list, Packer packer) {
		packer.packAll(Bytes.fromInt(list.size()));
		for (T t : list)
			packer.autoPack(t);
	}

	public static <T extends Assembable> List<T> toList(ByteReader reader, Class<T> clas) {
		List<T> l = new ArrayList<>();
		int r = reader.readInt();
		for (int x = 0; x < r; x++)
			try {
				T t = clas.newInstance();
				t.from(reader);
				l.add(t);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return l;
	}

	public static int toInt(byte... b) {
		return ((0xFF & b[0]) << 24) | ((0xFF & b[1]) << 16) | ((0xFF & b[2]) << 8) | (0xFF & b[3]);
	}

	public static byte[] fromInt(int from) {
		return new byte[] { (byte) (from >>> 24), (byte) (from >>> 16), (byte) (from >>> 8), (byte) from };
	}

	public static float toFloat(byte... b) {
		return Float.intBitsToFloat(b[0] << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | (b[3] & 0xFF));
	}

	public static byte[] fromFloat(float from) {
		return fromInt(Float.floatToIntBits(from));
	}

	public static short toShort(byte a, byte b) {
		return (short) (((0xFF & a) << 8) | (0xFF & b));
	}

	/**
	 * 2 bytes
	 * 
	 * @param bytes
	 * @return
	 */
	public static short toShort(byte... bytes) {
		return (short) (((0xFF & bytes[0]) << 8) | (0xFF & bytes[1]));
	}

	public static byte[] fromShort(short s) {
		return new byte[] { (byte) (s >>> 8), (byte) (s) };
	}

	public static byte[] fromPoint(Point p) {
		return new byte[] {(byte) (p.x >>> 24), (byte) (p.x >>> 16), (byte) (p.x >>> 8), (byte) p.x, (byte) (p.y >>> 24), (byte) (p.y >>> 16), (byte) (p.y >>> 8), (byte) p.y};
	}

	/**
	 * Am lazy, converts t into a point
	 * 
	 * @param bytes 8 bytes 4 for x, and 4 for y
	 * @return a new point
	 */
	public static Point toPoint(byte... bytes) {
		ByteReader reader = new ByteReader(bytes);
		return new Point(reader.readInt(), reader.readInt());
	}

	public static byte[] fromDouble(double dblValue) {
		long data = Double.doubleToRawLongBits(dblValue);
		return new byte[] { 
				(byte) ((data >> 56) & 0xff),
				(byte) ((data >> 48) & 0xff),
				(byte) ((data >> 40) & 0xff),
				(byte) ((data >> 32) & 0xff),
				(byte) ((data >> 24) & 0xff),
				(byte) ((data >> 16) & 0xff),
				(byte) ((data >> 8) & 0xff),
				(byte) (data & 0xff), };
	}

	/**
	 * 8 bytes
	 * 
	 * @param bytes must be 8 long
	 * @return
	 */
	public static double toDouble(byte... bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}

	/**
	 * 8 bytes
	 * 
	 * @param bytes must be 8 long
	 * @return
	 */
	public static long toLong(byte... bytes) {
		long value = 0;
		value += (long) (bytes[0] & 0x000000FF) << 56;
		value += (long) (bytes[1] & 0x000000FF) << 48;
		value += (long) (bytes[2] & 0x000000FF) << 40;
		value += (long) (bytes[3] & 0x000000FF) << 32;
		value += (bytes[4] & 0x000000FF) << 24;
		value += (bytes[5] & 0x000000FF) << 16;
		value += (bytes[6] & 0x000000FF) << 8;
		value += (bytes[7] & 0x000000FF);
		return value;
	}
}
