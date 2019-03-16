package graphics;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.Clientside;

/*
 * TODO add this.
 * termstext.setText(
				"1. I need to write this at some point\n"
				+ "2. Have fun\n"
				+ "4. Learn how to count\n"
				+ "3. No DDOSing\n"
				+ "6. No hax\n"
				+ "5. Teach me how to write a TOC/Rules list\n");
				*/
public class FirstPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel title;
	private JLabel userlbl;
	private JLabel passlbl;
	private JTextField userin;
	private JPasswordField passin;
	private JButton regbutt;
	private JButton logbutt;

	private void init() {
		regbutt.addActionListener(l -> {
			String user = userin.getText();
			String pass = new String(passin.getPassword());
			int resp = Clientside.register(user, pass);
			if(resp == 0) {
				JOptionPane.showMessageDialog(null, "Invalid registration!");
			} else {
				Clientside.mainframe = new CFrame("Tile Wars Client 1.0", resp);
				Clientside.login.dispose();
				JOptionPane.showMessageDialog(null, "Your ID is [" + resp + "] remember this, keep it somewhere safe!\nIt can also be found on your nation's stats page", "Welcome to Tile-Wars", JOptionPane.INFORMATION_MESSAGE);
				Clientside.id = resp;
			}
		});
		logbutt.addActionListener(l -> {
			String user = userin.getText();
			String pass = new String(passin.getPassword());
			int resp = Clientside.login(user, pass);
			if(resp == 0) {
				JOptionPane.showMessageDialog(null, "Invalid login!");
			} else {
				Clientside.mainframe = new CFrame("Tile Wars Client 1.0", resp);
				Clientside.login.dispose();
				Clientside.id = resp;
			}
		});
	}

	public FirstPanel() {
		// construct components
		title = new JLabel("Tile wars client v2.0.0 \"Epic version\"");
		userlbl = new JLabel("Username");
		passlbl = new JLabel("Password");
		userin = new JTextField(5);
		passin = new JPasswordField(5);
		regbutt = new JButton("Register");
		logbutt = new JButton("Login");

		// adjust size and set layout
		setPreferredSize(new Dimension(230, 170));
		setLayout(null);

		// add components
		add(title);
		add(userlbl);
		add(passlbl);
		add(userin);
		add(passin);
		add(regbutt);
		add(logbutt);

		// set component bounds (only needed by Absolute Positioning)
		title.setBounds(10, 0, 210, 35);
		userlbl.setBounds(10, 50, 100, 25);
		passlbl.setBounds(10, 75, 100, 25);
		userin.setBounds(80, 50, 100, 25);
		passin.setBounds(80, 75, 100, 25);
		regbutt.setBounds(10, 115, 100, 25);
		logbutt.setBounds(120, 115, 100, 25);
		init();
	}
}
