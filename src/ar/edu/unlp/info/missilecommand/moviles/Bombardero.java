package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.terrestres.Terrestre;

/**
 * Clase abstracta que representa un bombardero.
 */
public abstract class Bombardero extends MovilEnemigo {
	public enum Sentido {
		DERECHA, IZQUIERDA
	}

	/**
	 * Ancho del rango donde se puede mover en pixeles
	 */
	private double anchoRango;
	private boolean bombardeado = false;
	/**
	 * Indica habilidad de bombardear.
	 */
	private boolean bombardeoActivo;

	private boolean listo;

	/**
	 * Posicion a lo largo del eje horizontal donde bombardear
	 */
	private double posicionBombardeo;

	/**
	 * Rapidez en pixeles por segundo
	 */
	private double rapidez;

	/**
	 * Sentido en que se mueve
	 */
	private Sentido sentido;

	Bombardero(int ancho, int alto) {
		super(ancho, alto);
	}

	@Override
	protected void actualizar() {
		if (!listo)
			throw new IllegalStateException();
		setPosicion(getPosicion().suma(getVelocidad()));
		if (Math.abs(getPosicion().getX() - posicionBombardeo) < 4) {
			bombardear();
		}
		if (sentido.equals(Sentido.DERECHA)) {
			if (getPosicion().getX() > anchoRango) {
				Juego.getInstancia().eliminarMovilEnemigo(this);
			}
		} else {
			if (getPosicion().getX() + getAncho() < 0)
				Juego.getInstancia().eliminarMovilEnemigo(this);
		}
	}

	/**
	 * Obtiene un número random entre 2 y 5 que serán los misiles a bombardear,
	 * luego decide el destino de cada uno de ellos entre ciudades o silos,
	 * finalmente decide que ciudad o que silo destino tendrá especificamente.
	 */
	protected void bombardear() {
		if (!bombardeado) {
			final int cantidadADisparar = 2 + (int) (4 * Math.random());

			for (int i = 0; i < cantidadADisparar; i++) {
				Terrestre objetivo = Juego.getInstancia().getEscenario()
						.obtenerTerrestreRandom();
				MisilBalisticoInterplanetario nuevoMisil = new MisilBalisticoInterplanetario();
				nuevoMisil.setBifurcable(false);
				nuevoMisil.setPosicion(this.getPosicionCentrada());
				nuevoMisil.setDestino(objetivo.getPosicionCentrada(), rapidez);

				Juego.getInstancia().registrarMovilEnemigo(nuevoMisil);
			}
			bombardeado = true;
		}

	}

	/**
	 * Genera una posición dentro del rango, en donde va a bombardear.
	 */
	private void generarPosicionBombardeo() {
		bombardeado = false;
		posicionBombardeo = anchoRango * Math.random();

	}

	/**
	 * Comprueba que todos los campos necesarios se hayan seteado y prepara a
	 * Bombardero pasa su funcionamiento
	 */
	public void inicializar() {
		if (this.getPosicion() == Vector2D.vectorNulo || rapidez == 0
				|| anchoRango == 0)
			throw new IllegalStateException();
		final double rapidezAbsoluta = Juego
				.pixelPorSegundoAPixelPorFotograma(rapidez);
		this.generarPosicionBombardeo();
		if (getPosicion().getX() < 0) {
			/*
			 * Bombardero viene de la izquierda hacia la derecha
			 */
			sentido = Sentido.DERECHA;
			this.setVelocidad(new Vector2D(rapidezAbsoluta, 0));
		} else {
			sentido = Sentido.IZQUIERDA;
			this.setVelocidad(new Vector2D((-1) * rapidezAbsoluta, 0));
		}
		listo = true;
		assert (sentido != null);
	}

	
	boolean isBombardeoActivo() {
		return bombardeoActivo;
	}

	public void setAnchoRango(double anchoRango) {
		this.anchoRango = anchoRango;
	}

	public void setBombardeoActivo(boolean bombardeoActivo) {
		this.bombardeoActivo = bombardeoActivo;
	}

	/**
	 * @param rapidez
	 *            en pixeles por segundo
	 */
	public void setRapidez(double rapidez) {
		this.rapidez = rapidez;
	}

	public Sentido getSentido() {
		return sentido;
	}

}
