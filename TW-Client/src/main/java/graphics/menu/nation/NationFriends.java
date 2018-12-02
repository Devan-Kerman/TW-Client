package graphics.menu.nation;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.App;

public class NationFriends extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	public NationFriends() {
		super();
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
	}
}
