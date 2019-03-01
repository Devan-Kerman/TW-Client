package graphics.menu;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;

import main.Clientside;
import main.DLogger;
import server.world.tile.Tile;
import server.world.tile.TileEntity;

public class TileFrame extends JFrame {
	private static final long serialVersionUID = 4759145034977326766L;

	private JList<String> statslist;
	private JButton demolishbutt;
	private JButton buildbutt;
	private JLabel improvelab;
	private JLabel tierlab;
	private JLabel RSSLab;
	private JList<String> consumeList;
	private JLabel upgradelab;
	private JList<String> upgradelist;
	private JLabel prodlab;
	private JList<String> prodlist;
	private JButton upgradebutt;
	private JButton claimbutt;
	private JTextArea extraInfo;
	private JButton speedbutt;
	private JButton pausebutt;
	private JButton unpausebutt;
	private JButton capitalbutt;
	private JList<String> counterlist;
	private JList<String> defensive;
	private TileEntity te;
	private Tile t;
	private void ifNotTileEntity() {
		buildbutt.setEnabled(false);
		demolishbutt.setEnabled(false);
		upgradebutt.setEnabled(false);
		speedbutt.setEnabled(false);
		pausebutt.setEnabled(false);
		capitalbutt.setEnabled(false);
		unpausebutt.setEnabled(false);
		claimbutt.setEnabled(false);
	}
	private void ifOwner() {
		claimbutt.setText("Unclaim");
	}
	private void ifNotOwner() {
		buildbutt.setEnabled(false);
		demolishbutt.setEnabled(false);
		upgradebutt.setEnabled(false);
		speedbutt.setEnabled(false);
		pausebutt.setEnabled(false);
		capitalbutt.setEnabled(false);
		unpausebutt.setEnabled(false);
		claimbutt.setEnabled(true);
	}
	public void init() {
		if(Clientside.id == t.ownerid)
			ifOwner();
		else if(Clientside.id != t.ownerid && te!=null)
			ifNotOwner();
		if(te==null)
			ifNotTileEntity();
	}
	public TileFrame(Tile tin, TileEntity ent) {
		this.t = tin;
		te = ent;
		// construct preComponents
		String[] statslistItems = new String[17];
		int ar = 0;
		statslistItems[ar++] = "Owner ID: "+t.ownerid;
		statslistItems[ar++] = "Elevation: " + t.elevation;
		statslistItems[ar++] = "Iron Ore: " + t.ironlike;
		statslistItems[ar++] = "Bauxite Ore: " + t.bauxite;
		statslistItems[ar++] = "Tin Ore: " + t.tinlike;
		statslistItems[ar++] = "Copper Ore: " + t.copperlike;
		statslistItems[ar++] = "Gold Ore: " + t.copperlike*.1;
		statslistItems[ar++] = "Silver Ore: " + t.tinlike;
		statslistItems[ar++] = "Coal Ore: " + t.coal;
		statslistItems[ar++] = "Platinum Ore: " + t.tinlike*.1;
		statslistItems[ar++] = "Natural Gas: " + t.oil*.5;
		statslistItems[ar++] = "Oil: " + t.oil;
		statslistItems[ar++] = "Gems: " + t.tinlike*.5;
		statslistItems[ar++] = "Wild Life: " + t.wildlife;
		statslistItems[ar++] = "Lumber: " + t.lumber;
		statslistItems[ar++] = "Wildlife: " + t.wildlife;
		// construct components
		statslist = new JList<>(statslistItems);
		demolishbutt = new JButton("Demolish ");
		buildbutt = new JButton("Build");
		if(te!=null) {
			improvelab = new JLabel("Improvement: " + te.getClass().getSimpleName());
			tierlab = new JLabel("Tier: null");
		} else {
			improvelab = new JLabel("Improvement: null");
			tierlab = new JLabel("Tier: null");
		}
		RSSLab = new JLabel("Resource consumption");
		//consumeList = new JList<>(consumeListItems);
		upgradelab = new JLabel("Upgrade resources");
		//upgradelist = new JList<>(upgradelistItems);
		prodlab = new JLabel("Resource production");
		//prodlist = new JList<>(prodlistItems);
		upgradebutt = new JButton("Upgrade");
		claimbutt = new JButton("Claim");
		extraInfo = new JTextArea(5, 5);
		speedbutt = new JButton("Speed up");
		pausebutt = new JButton("Pause");
		unpausebutt = new JButton("Unpause");
		capitalbutt = new JButton("Capital");
		//counterlist = new JList<>(counterlistItems);
		//defensive = new JList<>(defensiveItems);

		// adjust size and set layout
		setPreferredSize(new Dimension(1000, 500));
		setLayout(null);

		// add components
		add(statslist);
		add(demolishbutt);
		add(buildbutt);
		add(improvelab);
		add(tierlab);
		add(RSSLab);
		add(consumeList);
		add(upgradelab);
		add(upgradelist);
		add(prodlab);
		add(prodlist);
		add(upgradebutt);
		add(claimbutt);
		add(extraInfo);
		add(speedbutt);
		add(pausebutt);
		add(unpausebutt);
		add(capitalbutt);
		add(counterlist);
		add(defensive);
		
		int scaler = this.getWidth();
		DLogger.debug(scaler+"");
		// set component bounds (only needed by Absolute Positioning)
		statslist.setBounds(10, 10, 185, 430);
		demolishbutt.setBounds(205, 10, 95, 30);
		buildbutt.setBounds(205, 50, 95, 30);
		improvelab.setBounds(205, 85, 110, 25);
		tierlab.setBounds(205, 110, 110, 25);
		RSSLab.setBounds(205, 135, 140, 25);
		consumeList.setBounds(205, 170, 135, 60);
		upgradelab.setBounds(205, 240, 135, 25);
		upgradelist.setBounds(205, 275, 135, 60);
		prodlab.setBounds(205, 345, 135, 25);
		prodlist.setBounds(205, 380, 135, 60);
		upgradebutt.setBounds(310, 10, 95, 30);
		claimbutt.setBounds(310, 50, 95, 30);
		extraInfo.setBounds(350, 90, 585, 350);
		speedbutt.setBounds(415, 10, 95, 30);
		pausebutt.setBounds(415, 50, 95, 30);
		unpausebutt.setBounds(520, 50, 95, 30);
		capitalbutt.setBounds(520, 10, 95, 30);
		counterlist.setBounds(625, 10, 150, 70);
		defensive.setBounds(785, 10, 150, 70);
		init();
	}

}
