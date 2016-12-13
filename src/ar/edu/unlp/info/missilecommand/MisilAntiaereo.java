package ar.edu.unlp.info.missilecommand;

/**
 * Misil lanzado desde tierra.
 * @author Sánchez
 */
class MisilAntiaereo extends Movil implements Destruible {

	private VectorGeometrico destino;
	private final double rapidez = 10;// la velocidad de los antiaereos es
										// constante

	/**
	 * 
	 * 
	 */
	MisilAntiaereo(VectorGeometrico posicionInicial,
			VectorGeometrico posicionFinal) {
		this.posicion = posicionInicial;
		this.destino = posicionFinal;
		// calculo el vector velocidad correspodiente
		this.establecerVelocidad(destino, rapidez);
		

	}

	/**
	 * 
	 */
	@Override
	public void recibirAtaque() {
		// TODO hace explosion

	}


	public void animar() {
		
		if(posicion.getY()>destino.getY()){
			//explota
		}
		this.posicion.desplazar(this.velocidad);

	}

	/**
	 *
	 */
	@Override
	public void dibujar() {
		// TODO dibuja;

	}

}