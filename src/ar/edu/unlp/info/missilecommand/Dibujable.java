package ar.edu.unlp.info.missilecommand;

/**
 * Esta es una clase abstracta cuya toda clase que represente un objeto visible tiene que extender.
 */
abstract class Dibujable {
	protected VectorGeometrico posicion;
	/**
	 * Esta es una clase abstracta cuya toda clase que represente un objeto visible tiene que extender.
	 */
	Dibujable() {
	}



	public VectorGeometrico getPosicion() {
		return posicion;
	}



	public void setPosicion(VectorGeometrico posicion) {
		this.posicion = posicion;
	}



	/**
	 * 
	 */
	public abstract void dibujar();

	/**
	 * @param pos
	 */
	public void posicionar(VectorGeometrico pos ) {
		// TODO implement here
	}

	/**
	 * @param x  
	 * @param y
	 */
	public void posicionar(double  x , double y ) {
		// TODO implement here
	}

}