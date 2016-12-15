package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa un satelite capaz de bombardear
 */
public class Satelite extends Bombardero {
	final static int ALTO = 60;
	final static int ANCHO = 60;

	public Satelite() {
		super(ANCHO, ALTO);
	}

	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

}