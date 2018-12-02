package graphics;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class CFrame extends JFrame {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/**
	 * 
	 */
	private static final long serialVersionUID = -3103448539603123391L;
	
	public CFrame(String s) {
		super(s);
		add(new GamePanel());
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(screenSize);
	}

}
