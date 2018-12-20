package main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DLogger {

	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public List<String> errors;
	public List<String> log;

	public DLogger() {
		errors = new ArrayList<>();
		log = new ArrayList<>();
		LOGGER.setLevel(Level.FINEST);
		LOGGER.addHandler(new Handler() {

			@Override
			public void close() {
				/* dunno what this does */
			}

			@Override
			public void flush() {
				/* dunno what this does */
			}

			@Override
			public void publish(LogRecord r) {
				Level l = r.getLevel();
				if (l.intValue() >= Level.WARNING.intValue())
					errors.add(String.format("<html><p color=\"%s\">[%s]: %s</p></html>", levelHex(l),
							r.getParameters()[0], r.getMessage()));
				else
					log.add(String.format("<html><p color=\"%s\">[%s]: %s</p></html>", levelHex(l),
							r.getParameters()[0], r.getMessage()));
				if (errors.size() > 100)
					errors.remove(0);
				if (log.size() > 100)
					log.remove(0);
			}

		});

	}

	/*
	 * Error = #ff0000 Warn = #ff6600 Info = #ffffff Debug = #33ccff Relief = #99ff66
	 */
	private String levelHex(Level l) {
		if (l.intValue() == Level.SEVERE.intValue())
			return "#ff0000";
		else if (l.intValue() == Level.WARNING.intValue())
			return "#ff6600";
		else if (l.intValue() == Level.CONFIG.intValue())
			return "#33ccff";
		else if(l.intValue() == Level.FINE.intValue())
			return "#99ff66";
		return "#ffffff";
	}

	public void error(String info) {
		LOGGER.log(Level.SEVERE, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void warn(String info) {
		LOGGER.log(Level.WARNING, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void info(String info) {
		LOGGER.log(Level.INFO, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void debug(String info) {
		LOGGER.log(Level.CONFIG, info, Thread.currentThread().getStackTrace()[2]);
	}

	public void relief(String info) {
		LOGGER.log(Level.FINE, info, Thread.currentThread().getStackTrace()[2]);
	}

}
