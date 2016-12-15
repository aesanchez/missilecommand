package ar.edu.unlp.info.missilecommand.terrestres;

import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa cada una de las ciudades. Se encarga de mantener la
 * información de su estado y dibujarse
 */
public class Ciudad extends Terrestre {

	public final static int ALTO = 50;
	public final static int ANCHO = 60;
	public final static int PUNTAJE = 100;

	/**
	 * @param padre
	 *            Referencia al Escenario que la crea.
	 */
	public Ciudad(Escenario padre) {
		super(padre, ANCHO, ALTO);
		this.reset();
	}
	
	@Override
	public void actualizar() {

	}

	@Override
	public void recibirAtaque() {
		super.recibirAtaque();
	}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}
}