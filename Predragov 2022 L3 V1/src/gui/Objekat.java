package gui;

//MODIFIKACIJA

import java.awt.Color;
import java.awt.Graphics;

public abstract class Objekat {
	
	protected int x, y;
	protected Color boja;

	public Objekat(int xx, int yy, Color col) {
		x = xx;
		y = yy;
		boja = col;
	}
	
	public int x() {
		return x;
	}
	
	public int y() {
		return y;
	}
	
	public void promeniX(int pom) {
		x += pom;
	}
	
	public void promeniY(int pom) {
		y += pom;
	}
	
	public abstract void crtaj(Graphics g);

}
