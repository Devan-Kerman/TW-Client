package graphics.menu.debug;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DebugUsage extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;

	public DebugUsage() {
		super();
	}
}

class GraphPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2912180213765103811L;
	Image bi;
	private ArrayList<Integer> cpu;
	private ArrayList<Integer> memory;

	public GraphPanel() {
		bi = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
		cpu = new ArrayList<>(251);
		memory = new ArrayList<>(251);
		
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bi, 0, 0, null);
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				OperatingSystemMXBean OSMXBean = ManagementFactory.getOperatingSystemMXBean();
				
			}
		}, 0, 50);
	}

	public void flipConvert(Point p) {
		p.y *= 2.5;
		p.y -= 250;
	}
}

class UsagePage extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9205709836083632466L;
	private JLabel cpu;
	private JLabel memory;
	private JLabel drive;
	private JLabel network;
	private JLabel ping;
	private JLabel graph;
	private JLabel AGraph;

	public UsagePage() {
		// construct components
		cpu = new JLabel("CPU Usage:");
		memory = new JLabel("Memory Usage:");
		drive = new JLabel("Drive Usage:");
		network = new JLabel("Network Activity:");
		ping = new JLabel("Ping: ");
		graph = new JLabel("Graph:");
		AGraph = new JLabel("Graph");

		// adjust size and set layout
		setPreferredSize(new Dimension(261, 398));
		setLayout(null);

		// add components
		add(cpu);
		add(memory);
		add(drive);
		add(network);
		add(ping);
		add(graph);
		add(AGraph);

		// set component bounds (only needed by Absolute Positioning)
		cpu.setBounds(5, 0, 100, 25);
		memory.setBounds(5, 25, 100, 25);
		drive.setBounds(5, 50, 100, 25);
		network.setBounds(5, 75, 100, 25);
		ping.setBounds(5, 100, 100, 25);
		graph.setBounds(5, 125, 100, 25);
		AGraph.setBounds(5, 155, 250, 235);
	}
}