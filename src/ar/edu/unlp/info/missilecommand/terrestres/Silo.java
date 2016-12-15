package ar.edu.unlp.info.missilecommand.terrestres;

import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;
import ar.edu.unlp.info.missilecommand.moviles.MisilAntiaereo;

/**
 * Clase que representa los silos. Cada silo tiene una cantidad de misiles que
 * se pierden al ser destruidos.
 */
public class Silo extends Terrestre {
	public final static int ALTO = 40;
	public final static int ANCHO = 60;
	final static int CANTIDAD_INICIAL = 10;
	private boolean activo = false;
	private int misilesDisponibles;
	/**
	 * Rapidez de misiles en pixeles por segundo.
	 */
	private double rapidezMisiles;

	/**
	 * Constructor
	 * 
	 * @param padre
	 *            Instancia de escenario que la crea
	 */
	public Silo(Escenario padre) {
		super(padre, ANCHO, ALTO);
		this.reset();
	}

	@Override
	public void actualizar() {

	}

	/**
	 * Crea un misil antiaéreo con la direccion final a la que se dirige.
	 * 
	 * @param destino
	 *            Posición destino
	 * @throws SiloVacioException
	 *             si el silo ya no tiene mas misiles
	 * @throws MisilDemasiadoBajoException
	 *             si se trata de disparar debajo del umbral
	 */
	public void disparar(Vector2D destino) throws SiloVacioException,
			MisilDemasiadoBajoException {
		assert (misilesDisponibles >= 0);
		Juego juego = Juego.getInstancia();
		if (rapidezMisiles == 0.0d)
			throw new IllegalStateException("Rapidez todavía no establecida!");
		/*
		 * Quedan misiles?
		 */
		if (misilesDisponibles > 0) {
			/*
			 * Si se trata de disparar debajo del umbral, disparar hacia el
			 * límite
			 */
			if (destino.getY() > juego.getEscenario().getUmbralMisiles())
				throw new MisilDemasiadoBajoException();
			/*
			 * Disparar
			 */
			MisilAntiaereo nuevoMisil = new MisilAntiaereo();
			nuevoMisil.setPosicion(getPosicionCentrada());
			nuevoMisil.setDestino(destino, rapidezMisiles);
			juego.registrarMovil(nuevoMisil);
			misilesDisponibles--;
		} else {
			throw new SiloVacioException();
		}
	}

	public int getMisilesDisponibles() {
		assert (misilesDisponibles >= 0);
		return misilesDisponibles;
	}

	/**
	 * Al recibir un ataque daña el silo, lo hace no colisionable y elimina
	 * todos los misiles que contenía. Luego dibuja una onda expansiva.
	 */

	@Override
	public void recibirAtaque() {
		super.recibirAtaque();
		this.misilesDisponibles = 0;
	}

	/**
	 * Reestablece el estado inicial del silo.
	 */
	@Override
	public void reset() {
		super.reset();
		this.activo = false;
		this.misilesDisponibles = CANTIDAD_INICIAL;
	}

	/**
	 * @param rapidez
	 *            Rapidez en pixeles por segundo
	 */
	public void setRapidezMisiles(double rapidez) {
		this.rapidezMisiles = rapidez;
	}

	@Override
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}