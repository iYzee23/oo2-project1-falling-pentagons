package gui;

//MODIFIKACIJA

import java.awt.Color;

public abstract class NebeskoTelo extends Objekat {
	
	protected int r;

	public NebeskoTelo(int xx, int yy, Color col, int rr) {
		super(xx, yy, col);
		r = rr;
	}
	
	public boolean preklapa(NebeskoTelo nt) {
		double d = Math.sqrt(Math.pow(x - nt.x, 2) + Math.pow(y - nt.y, 2));
		return r + nt.r > d;
	}

}
