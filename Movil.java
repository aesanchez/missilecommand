package ar.edu.unlp.info.missilecommand;

/**
 * Clase abstracta cuya toda clase que represente un objeto que se puede mover tiene que extender.
 */
abstract class Movil extends Dibujable {
	protected VectorGeometrico velocidad;


	/**
	 * Clase abstracta cuya toda clase que represente un objeto que se puede mover tiene que extender.
	 */
	Movil() {
		
	}
	public void establecerVelocidad(VectorGeometrico destino,double rapidez){
		VectorGeometrico aux = (destino.resta(posicion));
		aux=aux.productoEscalar((1.0) / aux.modulo());
		aux=aux.productoEscalar(rapidez);
		// asigno el vector calculado
		this.velocidad = aux;
	}
	abstract public void animar();
}