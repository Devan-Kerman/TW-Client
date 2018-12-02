package graphics.menu.debug;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.App;

public class DebugErrors extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;
	public DebugErrors() {
		super();
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
	}
}
