package gui;

//MODIFIKACIJA

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Zele extends NebeskoTelo implements Runnable {

	private Polygon poli;
	private boolean radi = true;
	private Thread nit;
	private Svemir svemir;
	
	public Zele(Svemir s) {
		super(0, 0, Color.cyan, 35);
		svemir = s;
		x = (int)(r/2 + Math.random() * (200 - r));
		y = (int)(r/2 + Math.random() * (400 - r));
		int[] xs = new int[4];
		int[] ys = new int[4];
		xs[0] = 0;	ys[0] = -r/2;
		xs[1] = -r/2;	ys[1] = 0;
		xs[2] = 0;	ys[2] = r/2;
		xs[3] = r/2;	ys[3] = 0;
		poli = new Polygon(xs, ys, 4);
		nit = new Thread(this);
		nit.start();
	}

	@Override
	public void crtaj(Graphics g) {
		g.setColor(boja);
		g.translate(x, y);
		g.fillPolygon(poli);
		//g.fillOval(0, 0, r, r);
		g.translate(-x, -y);
	}

	@Override
	public void run() {
		try {
			while (!nit.isInterrupted()) {
				synchronized (this) {
					while (!radi) wait();
				}
				svemir.zel = false;
				Thread.sleep(1000);
				x = (int)(r/2 + Math.random() * (200 - r));
				y = (int)(r/2 + Math.random() * (400 - r));
				System.out.println("generiso");
				svemir.zel = true;
				radi = false;
			}
		} catch (InterruptedException e) {
			// bez efekta
		}
		synchronized (this) {
			nit = null;
			notifyAll();
		}
	}
	
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	public synchronized void zaustavi() {
		radi = false;
		notifyAll();
	}
	
	protected synchronized void finish() {
		if (nit != null) nit.interrupt();
		while (nit != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// bez efekta
			}
		}
	}
	
	public void prekini() {
		if (nit != null) nit.interrupt();
	}

}
