package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Destruible;
import ar.edu.unlp.info.missilecommand.Estela;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa un misil lanzado desde tierra. Deja una estela a medida
 * que avanza. Al llegar a su destino o al colisionar con un elemento enemigo,
 * explota.
 */
public final class MisilAntiaereo extends MovilAliado implements Destruible {
	static private final int ALTO = 5;
	static private final int ANCHO = 5;
	public final static int PUNTAJE = 5;

	private Vector2D destino;

	private Estela estela;

	public MisilAntiaereo() {
		super(ANCHO, ALTO);
	}

	/**
	 * Metodo que dibuja el misil en la pantalla, y llama al dibujar de la
	 * estela
	 */
	@Override
	public void actualizar() {
		if (destino == null)
			throw new IllegalStateException();
		if (this.getPosicion().getY() < destino.getY()) {// si llego a su fin
			System.out
					.println(Juego.getInstancia().getFotogramaActual()
							+ " MisilAntiaereo llego a su destino,genera onda expansiva.");
			this.setPosicion(getDestino());
			this.recibirAtaque();// crea la onda expansiva y elimina el misil
		} else {
			setPosicion(this.getPosicion().suma(this.getVelocidad()));// avanza

		}
	}

	/**
	 * Define el destino y rapidez de este misil.
	 * 
	 * @param posicion
	 *            Posición final
	 * @param rapidez
	 *            Rapidez en pixeles por segundo
	 */
	public void setDestino(Vector2D posicion, double rapidez) {
		if (destino == null) {
			this.destino = posicion;
			this.establecerVelocidad(this.destino, (rapidez));
			estela = new Estela();
			estela.setOrigen(getPosicion());
			estela.setEmisor(this);
		}
	}
	
	public Estela getEstela(){
		return this.estela;
	}
	
	public Vector2D getDestino(){
		return this.destino;
	}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

	
}