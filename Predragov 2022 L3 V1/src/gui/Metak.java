package gui;

//MODIFIKACIJA

import java.awt.Color;
import java.awt.Graphics;

public class Metak extends NebeskoTelo {

	public Metak(int xx, int yy) {
		super(xx, yy, Color.green, 5);
	}

	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		g.fillRect(x - r / 2, y - r, r, 2 * r);
	}

}
