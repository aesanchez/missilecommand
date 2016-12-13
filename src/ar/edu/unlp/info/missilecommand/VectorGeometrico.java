package ar.edu.unlp.info.missilecommand;

/**
 * Tipo de dato que representa un par ordenado de punto flotantes (x,y).
 * 
 * Esta clase es inmutable pero con la opción de cambiar sus valores para mejor rendimiento.
 */
final class VectorGeometrico {

	/**
	 * Tipo de dato que representa un par ordenado de punto flotantes (x,y).
	 * 
	 * Esta clase es inmutable pero con la opción de cambiar sus valores para mejor rendimiento.
	 */
	VectorGeometrico() {
	}

	/**
	 * 
	 */
	private double x ;

	/**
	 * 
	 */
	private double y ;





	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Suma dos vectores y devuelve un vector con el resultado.
	 * @param op  
	 * @return
	 */
	public VectorGeometrico suma(VectorGeometrico op ) {
		// TODO implement here
		return null;
	}

	/**
	 * Resta a este vector otro y devuelve el resultado en un tercer vector.
	 * @param op  
	 * @return
	 */
	public VectorGeometrico resta(VectorGeometrico op ) {
		// TODO implement here
		return null;
	}

	/**
	 * Devuelve un vector con los signos cambiados.
	 * @param op  
	 * @return
	 */
	public VectorGeometrico inversoAditivo(VectorGeometrico op ) {
		// TODO implement here
		return null;
	}

	/**
	 * Devuelve la magnitud del vector.
	 * @return
	 */
	public double modulo() {
		// TODO implement here
		return 0.0d;
	}

	/**
	 * Devuelve el resultado del producto escalar entre dos vectores.
	 * @param op  
	 * @return
	 */
	public double productoEscalar(VectorGeometrico op ) {
		// TODO implement here
		return 0.0d;
	}

	/**
	 * Devuelve el resultado de la multiplicación de este vector por un escalar.
	 * @param escalar  
	 * @return
	 */
	public VectorGeometrico productoEscalar(double escalar ) {
		// TODO implement here
		return null;
	}

	/**
	 * Suma a este vector otro vector.
	 * @param desplazamiento
	 */
	public void desplazar(VectorGeometrico desplazamiento ) {
		// TODO implement here
	}

}