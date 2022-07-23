package gui;

//MODIFIKACIJA

import java.awt.Graphics;

public class TriMetak extends Metak {
	
	private Metak[] cime = new Metak[3];
	int counter;

	public TriMetak(int xx, int yy) {
		super(xx, yy);
		for (int i = 0; i < 3; i++) cime[i] = new Metak(xx, yy);
	}

	@Override
	public void promeniY(int pom) {
		for (int i = 0; i < 3; i++) {
			if (cime[i] == null) continue;
			if (i == 0) cime[i].x -= pom / 5;
			cime[i].y += pom;
			if (i == 2) cime[i].x += pom / 5;
		}
	}
	
	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		for (int i = 0; i < 3; i++) {
			if (cime[i] == null) continue;
			g.fillOval(cime[i].x - r/2, cime[i].y - r/2, r, r);
		}
	}

	@Override
	public boolean preklapa(NebeskoTelo nt) {
		for (int i = 0; i < 3; i++) {
			if (cime[i] == null) continue;
			if (cime[i].preklapa(nt)) {
				cime[i] = null;
				counter++;
				return true;
			}
		}
		return false;
	}
	
}