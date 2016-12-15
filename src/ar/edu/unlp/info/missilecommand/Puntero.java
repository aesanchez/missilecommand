/**
 * 
 */
package ar.edu.unlp.info.missilecommand;

import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa el puntero hacia donde se disparan los misiles.
 */
public class Puntero extends Elemento {

	final static int ALTO = 25;
	final static int ANCHO = 25;

	Puntero() {
		super(ANCHO, ALTO);
		this.setColisionable(false);
		this.setPosicion(new Vector2D(-ANCHO,-ALTO));
	}

	/**
	 * @see ar.edu.unlp.info.missilecommand.Elemento#actualizar()
	 */
	@Override
	public void actualizar() {}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

}
