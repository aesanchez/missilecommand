package ar.edu.unlp.info.missilecommand;

import java.math.BigInteger;

/**
 * Esta clase representa un nivel en particular y todos los aspectos correspondientes a uno.
 */
class Nivel {

	/**
	 * Esta clase representa un nivel en particular y todos los aspectos correspondientes a uno.
	 */
	Nivel() {
	}

	/**
	 * Cantidad de MBIs restantes.
	 * Debería ser siempre la suma de los MBIs restantes de todos los ilos.
	 */
	private int cantidadMisiles ;

	/**
	 * Cantidad de misiles enemigos por ser lanzados.
	 */
	private int misilesEnemigos ;

	/**
	 * Número de nivel.
	 */
	private int numero ;

	/**
	 * 
	 */
	private int puntajeMBI;

	/**
	 * 
	 */
	private int puntajeMBA;

	/**
	 * 
	 */
	private int puntajeCiudades;

	/**
	 * 
	 */
	private int puntajeMC ;

	/**
	 * ???
	 */
	private boolean rapidaIndicacion;

	/**
	 * ???
	 */
	private boolean lentaIndicacion;

	/**
	 * Idem MCI
	 */
	private boolean MCT ;

	/**
	 * Que es esto?!
	 */
	private boolean MCI ;




	/**
	 * @return
	 */
	public BigInteger puntajeTotal() {
		// TODO implement here
		return null;
	}

}