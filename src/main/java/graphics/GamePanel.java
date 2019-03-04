package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import graphics.menu.debug.DebugErrors;
import graphics.menu.debug.DebugLog;
import graphics.menu.debug.DebugPosition;
import graphics.menu.debug.DebugUsage;
import graphics.menu.nation.NationEdit;
import graphics.menu.nation.NationFriends;
import graphics.menu.nation.NationStats;
import graphics.menu.nation.NationView;
import graphics.menu.server.ServerStats;
import graphics.menu.server.ServerUsage;
import graphics.menu.world.WorldMonitor;
import graphics.menu.world.WorldRanks;
import graphics.menu.world.WorldStats;
import graphics.menu.world.view.WorldView;

public class GamePanel extends JPanel {

	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final long serialVersionUID = 2315822834624951903L;
	private JMenuBar Menu;
	public JPanel ViewPanel;

	public GamePanel() {
		super();
		ViewPanel = new JPanel();
		ViewPanel.setBounds(0, screenSize.height / 20 + 5, screenSize.width, screenSize.height);
		ViewPanel.setBorder(BorderFactory.createEtchedBorder());

		JMenu nationMenu = new JMenu("Nation");

		NationFriends friends = new NationFriends();
		addCL(nationMenu, new JMenuItem("Friends"), friends);
		NationStats stats = new NationStats();
		addCL(nationMenu, new JMenuItem("Stats"), stats);
		NationView view = new NationView();
		addCL(nationMenu, new JMenuItem("View"), view);
		NationEdit edit = new NationEdit();
		addCL(nationMenu, new JMenuItem("Edit"), edit);

		JMenu debugMenu = new JMenu("Debug");

		DebugErrors errors = new DebugErrors();
		addCL(debugMenu, new JMenuItem("Errors"), errors);
		DebugLog log = new DebugLog();
		addCL(debugMenu, new JMenuItem("Log"), log);
		DebugUsage usage = new DebugUsage();
		addCL(debugMenu, new JMenuItem("Usage"), usage);
		DebugPosition position = new DebugPosition();
		addCL(debugMenu, new JMenuItem("Position"), position);

		JMenu worldMenu = new JMenu("World");

		WorldMonitor monitor = new WorldMonitor();
		addCL(worldMenu, new JMenuItem("Monitor"), monitor);
		WorldStats stats2 = new WorldStats();
		addCL(worldMenu, new JMenuItem("Stats"), stats2);
		WorldRanks ranks = new WorldRanks();
		addCL(worldMenu, new JMenuItem("Ranks"), ranks);
		//WorldView view2 = new WorldView();
		WorldView view2 = new WorldView();
		addCL(worldMenu, new JMenuItem("View"), view2);

		JMenu serverMenu = new JMenu("Server");

		ServerStats stats3 = new ServerStats();
		addCL(serverMenu, new JMenuItem("Stats"), stats3);
		ServerUsage view3 = new ServerUsage();
		addCL(serverMenu, new JMenuItem("Usage"), view3);

		Menu = new JMenuBar();
		Menu.add(nationMenu);
		Menu.add(debugMenu);
		Menu.add(worldMenu);
		Menu.add(serverMenu);
		Menu.setBounds(0, 0, screenSize.width, screenSize.height / 20);

		setPreferredSize(screenSize);
		setLayout(null);

		add(Menu);
		add(ViewPanel);
	}

	private void addCL(JMenu menu, JMenuItem item, JPanel object) {
		object.setBounds(ViewPanel.getBounds());
		object.setPreferredSize(ViewPanel.getSize());
		item.addActionListener(event -> {
			ViewPanel.removeAll();
			ViewPanel.add(object);
			ViewPanel.repaint();
			ViewPanel.revalidate();
		});
		menu.add(item);
	}

}
