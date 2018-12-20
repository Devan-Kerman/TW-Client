package graphics.menu.debug;

import java.awt.Font;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import graphics.menu.world.WorldView;
import main.App;
import serverclasses.Tile;

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
			Point p = new Point(0, 0);
			@Override
			public void run() {
				try {
					selected.setText(String.format("Selected Position: %s", WorldView.selected));
					p.setLocation(WorldView.getFakeCP());
					chunk.setText(String.format("Draw Chunk Position: [%d, %d]", p.x, p.y));
					tile.setText(String.format("Tile Position: %s", WorldView.tile));
					downloaded.setText(String.format("Downloaded Chunk Position: [%d, %d]", WorldView.cx, WorldView.cy));
					Tile t = WorldView.getSelected();
					tiledebug.setText(String.format("Elevation: %d", t.elevation));
					
					selected.repaint();
					chunk.repaint();
					tile.repaint();
					downloaded.repaint();
					tiledebug.repaint();
				} catch(Exception e) {
					App.logger.warn(e.getMessage());
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
