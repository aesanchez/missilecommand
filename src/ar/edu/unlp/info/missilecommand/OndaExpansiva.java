package ar.edu.unlp.info.missilecommand;

import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa la explosión que deja la destrucción de un elemento.
 * Hereda de MovilAliado para simplicidad en el chequeo de colisiones. Ya que es
 * capaz de destruir algo de tipo MovilEnemigo.
 */
public class OndaExpansiva extends Elemento {

	public final static int RADIO_MAX = 40;
	/**
	 * Angulo en radianes para la función seno que regula el tamano de la onda
	 * espansiva
	 */
	private float phi = 0;
	
	/**
	 * Velocidad angular para phi (radian/fotograma)
	 */
	private float omega;

	public float getPhi() {
		return phi;
	}
	
	public float getRadioActual() {
		return radioActual;
	}

	private int radioActual;

	/**
	 * Crea una nueva onda en la posición pasada.
	 * 
	 * @param posicion
	 *            posicion del centro de la onda expansiva.
	 */
	public OndaExpansiva(Vector2D posicion) {
		super(0,0);
		this.setPosicion(posicion);
		omega = (float) Juego.pixelPorSegundoAPixelPorFotograma(1);
		//ajuste
		phi+=omega*5;
	}

	/**
	 * Expande la onda expansiva hasta el radio indicado y luego la contrae
	 * hasta que desaparece
	 */
	@Override
	public void actualizar() {

		radioActual = (int) (RADIO_MAX *Math.sin(phi));
		setAlto(radioActual);
		setAncho(radioActual);

		phi += omega;
		if (phi > Math.PI) {
			Juego.getInstancia().eliminarOndaExpansiva(this);
		}else if(phi> (Math.PI*0.50 + omega*10))
			this.setColisionable(false);
		
	}

	@Override
	public boolean colisionaCon(Elemento otro) {
		/*
		 * Alguno es no colisionable?
		 */

		if (!otro.isColisionable())
			return false;
		assert (otro.isColisionable());
		final double distancia = this.getPosicion().resta(otro.getPosicionCentrada())
				.modulo();
		final boolean colisionan = (distancia < (radioActual+otro.getAlto()/2));

		if (colisionan) {
			System.out.println(Juego.getInstancia().getFotogramaActual() + " "
					+ otro + " colisionó con: " + this
					+ ". Radio de la onda expansiva al colisionar: "
					+ radioActual);
		}
		return colisionan;

	}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

}