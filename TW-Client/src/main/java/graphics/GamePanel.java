package graphics;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
	/**
	 * 
	 */
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final long serialVersionUID = 2315822834624951903L;
	private JMenuBar Menu;
	private JPanel ViewPanel;
	private JLabel Resource;

	public GamePanel() {
		
		JMenu nationMenu = new JMenu("Nation");
		JMenuItem friendsItem = new JMenuItem("Friends");
		nationMenu.add(friendsItem);
		JMenuItem viewItem = new JMenuItem("View");
		nationMenu.add(viewItem);
		JMenuItem statsItem = new JMenuItem("Stats");
		nationMenu.add(statsItem);
		JMenuItem editItem = new JMenuItem("Edit");
		nationMenu.add(editItem);
		
		
		
		JMenu debugMenu = new JMenu("Debug");
		JMenuItem errorsItem = new JMenuItem("Errors");
		debugMenu.add(errorsItem);
		JMenuItem logItem = new JMenuItem("Log");
		debugMenu.add(logItem);
		JMenuItem usageItem = new JMenuItem("Usage");
		debugMenu.add(usageItem);
		JMenuItem positionItem = new JMenuItem("Position");
		debugMenu.add(positionItem);
		
		
		
		JMenu worldMenu = new JMenu("World");
		JMenuItem monitorItem = new JMenuItem("Monitor");
		worldMenu.add(monitorItem);
		JMenuItem statsItem1 = new JMenuItem("Stats");
		worldMenu.add(statsItem1);
		JMenuItem ranksItem = new JMenuItem("Ranks");
		worldMenu.add(ranksItem);
		
		
		
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
	}
}
