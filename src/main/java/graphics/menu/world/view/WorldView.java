package graphics.menu.world.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import main.DLogger;
import server.world.chunk.ChunkManager;
import server.world.tile.Tile;
import server.world.tile.TileEntity;

public class WorldView extends JPanel {
	private static final long serialVersionUID = 1L;
	public static long tx, ty; // Tile based location
	public static int sx, sy; // Selected tile location
	private Point old = new Point();
	public int scale = 5;
	private Executor exec = Executors.newSingleThreadExecutor();
	private Rectangle resolution = this.getBounds();
	public WorldView() {
		super(true);
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				/* Useless */}

			public void mouseEntered(MouseEvent e) {
				requestFocusInWindow();
				/* Useless */}

			public void mouseExited(MouseEvent e) {
				/* Useless */}

			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				old = p;
				sx = p.x / scale;
				sy = p.y / scale;
			}

			public void mouseReleased(MouseEvent e) {
				/* Useless */}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				/* Useless */}

			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				tx += (long) (old.x - p.x);
				ty += (long) (old.y - p.y);
				old = e.getPoint();
			}
		});
		addMouseWheelListener(e -> {
			scale -= e.getWheelRotation();
			if (scale < 20)
				scale = 20;
			DLogger.info("Scale increased: " + scale);
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keymethod(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// keymethod(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				keymethod(e);
			}

			public void keymethod(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
					tx--;
				} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
					tx++;
				} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
					ty--;
				} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
					ty++;
				} else if (key == KeyEvent.VK_EQUALS) {
					scale++;
					if (scale < 20)
						scale = 20;
				} else if (key == KeyEvent.VK_MINUS) {
					scale--;
				}
				if (scale < 4)
					scale = 4;
			}
		});
		setFocusable(true);
		requestFocusInWindow();
		exec.execute(() -> {
			while(true) {
				resolution = this.getBounds();
				repaint();
			}
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Number of tiles to show
		int tlx = resolution.width/scale;
		int tly = resolution.height/scale;
		
		for(int x = 0; x < tlx; x++)
			for(int y = 0; y < tly; y++) {
				g.setColor(getColor(get(tx+x, ty+y).elevation));
				g.fillRect(x*scale, y*scale, scale, scale);
			}
		g.setColor(Color.WHITE);
		g.drawRect(sx*scale, sy*scale, scale, scale);
		//super.paintComponent(g);
	}
	
	public static Tile get(long x, long y) {
		byte mx = (byte) (x % 100), my = (byte) (y % 100);
		return ChunkManager.safeChunk((int) (Math.floorDiv(x, 100l)), (int) (Math.floorDiv(y, 100l)))
				.get((byte) (mx < 0 ? mx + 100 : mx), (byte) (my < 0 ? my + 100 : my));
	}
	
	public static TileEntity getTileEntity(long x, long y) {
		byte mx = (byte) (x % 100), my = (byte) (y % 100);
		return ChunkManager.safeChunk((int) (Math.floorDiv(x, 100l)), (int) (Math.floorDiv(y, 100l))).tileEnts
				.at(mx < 0 ? mx + 100 : mx, my < 0 ? my + 100 : my);
	}
	
	private static Color getColor(int temp) {
		if (temp >= 240)
			return new Color(255, 255, 255);
		else if (temp >= 200 && temp < 240)
			return new Color(68 + (int) ((temp - 200) * 4.675), 44 + (int) ((temp - 200) * 5.275),
					0 + (int) ((temp - 200) * 6.375));
		else if (temp >= 150 && temp < 200)
			return new Color(0 + (int) ((temp - 150) * 1.36), 112 + (int) ((temp - 150) * 1.36),
					3 - (int) ((temp - 150) * 0.06));
		else if (temp >= 100 && temp < 150)
			return new Color(0, 221 - (int) ((temp - 100) * 2.18), 20 + (int) ((temp - 100) * 0.34));
		else if (temp >= 20 && temp < 100)
			return new Color(0, 255 - (int) ((temp - 20) * 0.05), 6 + (int) ((temp - 20) * 0.175));
		else if (temp >= 1 && temp < 20)
			return new Color(255 - (int) ((temp - 1) * 12.75), 250 + (int) ((temp - 1) * 0.25),
					0 + (int) ((temp - 1) * 0.3));
		else if (temp == 0)
			return new Color(0, 242, 255);
		else if (temp <= -1 && temp > -20)
			return new Color(0, 199 + (int) ((temp + 1) * 2.55), 255);
		else if (temp <= -20 && temp > -100)
			return new Color(0, 148 + (int) ((temp + 20) * 0.5), 255 + (int) ((temp + 20) * 1.38));
		else if (temp <= -100 && temp > -150)
			return new Color(0, 108 + (int) ((temp + 100) * 0.92), 186 + (int) ((temp + 100) * 0.06));
		else if (temp <= -150 && temp > -200)
			return new Color(0, 62 + (int) ((temp + 150) * 1.14), 183 + (int) ((temp + 150) * 1.28));
		else if (temp <= -200 && temp > -240)
			return new Color(0, 5 + (int) ((temp + 200) * 0.125), 119 + (int) ((temp + 200) * 2.975));
		else if (temp <= -240)
			return new Color(0, 0, 0);
		DLogger.error("Invalid Elevation! " + temp);
		return Color.BLACK;
	}
}
