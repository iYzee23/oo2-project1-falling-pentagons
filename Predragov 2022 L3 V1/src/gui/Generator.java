package gui;

//MODIFIKACIJA

public class Generator extends Thread {

	private Svemir svemir;
	private boolean radi;
	
	public Generator(Svemir s) {
		svemir = s;
		start();
	}
	
	@Override
	public void run() {
		try {
			while (!isInterrupted()) {
				synchronized (this) {
					while (!radi) wait();
				}
				Thread.sleep(900);
				int x = (int)(Math.random() * 201);
				int r = (int)(10 + Math.random() * 21);
				boolean ind = false;
				while (!ind) {
					Kometa k = new Kometa(x, 0, r);
					if (svemir.preklapa(k)) continue;
					svemir.dodajTelo(k);
					ind = true;
				}
			}
		} catch (InterruptedException e) {
			//bez efekta
		}
	}
	
	public synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	protected synchronized void zaustavi() {
		radi = false;
		notifyAll();
	}
	
	public void prekini() {
		interrupt();
	}

}
