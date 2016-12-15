package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Destruible;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;
import ar.edu.unlp.info.missilecommand.Vector2D;

/**
 * Clase abstracta que separa a los móviles enemigos de los aliados para mas
 * chequeos estáticos de tipo.
 */
public abstract class MovilEnemigo extends Movil implements Destruible {

	MovilEnemigo(int ancho, int alto) {
		super(ancho, alto);
		this.setVelocidad(Vector2D.vectorNulo);
	}

	@Override
	protected void actualizar() {

	}

	/**
	 * Se borra a si mismo y genera una onda expansiva en su lugar.
	 */
	@Override
	public void recibirAtaque() {
		Juego.getInstancia().registrarOndaExpansiva(
				new OndaExpansiva(this.getPosicionCentrada()));
		Juego.getInstancia().eliminarMovilEnemigo(this);
		this.setColisionable(false);
	}

}
