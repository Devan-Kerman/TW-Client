package server.api.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import main.DLogger;
import server.api.bytes.Assembable;
import server.api.bytes.ByteReader;

/**
 * Only for single time operations like file reading (no networking)
 * 
 * @author devan
 *
 */
public class Input {

	InputStream is;

	public Input(InputStream is) {
		this.is = is;
	}

	public <T extends Assembable> T read(Class<T> clas) throws IOException {
		try {
			T t = clas.newInstance();
			t.from(new ByteReader(readAll()));
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			DLogger.error("No. " + clas);
			e.printStackTrace();
		}
		return null;
	}

	public byte[] readAll() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = is.read(data, 0, data.length)) != -1)
			buffer.write(data, 0, nRead);
		buffer.flush();
		return buffer.toByteArray();
	}

}
