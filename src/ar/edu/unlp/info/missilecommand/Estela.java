package ar.edu.unlp.info.missilecommand;

/**
 * Clase que representa la estela que dejan por detrás los misiles
 * interplanetarios.
 */
public class Estela {
	/*
	 * el vector posicion de dibujable va a ser el principio de la estela
	 */
	private Elemento emisor;
	private Vector2D origen;

	public Estela() {
	}

	public Vector2D getOrigen(){
		return this.origen;
	}
	/**
	 * @param emisor
	 *            Elemento al que la estela sigue.
	 */
	public void setEmisor(Elemento emisor) {
		this.emisor = emisor;
	}
	
	public Elemento getEmisor() {
		return this.emisor;
	}

	/**
	 * @param posicion
	 *            Posición desde la cual se creó la estela.
	 */
	public void setOrigen(Vector2D posicion) {
		origen = posicion;
	}
}