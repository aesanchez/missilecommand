package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Vector2D;

/**
 * Clase abstracta que representa un misil crucero.
 */
public abstract class MisilCrucero extends MovilEnemigo {
	final static int ALTO = 10;
	final static int ANCHO = 10;
	public final static int PUNTAJE = 125;
	private Vector2D destino;

	/**
	 * Rapidez en pixeles por segundo
	 */
	private double rapidez;

	MisilCrucero() {
		super(ANCHO, ALTO);

	}

	protected Vector2D getDestino() {
		return destino;
	}

	protected double getRapidez() {
		return rapidez;
	}

	/**
	 * Define el destino de este MisilCrucero
	 * 
	 * @param destino
	 *            Posición destino
	 * @param rapidez
	 *            Rapidez en píxeles por segundo
	 */
	public void setDestino(Vector2D destino, double rapidez) {
		this.rapidez = rapidez;
		this.destino = destino;
		this.establecerVelocidad(this.destino, this.rapidez);
	}

	protected void setRapidez(double rapidez) {
		this.rapidez = rapidez;
	}


}