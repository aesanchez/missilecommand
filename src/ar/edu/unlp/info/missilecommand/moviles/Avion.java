package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.gui.Dibujador;


/**
 * Clase que representa un avión.
 */
public class Avion extends Bombardero {
	final static int ALTO = 40;
	final static int ANCHO = 100;

	public Avion() {
		super(ANCHO, ALTO);
	}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}
	

}