package ar.edu.unlp.info.missilecommand;

import java.util.*;

/**
 * 
 */
class MisilCruceroInteligente extends MisilCrucero {
	public VectorGeometrico destino;
	private VectorGeometrico esquivarDerecha;
	private VectorGeometrico esquivarIzquierda;
	private double radio = 4;

	/**
	 * 
	 */
	MisilCruceroInteligente(VectorGeometrico posicionInicial,
			VectorGeometrico posicionFinal) {
		this.posicion = posicionInicial;
		this.destino = posicionFinal;
		this.establecerVelocidad(destino, rapidez);
	}

	@Override
	public void recibirAtaque() {
		// TODO Auto-generated method stub

	}
	
	public void animar(List<OndaExpansiva> ondasExpansivas,
			List<MisilAntiaereo> misilesAntiaereos) {
		int i = 0;
		boolean hay = false;
		double distancia;
		while ((i < ondasExpansivas.size()) && (!hay)) {
			VectorGeometrico aux = this.posicion.resta((ondasExpansivas.get(i))
					.getPosicion());
			distancia = aux.modulo();
			if ((distancia < radio)
					&& ((ondasExpansivas.get(i)).getPosicion().getY() < posicion
							.getY())) {
				hay = true;
				if ((ondasExpansivas.get(i)).getPosicion().getX() < this.posicion
						.getX()) {// va para la derecha
					this.posicion.desplazar(this.esquivarDerecha);
				} else {// va para la izquierda
					this.posicion.desplazar(this.esquivarIzquierda);
				}
			}
			i++;

		}
		i = 0;
		while ((i < misilesAntiaereos.size()) && (!hay)) {
			VectorGeometrico aux = this.posicion.resta((misilesAntiaereos
					.get(i)).getPosicion());
			distancia = aux.modulo();
			if ((distancia < radio)
					&& ((misilesAntiaereos.get(i)).getPosicion().getY() < posicion
							.getY())) {
				hay = true;
				if ((misilesAntiaereos.get(i)).getPosicion().getX() < this.posicion
						.getX()) {// va para la derecha
					this.posicion.desplazar(this.esquivarDerecha);
				} else {// va para la izquierda
					this.posicion.desplazar(this.esquivarIzquierda);
				}
			}

		}
		if (!hay) {
			this.posicion.desplazar(this.velocidad);
		}

	}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}