package ar.edu.unlp.info.missilecommand;

import ar.edu.unlp.info.missilecommand.gui.Dibujable;


/**
 * Clase abstracta cuya toda clase que represente un objeto visible y
 * posiblemente colisionable tiene que extender.
 */
public abstract class Elemento implements Dibujable {

	private int alto;
	private int ancho;
	private boolean colisionable = true;
	private Vector2D posicion = Vector2D.vectorNulo;

	/**
	 * Crea un nuevo dibujable en la posicion dada
	 * 
	 * @param ancho
	 *            Ancho del elemento en pï¿½xeles.
	 * @param alto
	 *            Altura del elemento en pï¿½xeles.
	 */
	protected Elemento(int ancho, int alto) {
		this.ancho = ancho;
		this.alto = alto;
	}

	/**
	 * Actualiza este elemento.
	 */
	protected abstract void actualizar();

	/**
	 * Chequea los limites de los rectangulos que cubren a los objetos para
	 * detectar colisiones, a no ser que alguno sea no colisionable.
	 * 
	 * @param otro
	 *            un objeto de tipo Elemento
	 * @return True si colisionan, false si no (o si alguno es no colisionable).
	 */
	public boolean colisionaCon(Elemento otro) {
		/*
		 * Alguno es no colisionable?
		 */
		if (!(this.colisionable && otro.colisionable))
			return false;
		assert (this.colisionable || otro.colisionable);
		
		final int bordeIzq1 = (int)this.posicion.getX();
		final int bordeSup1 = (int)this.posicion.getY();
		final int bordeDer1 = bordeIzq1 + ancho;
		final int bordeInf1 = bordeSup1 + alto;
		final int bordeIzq2 = (int)otro.getPosicion().getX();
		final int bordeSup2 = (int)otro.getPosicion().getY();
		final int bordeDer2 = bordeIzq2 + otro.getAncho();
		final int bordeInf2 = bordeSup2 + otro.getAlto();
		
		
		
		
		final boolean colisionan = !( bordeSup1 > bordeSup2 ||
									bordeInf1 < bordeInf2 ||
									bordeDer1 < bordeIzq2 ||
									bordeIzq1 > bordeDer2);
		if (colisionan) {
			System.out.println(Juego.getInstancia().getFotogramaActual() + " "
					+ otro + " colisiona con: " + this);
		}
		return colisionan;
	}
	
	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}

	public Vector2D getPosicion() {
		return posicion;
	}
	
	/**
	 * @return posición del centro del elemento.
	 */
	public Vector2D getPosicionCentrada() {
		return new Vector2D(posicion.getX()+(this.ancho/2), posicion.getY()+(this.alto/2));
	}
	
	public void setPosicionCentrada(double x , double y) {
		this.posicion = new Vector2D(x-this.ancho/2,y-this.alto/2);
	}

	public boolean isColisionable() {
		return colisionable;
	}

	protected void setAlto(int alto) {
		this.alto = alto;
	}

	protected void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public void setColisionable(boolean colisionable) {
		this.colisionable = colisionable;
	}

	public void setPosicion(Vector2D posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " - pos:" + this.getPosicion();
	}

}