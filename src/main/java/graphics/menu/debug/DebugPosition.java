package graphics.menu.debug;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.menu.world.view.WorldView;
import main.DLogger;
import server.world.chunk.ChunkManager;

public class DebugPosition extends JPanel {
	private static final long serialVersionUID = 8511667415115269257L;

	private JLabel selected;
	private JLabel chunk;
	private JLabel tile;
	private JLabel downloaded;
	private JLabel tiledebug;

	public DebugPosition() {
		super();
		Font f = new Font(Font.SERIF, Font.BOLD, 25);
		selected = new JLabel("Selected Position");
		selected.setFont(f);
		chunk = new JLabel("Chunk Position");
		chunk.setFont(f);
		tile = new JLabel("Tile Position");
		tile.setFont(f);
		downloaded = new JLabel("Array Position");
		downloaded.setFont(f);
		tiledebug = new JLabel("Tile Debuger");
		tiledebug.setFont(f);
		

		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					selected.setText(String.format("Selected Position: [%d, %d]", WorldView.sx, WorldView.sy));
					chunk.setText(String.format("Chunk Position: [%d, %d]", Math.floorDiv(WorldView.tx, 100l), Math.floorDiv(WorldView.ty, 100l)));
					tile.setText(String.format("Tile Position: [%d, %d]", WorldView.tx, WorldView.ty));
					downloaded.setText(String.format("Cached chunks: %d", ChunkManager.size()));
					tiledebug.setText(String.format("null: %d", 0));
					
					selected.repaint();
					chunk.repaint();
					tile.repaint();
					downloaded.repaint();
					tiledebug.repaint();
				} catch(Exception e) {
					DLogger.warn(e.getMessage());
					/*Prevent Null Pointers*/
				}
			}

		}, 0, 50);

		add(selected);
		add(chunk);
		add(tile);
		add(downloaded);
		add(tiledebug);
	}
}
