package gui;

//MODIFIKACIJA

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Kometa extends NebeskoTelo {

	private double ang;
	private Polygon poli;
	
	public Kometa(int xx, int yy, int rr) {
		super(xx, yy, Color.gray, rr);
		ang = Math.random() * 2 * Math.PI;
		int[] xs = new int[5];
		int[] ys = new int[5];
		double tx = r * Math.cos(ang);
		double ty = r * Math.sin(ang);
		for (int i = 0; i < 5; i++) {
			xs[i] = (int)tx;
			ys[i] = (int)ty;
			ang += 72 * Math.PI / 180;
			tx = r * Math.cos(ang);
			ty = r * Math.sin(ang);
		}
		poli = new Polygon(xs, ys, 5);
	}

	@Override
	public void crtaj(Graphics g) {
		//Color col = g.getColor();
		g.setColor(boja);
		g.translate(x, y);
		g.fillPolygon(poli);
		g.translate(-x, -y);
		//g.setColor(col);
	}

}
