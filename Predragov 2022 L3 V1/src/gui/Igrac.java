package gui;

//MODIFIKACIJA

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends NebeskoTelo {

	private int[] xs;
	private int[] ys;
	private int poeni;
	
	public Igrac(int xx, int yy) {
		super(xx, yy, Color.red, 10);
		xs = new int[3];
		ys = new int[3];
	}

	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		for (int i = 0; i < 3; i++) {
			double alfa = (90 + i * 120) * Math.PI / 180;
			xs[i] = x + (int) (r * Math.cos(alfa));
			ys[i] = y - (int) (r * Math.sin(alfa));
		}
		g.fillPolygon(xs, ys, 3);
		//g.fillOval(x-r, y-r, 2*r, 2*r);
	}
	
	public void promeniPoene(int p) {
		poeni += p;
	}
	
	public int dohvPoene() {
		return poeni;
	}

}
