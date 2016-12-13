package ar.edu.unlp.info.missilecommand;

/**
 * Clase abstracta que representa un misil crucero.
 * Deberia tener ya la imagen visual de un misil crucero, ya que sus derivado se siguen viendo igual.
 */
abstract class MisilCrucero extends Movil implements Destruible {
	protected final double rapidez = 10;
	MisilCrucero() {
	}

}