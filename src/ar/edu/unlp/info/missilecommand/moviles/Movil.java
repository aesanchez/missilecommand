package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Elemento;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;

/**
 * Clase abstracta cuya toda clase que represente un objeto que se puede mover
 * tiene que extender.
 */
public abstract class Movil extends Elemento {

	/**
	 * Velocidad (vectorial) en píxeles por fotograma
	 */
	private Vector2D velocidad = Vector2D.vectorNulo;

	/**
	 * Constructor por defecto, inicializa velocidad en (0,0)
	 *
	 * @param ancho
	 *            Anchura
	 * @param alto
	 *            Altura
	 */
	Movil(int ancho, int alto) {
		super(ancho, alto);
	}

	/**
	 * Configura la velocidad de este Movil para que apunte hacia una posición
	 * con una rapidez deseada
	 * 
	 * @param destino
	 *            Posición destino.
	 * @param rapidez
	 *            Rapidez en pixeles por segundo.
	 */
	protected void establecerVelocidad(Vector2D destino, double rapidez) {
		rapidez = Juego.pixelPorSegundoAPixelPorFotograma(rapidez);
		Vector2D resultado = destino.resta(this.getPosicion());
		this.velocidad = resultado
				.productoEscalar(rapidez / resultado.modulo());
	}

	protected Vector2D getVelocidad() {
		return velocidad;
	}

	protected void setVelocidad(Vector2D velocidad) {
		this.velocidad = velocidad;
	}

	@Override
	public String toString() {

		return super.toString() + " - vel:" + this.velocidad;
	}

}