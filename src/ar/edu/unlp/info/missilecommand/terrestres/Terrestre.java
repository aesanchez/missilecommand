package ar.edu.unlp.info.missilecommand.terrestres;

import ar.edu.unlp.info.missilecommand.Destruible;
import ar.edu.unlp.info.missilecommand.Elemento;
import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;

/**
 * Clase abstracta que representa un elemento terrestre.
 */
public abstract class Terrestre extends Elemento implements Destruible {

	private boolean destruido;
	private Escenario padre;

	Terrestre(Escenario padre, int ancho, int alto) {
		super(ancho, alto);
		this.padre = padre;
	}

	@Override
	protected void actualizar() {
		assert (destruido == !isColisionable());

	}

	protected Escenario getPadre() {
		return padre;
	}

	/**
	 * Define a este Terrestre como desturido;
	 */
	@Override
	public void recibirAtaque() {
		if(!destruido) {
		assert (destruido == !isColisionable());
		destruido = true;
		setColisionable(false);
		assert (destruido == !isColisionable());
		Juego.getInstancia().registrarOndaExpansiva(
				new OndaExpansiva(this.getPosicionCentrada()));
		}

	}

	/**
	 * Restablece el estado de un Terrestre. Lo hace colisionable y no
	 * destruido. Tiene que ser llamado por los reset() de subclases!
	 */
	public void reset() {
		destruido = false;
		setColisionable(true);
	}

	public void setDestruido(boolean destruido) {
		this.destruido = destruido;
		setColisionable(!destruido);
	}
	
	public boolean isDestruido() {
		return this.destruido;
	}
}
