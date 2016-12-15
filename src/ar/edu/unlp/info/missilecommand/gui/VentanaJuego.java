package ar.edu.unlp.info.missilecommand.gui;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.gui.Dibujador.ModosDibujo;




public class VentanaJuego extends VentanaAbstracta {

	private static final long serialVersionUID = 1L;
	private Dibujador dibujador;
	private AreaDibujo areaDibujo;
	private Controlador controlador;
	private Dibujador.ModosDibujo modoDibujo = ModosDibujo.JUEGO_PRINCIPAL;

	public VentanaJuego(JFrame padre, EntradaPuntaje jugador)  {

		super(padre,"Missile Command");
		areaDibujo = new AreaDibujo();
		this.setResizable(false);
		final Escenario e  = Juego.getInstancia().getEscenario();
		this.setSize(e.getAncho()+5, e.getAlto()+29);
		esconderCursor();
		centrarVentana();
		

		dibujador = new Dibujador(Juego.getInstancia());
		
		this.add(areaDibujo);
		this.setFocusable(false);
		areaDibujo.setFocusable(true);
		
		controlador = new Controlador(this);
		controlador.setJugador(jugador);
		controlador.start();

	}



	private class AreaDibujo extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		AreaDibujo() {}
		@Override
		protected void paintComponent(Graphics g) {
			dibujador.dibujar(g,modoDibujo);
		}
	}
	
	JPanel getAreaDibujo() {
		return this.areaDibujo;
	}
	
	Dibujador getDibujador() {
		return dibujador;
	}
	

	public void setModoDibujador(ModosDibujo modo) {
		if(modo==null)
			throw new NullPointerException("Modo no puede ser nulo!");
		this.modoDibujo=modo;
		
	}
	
}
