package ar.edu.unlp.info.missilecommand;

import java.io.PrintStream;
import java.util.Locale;

/**
 * Clase que representa un nivel en particular. Se encarga de generar los
 * aspectos únicos a cada nivel. NO mantiene datos del transcurso de una
 * partida. Esta clase es inmutable.
 */
public final class Nivel {
	/**
	 * Cantidad de bombarderos a lanzarse en este nivel.
	 */
	private int bombarderos;

	final private boolean bonus;

	/**
	 * Cantidad de misiles cruceros a lanzarse en este nivel.
	 */
	private int misilesCruceros;

	/**
	 * Cantidad de misiles enemigos a lanzarse en este nivel.
	 */
	private int misilesEnemigos;

	private int numeroDeNivel;

	/**
	 * Indica rapidez aumentada de misiles antiaereos.
	 */
	private boolean rapidaIndicacion;

	/**
	 * Rapidez en píxeles por segundo para los misiles antiaéreos.
	 */
	private double rapidezAntiaereos;
	/**
	 * Rapidez en píxeles por segundo para los enemigos.
	 */
	private double rapidezEnemigos;
	/**
	 * True si hay misiles cruceros inteligentes, false si hay misiles cruceros
	 * tontos.
	 */
	private boolean tieneCrucerosInteligentes;

	/**
	 * Constructor por default. Asume nivel no bonus.
	 * 
	 * @param nroNivel
	 *            Número de nivel.
	 */
	Nivel(int nroNivel) {
		this(nroNivel, false);
	}

	/**
	 * Constructor.
	 * 
	 * @param nivel
	 *            Numero de nivel
	 * @param bonus
	 *            True si es bonus, false si no.
	 */
	Nivel(int nivel, boolean bonus) {

		if (nivel < 1)
			throw new IllegalArgumentException();

		this.bonus = bonus;
		numeroDeNivel = nivel;
		/*
		 * solo lectura
		 */
		misilesEnemigos = (6 + (int) (Math.random() * ((17 - 12) + 1)));
		bombarderos = (int) (3.0d * Math.random());
		misilesCruceros = 2 + (int) (3.0d * Math.random());
		rapidezEnemigos = 40 + (this.numeroDeNivel * 5);
		rapidezAntiaereos = rapidezEnemigos * 15d;
		if (bonus) {

			rapidaIndicacion = false;
			tieneCrucerosInteligentes = false;

		} else { // Si no es un bonus es un nivel normal

			if (numeroDeNivel % 2 == 0)
				rapidaIndicacion = false;
			else
				rapidaIndicacion = true;

			/*
			 * Para controlar el funcionamiento de los elementos de nivel que
			 * aparecen en 2 sí, 2 no, 2 sí, 2 no...etc
			 */
			int casosNivel = (this.numeroDeNivel % 4) + 1;
			switch (casosNivel) {
			case 1:
			case 2:
				tieneCrucerosInteligentes = false;
				break;
			case 3:
			case 4:
				tieneCrucerosInteligentes = true;
				break;
			}
		}
		System.out.println("Nivel " + numeroDeNivel + " instanciado!:");
		imprimirReporte();

	}

	boolean bombardeoActivo() {
		return numeroDeNivel > 1;
	}

	int getBombarderos() {
		return bombarderos;
	}

	int getMisilesCruceros() {
		return misilesCruceros;
	}

	int getMisilesEnemigos() {
		return misilesEnemigos;
	}

	public int getNumero() {
		return numeroDeNivel;
	}

	/**
	 * Devuelve la rapidez para los antiaereos. Si este nivel tiene rápida
	 * indicación, devuelve una rapidez mas alta.
	 * 
	 * @return Rapidez en pixel por fotograma.
	 */
	double getRapidezAntiaereos() {
		return (rapidaIndicacion) ? 2 * this.rapidezAntiaereos
				: this.rapidezAntiaereos;
	}

	double getRapidezEnemigos() {
		return rapidezEnemigos;
	}

	/**
	 * Devuelve si pueden o no haber misiles cruceros inteligentes
	 * 
	 * @return True si pueden haber, false si no.
	 */
	boolean hayMisilesCrucerosInteligentes() {
		return tieneCrucerosInteligentes;
	}

	/**
	 * Imprime un reporte de los enemigos y los puntajes de este nivel.
	 */
	void imprimirReporte() {
		PrintStream salida = System.out;
		salida.println("*** Características del nivel ***");
		salida.println("	Nivel #" + numeroDeNivel);
		System.out.println("	Rapidez enemigos: "
				+ String.format(Locale.ENGLISH, "%.2f", rapidezEnemigos)
				+ "(px/s)");
		salida.println("	Total de misiles enemigos por lanzar: "
				+ misilesEnemigos);
		salida.println("	Total de misiles cruceros por lanzar: "
				+ misilesCruceros);
		salida.println("	Total de bombarderos  por lanzar: " + bombarderos);
		salida.println("	¿Pueden haber cruceros inteligentes?: "
				+ tieneCrucerosInteligentes);
		salida.println("	¿Hay rápida indicacion?: " + rapidaIndicacion);
		salida.println("	¿Es bonus?: " + bonus);
		salida.println("*** Comienza el nivel *** \n");
	}

	public boolean misilesBifurcan() {
		return this.numeroDeNivel > 1;
	}

	public boolean isBonus() {
		return bonus;
	}
}