package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import serverclasses.Tile;

public class VPanel extends JPanel {
	private static final long serialVersionUID = 1064812704658251024L;
	private int scale = 10;
	private final Tile[][] array;
	BufferedImage img;

	public VPanel(Tile[][] arr) {
		array = arr;
		img = new BufferedImage(arr.length * scale, arr[0].length * scale, BufferedImage.TYPE_INT_ARGB);
		drawImage(img);
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (scale < 10)
					scale -= e.getWheelRotation() / 4.0;
				else if (scale < 30)
					scale -= e.getWheelRotation();
				else
					scale -= e.getWheelRotation() * 2;
				repaint();
				if (scale <= 1)
					scale = 1;
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.drawImage(img, 0, 0, (int) Math.round(array.length * scale), (int) Math.round(array[0].length * scale),
				null);
	}

	public void Export(File f) throws IOException {
		drawImage(img);
		ImageIO.write(img, "png", f);
	}

	private void drawImage(BufferedImage imag) {
		Graphics2D g2d = imag.createGraphics();

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				int temp = array[i][j].elevation;
				if (temp >= 240)
					g2d.setColor(new Color(255, 255, 255));
				else if (temp >= 200 && temp < 240)
					g2d.setColor(new Color(68 + (int) ((temp - 200) * 4.675), 44 + (int) ((temp - 200) * 5.275),
							0 + (int) ((temp - 200) * 6.375)));
				else if (temp >= 150 && temp < 200)
					g2d.setColor(new Color(0 + (int) ((temp - 150) * 1.36), 112 + (int) ((temp - 150) * 1.36),
							3 - (int) ((temp - 150) * 0.06)));
				else if (temp >= 100 && temp < 150)
					g2d.setColor(new Color(0, 221 - (int) ((temp - 100) * 2.18), 20 + (int) ((temp - 100) * 0.34)));
				else if (temp >= 20 && temp < 100)
					g2d.setColor(new Color(0, 255 - (int) ((temp - 20) * 0.05), 6 + (int) ((temp - 20) * 0.175)));
				else if (temp >= 1 && temp < 20)
					g2d.setColor(new Color(255 - (int) ((temp - 1) * 12.75), 250 + (int) ((temp - 1) * 0.25),
							0 + (int) ((temp - 1) * 0.3)));
				else if (temp == 0)
					g2d.setColor(new Color(0, 242, 255));
				else if (temp <= -1 && temp > -20)
					g2d.setColor(new Color(0, 199 + (int) ((temp + 1) * 2.55), 255));
				else if (temp <= -20 && temp > -100)
					g2d.setColor(new Color(0, 148 + (int) ((temp + 20) * 0.5), 255 + (int) ((temp + 20) * 1.38)));
				else if (temp <= -100 && temp > -150)
					g2d.setColor(new Color(0, 108 + (int) ((temp + 100) * 0.92), 186 + (int) ((temp + 100) * 0.06)));
				else if (temp <= -150 && temp > -200)
					g2d.setColor(new Color(0, 62 + (int) ((temp + 150) * 1.14), 183 + (int) ((temp + 150) * 1.28)));
				else if (temp <= -200 && temp > -240)
					g2d.setColor(new Color(0, 5 + (int) ((temp + 200) * 0.125), 119 + (int) ((temp + 200) * 2.975)));
				else if (temp <= -240)
					g2d.setColor(new Color(0, 0, 0));
				g2d.fillRect((int) (i * scale), (int) (j * scale), (int) scale, (int) scale);
				g2d.setColor(new Color(0, 0, 0, 100));
				// g2d.drawString(array[i][j].elevation+"", i*scale, j*scale);
			}
		}
	}
}
