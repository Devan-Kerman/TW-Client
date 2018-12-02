package graphics;

import java.awt.*;
import javax.swing.*;

import graphics.menu.debug.DebugErrors;
import graphics.menu.debug.DebugLog;
import graphics.menu.debug.DebugPosition;
import graphics.menu.debug.DebugUsage;
import graphics.menu.nation.NationEdit;
import graphics.menu.nation.NationFriends;
import graphics.menu.nation.NationStats;
import graphics.menu.nation.NationView;
import graphics.menu.world.WorldMonitor;
import graphics.menu.world.WorldRanks;
import graphics.menu.world.WorldStats;
import graphics.menu.world.WorldView;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final long serialVersionUID = 2315822834624951903L;
	private JMenuBar Menu;
	public JPanel ViewPanel;
	public JLabel Resource;

	public GamePanel() {
		
		JMenu nationMenu = new JMenu("Nation");
		JMenuItem editItem = new JMenuItem("Friends");
		editItem.addActionListener(event -> {
			ViewPanel.removeAll();
			ViewPanel.add(new NationFriends()); 
			ViewPanel.repaint();
		});
		nationMenu.add(editItem);
		JMenuItem statsItem = new JMenuItem("Stats");
		statsItem.addActionListener(event -> {
			ViewPanel.removeAll();
			ViewPanel.add(new NationStats()); 
			ViewPanel.repaint();
		});
		nationMenu.add(statsItem);
		JMenuItem viewItem = new JMenuItem("View");
		viewItem.addActionListener(event -> {
			ViewPanel.removeAll();
			ViewPanel.add(new NationView()); 
			ViewPanel.repaint();
		});
		nationMenu.add(viewItem);
		JMenuItem friendsItem = new JMenuItem("Edit");
		friendsItem.addActionListener(event -> {
			ViewPanel.removeAll();
			ViewPanel.add(new NationEdit()); 
			ViewPanel.repaint();
		});
		nationMenu.add(friendsItem);
		
		
		
		
		
		JMenu debugMenu = new JMenu("Debug");
		
		JMenuItem errorsItem = new JMenuItem("Errors");
		errorsItem.addActionListener(event -> {ViewPanel = new DebugErrors();repaint();});
		debugMenu.add(errorsItem);
		
		JMenuItem logItem = new JMenuItem("Log");
		logItem.addActionListener(event -> {ViewPanel = new DebugLog();repaint();});
		debugMenu.add(logItem);
		
		JMenuItem usageItem = new JMenuItem("Usage");
		usageItem.addActionListener(event -> {ViewPanel = new DebugUsage();repaint();});
		debugMenu.add(usageItem);
		
		JMenuItem positionItem = new JMenuItem("Position");
		positionItem.addActionListener(event -> {ViewPanel = new DebugPosition();repaint();});
		debugMenu.add(positionItem);
		
		
		
		
		
		JMenu worldMenu = new JMenu("World");
		
		JMenuItem monitorItem = new JMenuItem("Monitor");
		monitorItem.addActionListener(event -> {ViewPanel = new WorldMonitor();repaint();});
		worldMenu.add(monitorItem);
		
		JMenuItem statsItem1 = new JMenuItem("Stats");
		statsItem1.addActionListener(event -> {ViewPanel = new WorldStats();repaint();});
		worldMenu.add(statsItem1);
		
		JMenuItem ranksItem = new JMenuItem("Ranks");
		ranksItem.addActionListener(event -> {ViewPanel = new WorldRanks();repaint();});
		worldMenu.add(ranksItem);
		
		JMenuItem viewsItem = new JMenuItem("View");
		viewsItem.addActionListener(event -> {
			ViewPanel.removeAll();
			WorldView wv = new WorldView();
			wv.setBounds(ViewPanel.getBounds());
			ViewPanel.add(wv);
			ViewPanel.revalidate();
			ViewPanel.repaint();
		});
		worldMenu.add(viewsItem);
		
		
		
		
		
		JMenu serverMenu = new JMenu("Server");
		
		JMenuItem usageItem2 = new JMenuItem("Usage");
		serverMenu.add(usageItem2);
		
		JMenuItem statsItem3 = new JMenuItem("Stats");
		serverMenu.add(statsItem3);
		
		
		
		
		
		Menu = new JMenuBar();
		Menu.add(nationMenu);
		Menu.add(debugMenu);
		Menu.add(worldMenu);
		Menu.add(serverMenu);
		ViewPanel = new JPanel();
		Resource = new JLabel("newLabel");

		setPreferredSize(screenSize);
		setLayout(null);

		add(Menu);
		add(ViewPanel);
		add(Resource);
		
		Menu.setBounds(0, 0, screenSize.width, screenSize.height/20);
		ViewPanel.setBounds(0, screenSize.height/20+5, screenSize.width, screenSize.height);
		ViewPanel.setBorder(BorderFactory.createEtchedBorder());
	}
	
	
}
