package graphics.menu.nation;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.App;

public class NationView extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	public NationView() {
		super();
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
	}
}
