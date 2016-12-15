package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Destruible;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;

/**
 * Clase abstracta que separa a los móviles aliados de los enemigos para mas
 * chequeos estáticos de tipo.
 */
public abstract class MovilAliado extends Movil implements Destruible {

	MovilAliado(int ancho, int alto) {
		super(ancho, alto);
	}

	@Override
	protected void actualizar() {

	}

	@Override
	public void recibirAtaque() {
		Juego.getInstancia().registrarOndaExpansiva(
				new OndaExpansiva(this.getPosicionCentrada()));
		Juego.getInstancia().eliminarMovil(this);
		this.setColisionable(false);
	}

}
