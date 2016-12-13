package ar.edu.unlp.info.missilecommand;

/**
 * @author Sánchez
 */
class MisilCruceroTonto extends MisilCrucero {
	public VectorGeometrico destino;
	
	MisilCruceroTonto(VectorGeometrico posicionInicial,VectorGeometrico posicionFinal) {
		this.posicion=posicionInicial;
		this.destino=posicionFinal;
		this.establecerVelocidad(destino, rapidez);
	}

	@Override
	public void recibirAtaque() {
		// TODO Auto-generated method stub
		
	}
	public void animar(){
		this.posicion.desplazar(this.velocidad);
	}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}

}