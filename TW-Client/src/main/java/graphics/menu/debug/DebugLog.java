package graphics.menu.debug;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.App;

public class DebugLog extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	public DebugLog() {
		super();
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
	}
}
