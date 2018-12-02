package graphics.menu.world;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.App;
import serverclasses.Chunk;
import serverclasses.Tile;

public class WorldView extends JPanel {
	long tx;
	long ty;
	private static final long serialVersionUID = 8511667415115269257L;
	BufferedImage img;
	int scale = 10;
	Tile[][] array;
	Point old;
	Timer t;
	Point drawcoor = new Point(0, 0);

	public WorldView() {
		super();
		old = new Point(0, 0);
		//System.out.println("Array");
		array = new Tile[App.chunksize * 3][App.chunksize * 3];
		//updateArray();
		//System.out.println("Image");
		//drawImage();
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		System.out.println("Listener");
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				/* Useless */}

			public void mouseEntered(MouseEvent e) {
				/* Useless */}

			public void mouseExited(MouseEvent e) {
				/* Useless */}

			public void mousePressed(MouseEvent e) {
				old = e.getPoint();
			}

			public void mouseReleased(MouseEvent e) {
				/* Useless */}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {
				/* Useless */}
			public void mouseDragged(MouseEvent e) {
				int difx = (old.x - e.getX());
				int dify = (old.y - e.getY());
				tx += (long)difx;
				ty += (long)dify;
				drawcoor.x = (int)((tx%App.chunksize)*scale+App.chunksize*scale);
				drawcoor.y = (int)((ty%App.chunksize)*scale+App.chunksize*scale);
				old = e.getPoint();
			}
		});
		this.addMouseWheelListener(e -> {
			scale -= e.getWheelRotation();
			if (scale < 4)
				scale = 4;
		});
		System.out.println("Borderatering");
		setBounds(App.game.gp.ViewPanel.getBounds());
		setPreferredSize(App.game.gp.ViewPanel.getSize());
		setBorder(BorderFactory.createEtchedBorder());
		System.out.println("Repaint");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				//long start = System.currentTimeMillis();
				frame();
				//System.out.printf("Time for repaint: %dms Current Chunk: %d, %d\n",
				//		(System.currentTimeMillis() - start), tx / App.chunksize, ty / App.chunksize);
			}
		}, 0, 50);
		System.out.println("Finalization");
	}
	public synchronized void frame() {
		repaint();
		updateArray();
		drawImage();
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	private void drawImage() {
		Rectangle r = this.getBounds();
		BufferedImage img2 = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = img2.createGraphics();
		for (int i = (int)tx%100; i < r.width/scale+1 + (int)tx%100; i++)
			for (int j = (int)ty%100; j < r.height/scale+1 + (int)ty%100; j++) {
				g2d.setColor(getColor(array[i][j].elevation));
				g2d.fillRect((int) (i * scale), (int) (j * scale), scale, scale);
			}
		img = img2;
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
		else {
			System.out.println("Break");
			return new Color(0);
		}
	}

	private void updateArray() {
		Chunk[][] cs = App.getChunks((int) (tx / App.chunksize), (int) (ty / App.chunksize));
		for (int x = 0; x < cs.length; x++)
			for (int y = 0; y < cs.length; y++)
				oneArray(cs[x][y].data, x * 100, y * 100);
	}

	private void oneArray(Tile[][] c, int sx, int sy) {
		for (int x = 0; x < c.length; x++)
			for (int y = 0; y < c[0].length; y++)
				array[x + sx][y + sy] = c[x][y];
	}
}
