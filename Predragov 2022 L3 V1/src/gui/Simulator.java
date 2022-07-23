package gui;

//MODIFIKACIJA

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulator extends Frame {

	private static final long serialVersionUID = 1L;
	private Svemir svemir;
	private Generator gen;
	private Panel komande;
	private Button pokreni;
	private Label poeni;
	
	class Dijalog extends Dialog {

		private static final long serialVersionUID = 1L;

		public Dijalog() {
			super(Simulator.this, "Restart", ModalityType.APPLICATION_MODAL);
			//setLocation(Simulator.this.getX() + Simulator.this.getWidth() / 2, Simulator.this.getY() + Simulator.this.getHeight() / 2);
			setResizable(false);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					dispose();
					if (svemir != null) svemir.prekini();
					if (gen != null) gen.prekini();
					Simulator.this.dispose();
				}
			});
			
			dijPopuniProzor();
			pack();
			setLocation(
					Simulator.this.getX() + Simulator.this.getWidth() / 2 - getWidth() / 2, 
					Simulator.this.getY() + Simulator.this.getHeight() / 2 - getHeight() / 2
			);
			setVisible(true);
		}

		private void dijPopuniProzor() {
			Panel sever = new Panel();
			sever.add(new Label("Pokusajte ponovo?"));
			add(sever, BorderLayout.NORTH);
			
			Panel jug = new Panel();
			Button ok = new Button("OK");
			jug.add(ok);
			add(jug, BorderLayout.CENTER);
			
			ok.addActionListener(ae -> {
				dispose();
				if (svemir != null) svemir.prekini();
				if (gen != null) gen.prekini();
				Simulator.this.dispose();
				new Simulator();
			});
		}
		
	}
	
	public Simulator() {
		setBounds(800, 300, 200, 400);
		setTitle("");
		setResizable(false);
		
		svemir = new Svemir();
		gen = new Generator(svemir);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (svemir != null) svemir.prekini();
				if (gen != null) gen.prekini();
				dispose();
			}
		});
		
		popuniProzor();
		//pack();
		setVisible(true);
	}
	
	public void azurirajLabele() {
		if (svemir.dohvIgraca().dohvPoene() >= 100) svemir.tri = false;
		poeni.setText("" + svemir.dohvIgraca().dohvPoene());
		poeni.revalidate();
	}
	
	void obavesti() {
		if (gen != null) gen.prekini();
		if (svemir != null) svemir.prekini();
		new Dijalog();
	}

	private void popuniProzor() {
		komande = new Panel();
		poeni = new Label("0");
		pokreni = new Button("Pokreni!");
		komande.add(pokreni);
		komande.add(poeni);
		add(komande, BorderLayout.SOUTH);
		add(svemir, BorderLayout.CENTER);
		
		pokreni.addActionListener(ae -> {
			if (svemir != null && gen != null) {
				svemir.kreni();
				gen.kreni();
				svemir.requestFocus();
				pokreni.setEnabled(false);
			}
		});
		
		svemir.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				svemir.pomeriIgraca(e.getKeyCode());
			}
		});
	}
	
	public static void main(String[] args) {
		new Simulator();
	}

}
