package graphics.menu.world;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import graphics.LongPoint;
import graphics.utils.Chunks;
import main.DLogger;
import server.nation.TilePoint;
import server.world.chunk.Chunk;
import server.world.tile.Tile;

public class WorldView extends JPanel {
	public static LongPoint tile = new LongPoint(0, 0);
	public static LongPoint selected = new LongPoint(0, 0);

	private static final long serialVersionUID = 8511667415115269257L;
	BufferedImage img;
	int scale = 50;
	static Chunks chunks = new Chunks();
	Point old;
	public static int renderdistance = 1;

	public static int cx;
	public static int cy;

	public WorldView() {
		super();
		old = new Point(0, 0);
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
				selected.x = tile.x + p.x / scale;
				selected.y = tile.y + p.y / scale;

			}

			public void mouseReleased(MouseEvent e) {
				/* Useless */}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				/* Useless */}

			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				tile.x += (long) (old.x - p.x);
				tile.y += (long) (old.y - p.y);
				selected.x = tile.x + p.x / scale;
				selected.y = tile.y + p.y / scale;
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

			private void keymethod(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
					tile.x--;
				} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
					tile.x++;
				} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
					tile.y--;
				} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
					tile.y++;
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
		updateArray();
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				drawImage();
				repaint();
			}
		}, 0, 50);
		Timer timer2 = new Timer(true);
		timer2.schedule(new TimerTask() {
			Point oldp = new Point(0, 0);

			public void run() {
				Point faker = getFakeCP();
				if (faker.x != oldp.x || faker.y != oldp.y)
					updateArray();

				/*TilePoint tp = getTilePoint(faker);
				Clientside.getUpdates().forEach(action -> {
					if (action.point.chunk.distance(faker) <= Math.sqrt(2)) {
						Point diff = getDifference(tp, action.point);
						System.out.println("gotem");
						array[diff.x][diff.y] = action.tile;
					}
				});*/
				
				oldp.setLocation(faker);
			}
		}, 0, 50);
		setFocusable(true);
		requestFocusInWindow();
		DLogger.relief("World View Initialized!");
	}

	public Point getDifference(TilePoint a, TilePoint b) {
		return TilePoint.diff(a, b);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	public static Point getFakeCP() {
		Point fp = new Point(0, 0);
		if (tile.x < 0)
			fp.x = (int) (tile.x / Chunk.CHUNKSIZE - 1);
		else
			fp.x = (int) (tile.x / Chunk.CHUNKSIZE) + 1;
		if (tile.y < 0)
			fp.y = (int) (tile.y / Chunk.CHUNKSIZE - 1);
		else
			fp.y = (int) (tile.y / Chunk.CHUNKSIZE) + 1;
		return fp;
	}

	public static TilePoint getTilePoint(Point fake) {
		return new TilePoint(fake, (byte) (tile.x % 100), (byte) (tile.y % 100));
	}

	Font chunkFont = new Font("Serif", Font.PLAIN, 50);
	Font tileFont;

	private Point getArrayPoint() {
		return new Point((int) (tile.x - cx * Chunk.CHUNKSIZE) + Chunk.CHUNKSIZE,
				(int) (tile.y - cy * Chunk.CHUNKSIZE) + Chunk.CHUNKSIZE);
	}

	private void drawImage() {
		tileFont = new Font("Serif", Font.PLAIN, scale / 6);
		Point a = getArrayPoint();
		Rectangle rect = this.getBounds();
		BufferedImage img2 = new BufferedImage(rect.width + 1, rect.height + 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img2.createGraphics();
		for (int i = 0; i * scale < rect.width && i + a.x < chunks.len() && i + a.x > 0; i++)
			for (int j = 0; j * scale < rect.height && j + a.y < chunks.len() && j + a.y > 0; j++) {
				g2d.setColor(getColor(chunks.get(i + a.x, j + a.y).elevation));
				g2d.fillRect((int) (i * scale), (int) (j * scale), scale, scale);
				if (selected.x == i + tile.x && selected.y == j + tile.y) {
					g2d.setColor(new Color(255, 100, 100, 100));
					g2d.fillRect((int) (i * scale), (int) (j * scale), scale, scale);
				}

			}
		g2d.setFont(chunkFont);
		g2d.setColor(Color.black);
		g2d.drawString(tile.toString(), 50, 50);
		img = img2;
	}

	public static Tile getSelected() {
		try {
			return chunks.get((int) (selected.x - tile.x), (int) (selected.y - tile.y));
		} catch (Exception e) {
			return new Tile();
		}
	}

	private static void updateArray() {
		Point fp = getFakeCP();
		chunks.update(fp.x, fp.y);
		DLogger.info("Grabbed chunks");
		cx = chunks.getCH(0, 0).cx;
		cy = chunks.getCH(0, 0).cy;
		
	}

	private Color getColor(int temp) {
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
