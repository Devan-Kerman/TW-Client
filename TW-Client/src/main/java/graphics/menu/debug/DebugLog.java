package graphics.menu.debug;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import main.App;
import main.DLogger;

public class DebugLog extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	private JList<String> list;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DebugLog() {
		super();
		setLayout(null);
		DefaultListModel<String> dlm = new DefaultListModel<>();

		list = new JList<>(dlm);

		add(list);

		list.setBounds(screenSize.width / 4, 16, screenSize.width / 2, (int) (screenSize.height / 1.5));

		list.setBackground(new Color(125, 125, 125));
		setBackground(new Color(100, 100, 100));

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				dlm.clear();
				App.logger.log.forEach(dlm::addElement);
			}
		}, 0, 100);

		DLogger.relief("Debug Log Initialized!");
	}
}
