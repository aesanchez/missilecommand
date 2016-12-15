package ar.edu.unlp.info.missilecommand;

/**
 * Misil balistico interplanetario. Caen en una linea recta dejando una estela
 * en el camino. Tienen la capacidad de bifurcarse al llegar a cierta altura.
 * 
 * @author Sánchez
 */
class MisilBalisticoInterplanetario extends Movil implements Destruible {

	private VectorGeometrico destino;// destino, indicado por el
											// juego,ciudad o silo
	private double rapidez;// varia segun el nivel
	/**
	 * Altura en la que el misil debe bifurcarse, se genera al azar dentro de un
	 * rango.
	 */
	private double alturaBifurcacion;

	private boolean puedeBifurcar;//

	MisilBalisticoInterplanetario(VectorGeometrico posicionFinal,
			double velocidad, VectorGeometrico posicionInicial,
			boolean bifurcable) {
		
		this.puedeBifurcar = bifurcable;
		this.posicion = posicionInicial;
		this.destino = posicionFinal;
		this.rapidez = velocidad;
		//generar altura random entre 300 y 150
		
		this.alturaBifurcacion=(Math.random()*151)+150;
		// calculo el vector velocidad correspodiente
		this.establecerVelocidad(destino, rapidez);
	}

	@Override
	public void recibirAtaque() {
		// TODO Auto-generated method stub

	}

	public void animar() {
		
		if((puedeBifurcar)&&(posicion.getY()<alturaBifurcacion)){
			//genera misiles entre 2 y 5(????)
		//se borra
		}
		this.posicion.desplazar(this.velocidad);

	}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}