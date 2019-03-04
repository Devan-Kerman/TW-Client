package graphics.menu.world.view;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import main.Clientside;
import server.api.area.BPoint;
import server.world.chunk.ChunkManager;
import server.world.tile.Tile;
import server.world.tile.TileEntity;
import util.math.Locations;

public class PopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PopupMenu(long tx, long ty) {
		Tile t = WorldView.get(tx, ty);
		if(t.ownerid == 0) {
			JMenuItem item = new JMenuItem("Claim!");
			item.addActionListener(e -> {
				boolean b = Clientside.claim(tx, ty);
				if(b) {
					BPoint point = Locations.getArray(tx, ty);
					ChunkManager.safeChunk(Locations.getChunk(tx, ty)).tiles[point.x][point.y].ownerid = Clientside.id;
				} else
					JOptionPane.showMessageDialog(null, "Failed to claim!", "Unable to claim", JOptionPane.CANCEL_OPTION);
			});
			add(item);
		} else if(t.ownerid != Clientside.id){
			JMenuItem item = new JMenuItem("Invade!");
			item.addActionListener(e -> {
				// TODO Invasion networking & GUI
			});
			add(item);
		} else {
			TileEntity ent = WorldView.getTileEntity(tx, ty);
			if(ent == null) {
				JMenuItem item = new JMenuItem("Build!");
				item.addActionListener(e -> {
					// TODO Build improvement networking & GUI
				});
				add(item);
			} else {
				JMenuItem item = new JMenuItem();
				item.addActionListener(e -> {
					// TODO Tile Entity GUI
				});
			}
		}
		
	}
	
	
}
