package gui;

//MODIFIKACIJA

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Svemir extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private ArrayList<NebeskoTelo> tela;
	private ArrayList<Metak> meci;
	Zele zele;
	private Igrac igrac;
	private boolean radi;
	private Thread nit;
	boolean tri = true;
	boolean zel;

	public Svemir() {
		this.setBackground(Color.black);
		tela = new ArrayList<NebeskoTelo>();
		meci = new ArrayList<Metak>();
		zele = new Zele(this);
		igrac = new Igrac(90, 300);
		nit = new Thread(this);
		nit.start();
	}
	
	public synchronized Svemir dodajTelo(NebeskoTelo t) {
		tela.add(t);
		return this;
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		for (Metak m: meci) m.crtaj(g);
		for (NebeskoTelo t: tela) t.crtaj(g);
		if (zel) zele.crtaj(g);
		igrac.crtaj(g);
	}
	

	@Override
	public void run() {
		try {
			while (!nit.isInterrupted()) {
				synchronized (this) {
					while (!radi) wait();
				}
				Thread.sleep(109);
				synchronized (this) {
					repaint();
					for (NebeskoTelo t: tela) {
						t.promeniY(5);
						if (igrac.preklapa(t)) {
							//prekini();
							((Simulator)getParent()).obavesti();
							tela.clear();
							meci.clear();
							zele.prekini();
							repaint();
							break;
						}
					}
					boolean ind = false;
					while (!ind) {
						ind = true;
						spljM: for (Metak m: meci) {
							for (NebeskoTelo t: tela) {
								if (m.preklapa(t)) {
									ind = false;
									tela.remove(t);
									if (!tri) meci.remove(m);
									if (tri && ((TriMetak)m).counter == 3) meci.remove(m);
									igrac.promeniPoene(10);
									((Simulator)getParent()).azurirajLabele();
									break spljM;
								}
							}
							m.promeniY(-5);
						}
					}
					for (NebeskoTelo t: tela) {
						if (t.y - t.r > this.getHeight()) {
							tela.remove(t);
							igrac.promeniPoene(-5);
							((Simulator)getParent()).azurirajLabele();
							//System.out.println("Unisten");
							break;
						}
					}
					for (Metak m: meci) {
						if (m.y + m.r < 0) {
							meci.remove(m);
							break;
						}
					}
					if (zel) {
						if (zele.preklapa(igrac)) {
							igrac.promeniPoene(50);
							((Simulator)getParent()).azurirajLabele();
							zele.kreni();
						}
						for (NebeskoTelo nt: tela) {
							if (zele.preklapa(nt)) {
								zele.kreni();
								break;
							}
						}
					}
				}
			}
		} catch (InterruptedException e) {
			//bez efekta
		}
		synchronized (this) {
			nit = null;
			notifyAll();
		}
	}
	
	public synchronized boolean preklapa(NebeskoTelo t) {
		for (NebeskoTelo nt: tela) 
			if (nt.preklapa(t)) 
				return true;
		return false;
	}
	
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	protected synchronized void zaustavi() {
		radi = false;
		notifyAll();
	}
	
	protected synchronized void finish() {
		if (nit != null) nit.interrupt();
		while (nit != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				//bez efekta
			}
		}
	}
	
	public void prekini() {
		if (nit != null) nit.interrupt();
	}
	
	public synchronized void pomeriIgraca(int opcija) {
		switch(opcija) {
			case KeyEvent.VK_LEFT:
				igrac.promeniX(-3);
				break;
			case KeyEvent.VK_RIGHT:
				igrac.promeniX(3);
				break;
			case KeyEvent.VK_UP:
				igrac.promeniY(-3);
				break;
			case KeyEvent.VK_DOWN:
				igrac.promeniY(3);
				break;
			case KeyEvent.VK_SPACE:
				napraviMetak();
				break;
		}
	}

	private void napraviMetak() {
		Metak m = null;
		if (!tri) m = new Metak(igrac.x, igrac.y - igrac.r);
		else m = new TriMetak(igrac.x, igrac.y - igrac.r);
		meci.add(m);
	}
	
	public Igrac dohvIgraca() {
		return igrac;
	}

}
