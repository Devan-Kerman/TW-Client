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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import graphics.LongPoint;
import main.App;
import serverclasses.Chunk;
import serverclasses.Tile;

public class WorldView extends JPanel {
	LongPoint tile = new LongPoint(1000, 1000);
	LongPoint selected = new LongPoint(0, 0);

	private static final long serialVersionUID = 8511667415115269257L;
	BufferedImage img;
	int scale = 50;
	Tile[][] array;
	Point old;
	Timer t;
	Random r;
	boolean slowmode = false;

	int cx;
	int cy;

	public WorldView() {
		super();
		requestFocusInWindow();
		r = new Random();
		old = new Point(0, 0);
		array = new Tile[App.CHUNKSIZE * 3][App.CHUNKSIZE * 3];
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		System.out.println("Listener");
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
			if (scale < 4)
				scale = 4;
			System.out.println("Current zoom: " + scale);
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				keymethod(e);
				if (e.getKeyCode() == KeyEvent.VK_G)
					slowmode = !slowmode;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// keymethod(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				keymethod(e);
			}

			boolean toggled = true;

			private void keymethod(KeyEvent e) {
				int key = e.getKeyCode();
				if (toggled) {
					if (key == KeyEvent.VK_KP_LEFT || key == KeyEvent.VK_LEFT) {
						tile.x--;
					} else if (key == KeyEvent.VK_KP_RIGHT || key == KeyEvent.VK_RIGHT) {
						tile.x++;
					} else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
						tile.y--;
					} else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
						tile.y++;
					} else if (key == KeyEvent.VK_PLUS) {
						scale++;
					} else if (key == KeyEvent.VK_MINUS) {
						scale--;
					} else if (key == KeyEvent.VK_0) {
						System.out.println(tile.toString());
					}
					toggled = false;
				}
				toggled = true;
				if (scale < 4)
					scale = 4;
			}
		});
		System.out.println("Borderatering");
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
		System.out.println("Repaint");
		updateArray();
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				if (slowmode) {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					drawImage();
					repaint();
				} else {
					drawImage();
					repaint();
				}
			}
		}, 0, 50);
		Timer timer2 = new Timer(true);
		timer2.schedule(new TimerTask() {
			Point oldp = new Point(0, 0);

			public void run() {
				Point faker = getFakeCP();
				if (faker.x != oldp.x || faker.y != oldp.y) {
					updateArray();
					oldp.setLocation(faker);
				}
			}
		}, 0, 50);
		System.out.println("Finalization");
		setFocusable(true);
		requestFocusInWindow();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	public Point getFakeCP() {
		Point fp = new Point(0, 0);
		if (tile.x < 0)
			fp.x = (int) (tile.x / App.CHUNKSIZE - 1);
		else
			fp.x = (int) (tile.x / App.CHUNKSIZE) + 1;
		if (tile.y < 0)
			fp.y = (int) (tile.y / App.CHUNKSIZE - 1);
		else
			fp.y = (int) (tile.y / App.CHUNKSIZE) + 1;
		return fp;
	}

	Font chunkFont = new Font("Serif", Font.BOLD, 50);
	Font tileFont;

	private void drawImage() {
		tileFont = new Font("Serif", Font.BOLD, scale / 6);
		int ax = (int) (tile.x - cx * App.CHUNKSIZE) + App.CHUNKSIZE;
		int ay = (int) (tile.y - cy * App.CHUNKSIZE) + App.CHUNKSIZE;
		Rectangle rect = this.getBounds();
		BufferedImage img2 = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img2.createGraphics();
		for (int i = 0; i * scale < rect.width && i + ax < array.length && i + ax > 0; i++)
			for (int j = 0; j * scale < rect.height && j + ay < array[0].length && j + ay > 0; j++) {
				g2d.setColor(getColor(array[i + ax][j + ay].elevation));
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

	private void updateArray() {
		Point fp = getFakeCP();
		Chunk[][] cs = App.getChunks(fp.x, fp.y);
		for (int x = 0; x < cs.length; x++)
			for (int y = 0; y < cs.length; y++)
				oneArray(cs[x][y].data, x * 100, y * 100);
		cx = cs[0][0].x;
		cy = cs[0][0].y;
	}

	private void oneArray(Tile[][] c, int sx, int sy) {
		for (int x = 0; x < c.length; x++)
			for (int y = 0; y < c[0].length; y++)
				array[x + sx][y + sy] = c[x][y];
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
		System.out.println("Break");
		return new Color(r.nextInt());
	}
}
