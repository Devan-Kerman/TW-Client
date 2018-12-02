package graphics.menu.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.App;
import serverclasses.Chunk;
import serverclasses.Tile;

public class WorldView extends JPanel {
	long px;
	long py;
	private static final long serialVersionUID = 8511667415115269257L;
	transient BufferedImage img;
	int scale = 10;
	Tile[][] array;
	public WorldView() {
		super();
		System.out.println("Array");
		array = new Tile[App.chunksize*3][App.chunksize*3];
		updateArray();
		System.out.println("Image");
		img = new BufferedImage(array.length * scale, array[0].length * scale, BufferedImage.TYPE_INT_ARGB);
		drawImage(img);
		System.out.println("Listener");
		addMouseWheelListener(e ->  {
				if(scale < 10)
					scale -= e.getWheelRotation()/4.0;
				else if(scale < 30)
					scale -= e.getWheelRotation();
				else
					scale -= e.getWheelRotation()*2;
				repaint();
					if(scale <= 1)
						scale = 1;
		});
		System.out.println("Repaint");
		repaint();
		System.out.println("Finalization");
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	
	
	private void drawImage(BufferedImage imag) {
		Graphics2D g2d = imag.createGraphics();
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				g2d.setColor(getColor(array[i][j].elevation));
				g2d.fillRect((int) (i * scale), (int) (j * scale), (int) scale, (int) scale);
			}
		}
	}
	private Color getColor(int temp) {
		if(temp >=240)
			return new Color(255,255,255);
		else if(temp >=200 && temp < 240)
			return new Color(68 + (int)((temp - 200)* 4.675), 44 + (int)((temp - 200)* 5.275), 0 + (int)((temp - 200)* 6.375));
		else if(temp >=150 && temp < 200)
			return new Color(0 + (int)((temp - 150)* 1.36), 112 + (int)((temp - 150)* 1.36), 3 - (int)((temp - 150)* 0.06));
		else if(temp >=100 && temp < 150)
			return new Color(0 , 221 - (int)((temp - 100)* 2.18), 20 + (int)((temp - 100)* 0.34));
		else if(temp >=20 && temp < 100)
			return new Color(0, 255 - (int)((temp - 20)* 0.05), 6 + (int)((temp - 20)* 0.175));
		else if(temp >=1 && temp < 20)
			return new Color(255 - (int)((temp - 1)* 12.75), 250 + (int)((temp - 1)* 0.25), 0 + (int)((temp - 1)* 0.3));
		else if(temp == 0)
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
	public void updateArray() {
		Chunk[][] cs = App.getChunks(px, py);
		for(int cx = 0; cx < cs.length; cx++)
			for(int cy = 0; cy < cs.length; cy++)
				for(int x = 0; x < App.chunksize; x++)
					for(int y = 0; y < App.chunksize; y++)
						array[cx*App.chunksize+x][cy*App.chunksize+y] = cs[cx][cy].data[x][y];
	}
}
