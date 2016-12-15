package ar.edu.unlp.info.missilecommand;

import java.util.Locale;

/**
 * Clase que representa un par ordenado de punto flotantes (x,y) inmutable.
 */
public final class Vector2D {

	final public static Vector2D vectorNulo = new Vector2D(0, 0);
	final private double x;
	final private double y;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            Posición inicial x
	 * @param y
	 *            Posición incial y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	/**
	 * Devuelve un vector con los signos cambiados.
	 * 
	 * @return Inverso aditivo
	 */
	public Vector2D inversoAditivo() {
		return new Vector2D((-1) * this.x, (-1) * this.y);
	}

	/**
	 * Devuelve la magnitud del vector.
	 * 
	 * @return El módulo del vector.
	 */
	public double modulo() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Devuelve el resultado de la multiplicación de este vector por un escalar.
	 * 
	 * @param escalar
	 *            Un escalar real.(ish)
	 * @return Vector con el resultado de la multiplicación.
	 */
	public Vector2D productoEscalar(double escalar) {
		return new Vector2D(escalar * x, escalar * y);
	}

	/**
	 * Devuelve el resultado del producto escalar entre dos vectores.
	 * 
	 * @param op
	 *            Segundo operando
	 * @return Producto escalar entre el vector receptor y el vector pasado por
	 *         parámetro.
	 */
	public double productoEscalar(Vector2D op) {
		return (this.x * op.x) + (this.y + op.y);
	}

	/**
	 * Resta a este vector otro y devuelve el resultado en un tercer vector.
	 * 
	 * @param op
	 *            Sustraendo
	 * @return Resultado de la resta.
	 */
	public Vector2D resta(Vector2D op) {
		return new Vector2D(this.x - op.x, this.y - op.y);
	}

	/**
	 * Suma dos vectores y devuelve un vector con el resultado.
	 * 
	 * @param op
	 *            Sumando.
	 * @return Resultado de la suma.
	 */
	public Vector2D suma(Vector2D op) {
		return new Vector2D(this.x + op.x, this.y + op.y);
	}

	/**
	 * Devuelve una String representando el par (x,y) de este vector.
	 */
	@Override
	public String toString() {
		return "(" + String.format(Locale.ENGLISH, "%.2f", x) + ","
				+ String.format(Locale.ENGLISH, "%.2f", y) + ")";
	}

}