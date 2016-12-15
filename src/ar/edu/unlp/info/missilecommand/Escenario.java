package ar.edu.unlp.info.missilecommand;

import ar.edu.unlp.info.missilecommand.gui.Dibujador;
import ar.edu.unlp.info.missilecommand.terrestres.Ciudad;
import ar.edu.unlp.info.missilecommand.terrestres.MisilDemasiadoBajoException;
import ar.edu.unlp.info.missilecommand.terrestres.Silo;
import ar.edu.unlp.info.missilecommand.terrestres.SiloVacioException;
import ar.edu.unlp.info.missilecommand.terrestres.Terrestre;

/**
 * Clase que contiene todos los objetos visuales no móviles del juego. También
 * se encarga de controlar los silos y ciudades.
 */
public final class Escenario extends Elemento {

	public static final int ANCHO = 640;
	public static final int ALTO = 640;

	final private Ciudad[] ciudades;

	private int siloActual;

	final private Silo[] silos;

	/**
	 * Posición de linea imaginaria que marca el umbral.
	 */
	final private int umbralMisiles;

	/**
	 * @param padre
	 *            Referencia al Juego que lo instancia
	 */
	Escenario(Juego padre) {
		super(ANCHO, ALTO);
		this.setPosicion(Vector2D.vectorNulo);
		this.setColisionable(false);
		/*
		 * El umbral esta poco arriba sobre el edificio mas alto.
		 */
		umbralMisiles = Escenario.ALTO - Math.max(Ciudad.ALTO, Silo.ALTO)
				- (OndaExpansiva.RADIO_MAX + 1);
		{
			/*
			 * Instanciacion de silos
			 */
			final double alturaSilos = Escenario.ALTO - Silo.ALTO;
			silos = new Silo[3];
			assert (silos.length == 3);
			silos[0] = new Silo(this);
			silos[0].setPosicion(new Vector2D(0, alturaSilos));
			silos[1] = new Silo(this);
			silos[1].setPosicion(new Vector2D((Escenario.ANCHO / 2)
					- (Silo.ANCHO / 2.0), alturaSilos));
			silos[2] = new Silo(this);
			silos[2].setPosicion(new Vector2D(Escenario.ANCHO - Silo.ANCHO,
					alturaSilos));
		}

		{
			/*
			 * Instanciacion de ciudades. Esto es un lío y la demonstracion
			 * geométrica esta en un papel perdido.
			 */
			this.ciudades = new Ciudad[6];
			assert (ciudades.length == 6);
			final double alturaCiudades = Escenario.ALTO - Ciudad.ALTO;
			final double particion = (silos[1].getPosicion().getX() - Silo.ANCHO) / 4.0d;
			final double mitadCiudad = Ciudad.ANCHO / 2;
			for (byte i = 0; i < 3; i++) {
				ciudades[i] = new Ciudad(this);
				ciudades[i].setPosicion(new Vector2D(Silo.ANCHO + (i + 1)
						* particion - mitadCiudad, alturaCiudades));
				ciudades[i + 3] = new Ciudad(this);
				ciudades[i + 3].setPosicion(new Vector2D(
						((Escenario.ANCHO + Silo.ANCHO) / 2) + (i + 1)
								* particion - mitadCiudad, alturaCiudades));
			}
		}
		resetCiudades(false);
		resetSilos();
	}

	@Override
	public void actualizar() {
		for (Silo silo : silos)
			silo.actualizar();
		for (Ciudad ciudad : getCiudades())
			ciudad.actualizar();
		/*
		 * Aca iria tambien el dibujo de todo lo demas.
		 */
	}

	/**
	 * Dispara un misil desde el silo actualmente activo hacia donde esté el
	 * puntero. Si se trata de disparar debajo del umbral, se dispara hacía la
	 * posición frontera. Si el silo seleccionado esta vacío, se trata de
	 * seleccionar el que más misiles tenga.
	 * 
	 * @param destino
	 *            Posición hacia donde disparar
	 */
	public void disparar(Vector2D destino) {
		int intentos = 0;
		while (intentos < 2) {
			try {
				silos[siloActual].disparar(destino);
				return;
				// exito
			} catch (SiloVacioException e) {
				setSiloActual(this.getSiloConMasMisiles());
			} catch (MisilDemasiadoBajoException e) {
				destino = new Vector2D(destino.getX(), umbralMisiles);
			}
			intentos++;
		}
	}

	private int getSiloConMasMisiles() {
		int max = 0;
		int maxSilo = siloActual;
		for (int i = 0; i <= 2; i++) {
			if (max < silos[i].getMisilesDisponibles()) {
				max = silos[i].getMisilesDisponibles(); // aguante Progra I
				maxSilo = i;
			}
		}
		return (maxSilo);
	}

	/**
	 * @return referencia a algún Silo o Ciudad elegido al azar. Trata de
	 *         devolver un terrestre no destruido.
	 */
	public Terrestre obtenerTerrestreRandom() {
		if (Math.random() > 0.5)
			return obtenerTerrestreRandom(ciudades);
		else
			return obtenerTerrestreRandom(silos);
	}

	private Terrestre obtenerTerrestreRandom(Terrestre[] arreglo) {
		/*
		 * Estoy 100% consciente de que puede intentar mas de una vez con el
		 * mismo terrestre
		 */
		int terrestreRandom = 0;
		for (int intento = 0; intento < arreglo.length; intento++) {

			terrestreRandom = (int) Math.floor(Math.random() * arreglo.length);
			if (!arreglo[terrestreRandom].isDestruido())
				return arreglo[terrestreRandom];
		}
		// rendirse
		return arreglo[terrestreRandom];

	}

	/**
	 * Vuelve a establecer a todas las ciudades como no destruidas. Si esBonus
	 * es verdadero, solo deja una ciudad.
	 * 
	 * @param esBonus
	 *            True si es bonus, false si no.
	 */
	void resetCiudades(boolean esBonus) {
		if (!esBonus) {
			for (Ciudad ciudad : getCiudades())
				ciudad.reset();
			esBonus = false;
		} else {
			for (Ciudad ciudad : getCiudades()) {
				ciudad.setDestruido(true);
				ciudad.setColisionable(false);
			}
			int ciudadUnica = (int) Math.floor(Math.random() * 6.0d); // [0,5]
			ciudades[ciudadUnica].setDestruido(false);
		}
	}

	/**
	 * Reabastece a los silos con misiles y los establece como no destruidos.
	 * Establece el primer Silo como el que va a disparar por defecto.
	 */
	void resetSilos() {
		for (Silo silo : silos) {
			silo.reset();
		}
		silos[0].setActivo(true);
	}

	@Override
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

	void setRapidezAntiaereos(double rapidez) {
		for (Silo silo : silos)
			silo.setRapidezMisiles(rapidez);
	}

	public void setSiloActual(int silo) {
		if (silo < 0 || silo > 2)
			throw new IllegalArgumentException();

		silos[siloActual].setActivo(false);
		if ((silos[silo].getMisilesDisponibles() == 0)
				|| (silos[silo].isDestruido())) {
			silo = this.getSiloConMasMisiles();
		}
		this.siloActual = silo;
		silos[siloActual].setActivo(true);
	}

	public int getCantidadCiudades() {
		int cantidadCiudades = 0;
		for(Ciudad ciudad : ciudades)
			if (!ciudad.isDestruido())
				cantidadCiudades++;
		return cantidadCiudades;
	}

	/**
	 * Devuelve la cantidad de misiles en los silos
	 * 
	 * @return cantidad de misiles antiaéreos disponibles en los silos
	 */
	int getCantidadMisiles() {
		int misiles = 0;
		for (Silo s : silos)
			misiles += s.getMisilesDisponibles();
		return misiles;
	}

	public Ciudad[] getCiudades() {
		return ciudades;
	}

	Silo getSiloActivo() {
		return silos[siloActual];
	}

	public Silo[] getSilos() {
		return this.silos;
	}

	public int getUmbralMisiles() {
		return umbralMisiles;
	}
}
