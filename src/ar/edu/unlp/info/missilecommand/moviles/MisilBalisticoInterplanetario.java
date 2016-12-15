package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Estela;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;
import ar.edu.unlp.info.missilecommand.terrestres.Terrestre;

/**
 * Clase que representa un misil balístico interplanetario. Caen en una linea
 * recta dejando una estela en el camino. Tienen la capacidad de bifurcarse al
 * llegar a cierta altura.
 */
public class MisilBalisticoInterplanetario extends MovilEnemigo {
	private static int ALTO = 5;
	private static int ANCHO = 5;
	

	public final static int PUNTAJE = 25;
	/**
	 * Altura en la que el misil debe bifurcarse, se genera al azar dentro de un
	 * rango
	 */
	private double alturaBifurcacion;

	private boolean bifurcable;
	/**
	 * El destino del misil: ciudad o silo
	 */
	private Vector2D destino;

	private Estela estela;
	/**
	 * Posicion de donde viene el misil
	 */
	private Vector2D posicionInicial;
	/**
	 * Rapidez medida en pixel por segundo, varia segun el nivel.
	 */
	private double rapidez;

	/**
	 * 
	 */
	public MisilBalisticoInterplanetario() {
		super(ANCHO, ALTO);
		rapidez = Juego.getInstancia().getPartida().getRapidezEnemigos();
	}

	/**
	 * Actualiza el estado de misil
	 */
	@Override
	public void actualizar() {
		if (destino == null || posicionInicial == null)
			throw new IllegalStateException();

		if (this.getPosicion().getY() > this.destino.getY()) {
			System.out
					.println(Juego.getInstancia().getFotogramaActual()
							+ " "
							+ "MisilBalisticoInterplanetario llego a su destino,no se encontro con nada");
			this.recibirAtaque();
		} else if (bifurcable && getPosicion().getY() > alturaBifurcacion)
			bifurcar();
		else
			this.setPosicion(this.getPosicion().suma(this.getVelocidad()));

	}

	/**
	 * Bifurca este mísil. Genera otros una cantidad al azar y también mantiene
	 * a este.
	 */
	private void bifurcar() {
		assert (bifurcable);
		// genera entre 1 y 3 bifurcaciones extra. El misil original sigue
		// estando
		int bifurcaciones = (1 + (int) (Math.random() * 3));
		System.out.println(Juego.getInstancia().getFotogramaActual() + " "
				+ this + " se bifurcó en " + bifurcaciones
				+ " misil/es nuevo/s");
		for (int i = 0; i < bifurcaciones; i++) {
			Terrestre objetivo = Juego.getInstancia().getEscenario()
					.obtenerTerrestreRandom();
			MisilBalisticoInterplanetario nuevoMisil = new MisilBalisticoInterplanetario();
			nuevoMisil.setPosicion(getPosicion());
			nuevoMisil.setBifurcable(false);
			nuevoMisil.setDestino(objetivo.getPosicionCentrada(), rapidez);
			Juego.getInstancia().registrarMovilEnemigo(nuevoMisil);
		}
		bifurcable = false;
	}

	boolean isBifurcable() {
		return bifurcable;
	}

	/**
	 * @param bifurcable
	 *            True si se puede bifurcar, false si no.
	 */
	public void setBifurcable(boolean bifurcable) {
		this.bifurcable = bifurcable;
		if (this.bifurcable) {
			// genero una altura de bifurcacion random entre 1/3 y 2/3 de la
			// altura del escenario
			int alturaEscenario = Juego.getInstancia().getEscenario().getAlto();
			this.alturaBifurcacion = (Math.random() * (alturaEscenario / 3))
					+ (alturaEscenario / 3);
		}
	}

	/**
	 * 
	 * @param destino
	 *            Posicion final
	 * @param rapidez
	 *            Rapidez en pixeles por segundo
	 */
	public void setDestino(Vector2D destino, double rapidez) {
		this.destino = destino;
		this.establecerVelocidad(this.destino, rapidez);
	}

	@Override
	public void setPosicion(Vector2D posicion) {
		super.setPosicion(posicion);
		if (posicionInicial == null) {
			this.posicionInicial = posicion;
			this.estela = new Estela();
			this.estela.setEmisor(this);
			this.estela.setOrigen(posicionInicial);
		} else
			super.setPosicion(posicion);
	}
	
	public Estela getEstela(){
		return this.estela;
	}
	
	@Override 
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

}