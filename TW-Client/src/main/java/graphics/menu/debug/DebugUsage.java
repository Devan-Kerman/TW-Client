package graphics.menu.debug;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.ClientSide;


public class DebugUsage extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DebugUsage() {
		super();
		UsagePage up = new UsagePage();
		setLayout(null);
		add(up);
		up.setBounds(screenSize.width / 4, 16, screenSize.width / 2, (int) (screenSize.height / 1.5));
		up.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.CYAN));
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
	private GraphPanel AGraph;
	
	public UsagePage() {
		cpu = new JLabel("CPU Usage:");
		cpu.setForeground(Color.WHITE);
		memory = new JLabel("Memory Usage:");
		memory.setForeground(Color.WHITE);
		drive = new JLabel("Drive Usage:");
		drive.setForeground(Color.WHITE);
		network = new JLabel("Network Activity:");
		network.setForeground(Color.WHITE);
		ping = new JLabel("Ping: ");
		ping.setForeground(Color.WHITE);
		graph = new JLabel("Graph:");
		graph.setForeground(Color.WHITE);
		AGraph = new GraphPanel();
		Timer t = new Timer();
		OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
		Runtime r = Runtime.getRuntime();
		File f = new File("/");
		t.schedule(new TimerTask() {
			public void run() {
				cpu.setText(String.format("CPU Usage: %3.3f", osmxb.getSystemLoadAverage()*100));
				memory.setText(String.format("Memory Usage: %3.3f",(((double)(r.totalMemory() -r.freeMemory()))/r.totalMemory())*100));
				drive.setText(String.format("Drive Usage:  %3.3f",(((double)f.getUsableSpace())/f.getTotalSpace())*100));
				network.setText("Network Activity: " + (ClientSide.s.isClosed()?"Inactive":"Active"));
				//ping.setText("Ping(ms): " + App.ping());
			}
		}, 0, 600);
		setPreferredSize(new Dimension(300, 400));
		setLayout(null);
		add(cpu);
		add(memory);
		add(drive);
		add(network);
		add(ping);
		add(graph);
		add(AGraph);
		cpu.setBounds(5, 0, 250, 50);
		memory.setBounds(5, 25, 250, 50);
		drive.setBounds(5, 50, 250, 50);
		network.setBounds(5, 75, 250, 50);
		ping.setBounds(5, 100, 250, 50);
		graph.setBounds(5, 125, 250, 50);
		AGraph.setBounds(5, 160, 250, 250);
		setBackground(Color.BLACK);
	}
}
class GraphPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2912180213765103811L;
	private BufferedImage bi;
	private ArrayList<Integer> memory;
	private Runtime rt;

	public GraphPanel() {
		bi = new BufferedImage(250, 250, BufferedImage.TYPE_INT_ARGB);
		memory = new ArrayList<>(51);
		rt = Runtime.getRuntime();
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				int r1 = (int) (((double) (rt.totalMemory() - rt.freeMemory())) / (rt.totalMemory()) * 250);
				try {Thread.sleep(600);} catch (InterruptedException e) {e.printStackTrace();}
				int r2 = (int) (((double) (rt.totalMemory() - rt.freeMemory())) / (rt.totalMemory()) * 250);
				try {Thread.sleep(600);} catch (InterruptedException e) {e.printStackTrace();}
				int r3 = (int) (((double) (rt.totalMemory() - rt.freeMemory())) / (rt.totalMemory()) * 250);
				try {Thread.sleep(600);} catch (InterruptedException e) {e.printStackTrace();}
				int r4 = (int) (((double) (rt.totalMemory() - rt.freeMemory())) / (rt.totalMemory()) * 250);
				memory.add((r1+r2+r3+r4)/4);
				if(memory.size() > 50)
					memory.remove(0);
				writeGraph();
				repaint();
			}
		}, 0, 600);
		setPreferredSize(new Dimension(250, 250));
		setLayout(null);
		setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.CYAN));
	}

	Point fir = new Point();
	Point sec = new Point();

	public void writeGraph() {
		Graphics2D g = bi.createGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 250, 250);
		g.translate(0, 250);
		for (int x = 0; x < memory.size() - 1; x++) {
			Integer i = memory.get(x);
			Integer in = memory.get(x + 1);
			g.setColor(getLevel(in));
			fir.setLocation(x * 5, -i);
			sec.setLocation((x + 1) * 5, -in);
			g.drawLine(fir.x, fir.y, sec.x, sec.y);
		}
	}
	public Color getLevel(int r) {
		if(r < 100)
			return Color.GREEN;
		else if(r < 150)
			return Color.YELLOW;
		else if(r < 200)
			return Color.RED;
		else
			return Color.BLACK;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(bi, 0, 0, null);
	}
}

