package pqt.ConJuego;

import java.awt.EventQueue;

public class MainCon {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				  MainCon frame = new MainCon();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainCon() {
		ConInicio miInicio;
		miInicio = new ConInicio();
		miInicio.setVisible(true);
	}
	
}