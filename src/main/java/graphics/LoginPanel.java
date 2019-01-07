package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Clientside;
import main.DLogger;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 4233364568392739319L;
	private JLabel welcometext;
	private JTextField nationidinp;
	private JPasswordField passwordlogin;
	private JLabel idlabel;
	private JLabel passwordlabel;
	private JLabel firsttime;
	private JPasswordField passwordregister;
	private JButton confirmation;
	private JCheckBox terms;
	private JTextArea termstext;
	private JCheckBox rules;
	
	boolean login = false;
	boolean register = false;
	private void init() {
		new Thread(() -> {
			Scanner password = null;
			Scanner id = null;
			Scanner rpass = null;
			while (true) {
				password = new Scanner(new String(passwordlogin.getPassword()));
				id = new Scanner(nationidinp.getText());
				rpass = new Scanner(new String(passwordregister.getPassword()));
				login = (id.hasNextInt() && password.hasNext());
				register = (rpass.hasNext() && terms.isSelected() && rules.isSelected());
				confirmation.setEnabled(login || register);
				password.close();
				id.close();
				rpass.close();
			}
		}).start();
		confirmation.addActionListener(event -> {
			confirmation.setEnabled(false);
			if(login) {
				Scanner s = new Scanner(nationidinp.getText());
				int i = s.nextInt();
				if(Clientside.request(new Object[] {5, i, new String(passwordlogin.getPassword())}, Integer.class) == 1) {
					Clientside.game = new CFrame("Tile Wars Client 1.0", i);
					Clientside.frame.dispose();
					Clientside.id = i;
				} else
					confirmation.setForeground(Color.RED);
				s.close();
			} else if(register) {
				System.out.println("AAA");
				int id = Clientside.request(new Object[] {4,new String(passwordregister.getPassword())}, Integer.class);
				System.out.println("AAA");
				Clientside.game = new CFrame("Tile Wars Client 1.0", id);
				System.out.println("AAA");
				Clientside.frame.dispose();
				JOptionPane.showMessageDialog(null, "Your ID is [" + id + "] remember this, keep it somewhere safe!\nIt can also be found on your nation's stats page", "Welcome to Tile-Wars", JOptionPane.INFORMATION_MESSAGE);
				Clientside.id = id;
			}
			confirmation.setEnabled(true);
		});
	}

	public LoginPanel() {
		welcometext = new JLabel("Tile Wars Pre Alpha v. 0.0.1");
		nationidinp = new JTextField(20);
		passwordlogin = new JPasswordField(100);
		idlabel = new JLabel("Nation ID");
		passwordlabel = new JLabel("Password");
		firsttime = new JLabel("First Time?");
		passwordregister = new JPasswordField(5);
		confirmation = new JButton("Submit");
		terms = new JCheckBox("I Agree to the Terms and Services");
		termstext = new JTextArea(5, 5);
		rules = new JCheckBox("I Agree to follow the rules");
		termstext.setEnabled(false);
		setPreferredSize(new Dimension(235, 428));
		setLayout(null);
		add(welcometext);
		add(nationidinp);
		add(passwordlogin);
		add(idlabel);
		add(passwordlabel);
		add(firsttime);
		add(passwordregister);
		add(confirmation);
		add(terms);
		add(termstext);
		add(rules);
		welcometext.setBounds(40, 5, 160, 40);
		nationidinp.setBounds(10, 55, 100, 25);
		passwordlogin.setBounds(10, 90, 100, 25);
		idlabel.setBounds(120, 55, 100, 25);
		passwordlabel.setBounds(120, 90, 100, 25);
		firsttime.setBounds(80, 150, 65, 25);
		passwordregister.setBounds(65, 190, 100, 25);
		confirmation.setBounds(65, 385, 100, 25);
		terms.setBounds(10, 335, 220, 20);
		termstext.setBounds(15, 225, 205, 105);
		rules.setBounds(10, 355, 215, 25);
		termstext.setText(
				"1. I need to write this at some point\n"
				+ "2. Have fun\n"
				+ "4. Learn how to count\n"
				+ "3. No DDOSing\n"
				+ "6. No hax\n"
				+ "5. Teach me how to write a TOC/Rules list\n");
		init();
	}
}