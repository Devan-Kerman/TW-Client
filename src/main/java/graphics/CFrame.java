package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CFrame extends JFrame {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public GamePanel gp;
	public static int id;

	private static final long serialVersionUID = -3103448539603123391L;

	public CFrame(String s, int id) {
		super(s);
		gp = new GamePanel();
		add(gp);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screenSize);
		setID(id);
	}

	public static void setID(int n) {
		id = n;
	}
}
