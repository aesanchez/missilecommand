package ar.edu.unlp.info.missilecommand;

import java.io.PrintStream;

import ar.edu.unlp.info.missilecommand.moviles.Avion;
import ar.edu.unlp.info.missilecommand.moviles.Bombardero;
import ar.edu.unlp.info.missilecommand.moviles.MisilAntiaereo;
import ar.edu.unlp.info.missilecommand.moviles.MisilBalisticoInterplanetario;
import ar.edu.unlp.info.missilecommand.moviles.MisilCrucero;
import ar.edu.unlp.info.missilecommand.moviles.MisilCruceroInteligente;
import ar.edu.unlp.info.missilecommand.moviles.MisilCruceroTonto;
import ar.edu.unlp.info.missilecommand.moviles.MovilEnemigo;
import ar.edu.unlp.info.missilecommand.moviles.Satelite;
import ar.edu.unlp.info.missilecommand.terrestres.Ciudad;

/**
 * Clase encargada de mantener toda la información propia de una partida. (Su
 * transcurso) y de la generacion de enemigos. Una partida termina con la
 * destrucción de todas las ciudades.
 */
public class Partida {

	/**
	 * Cantidad de bombarderos restantes por lanzar
	 */
	private int bombarderos;
	private int bombarderosDerribados;

	private Escenario escenario;
	private int misilesBalisticosDerribados;
	/**
	 * Cantidad de misiles cruceros restantes por lanzar
	 */
	private int misilesCruceros;
	private int misilesCrucerosDerribados;

	/**
	 * Número de nivel actual
	 */
	/**
	 * Cantidad de misiles restantes porlanzar
	 */
	private int misilesEnemigos;
	/**
	 * Referencia a la instancia de nivel actual.
	 */
	private Nivel nivel;
	final private Juego padre;

	public Partida(Juego padre, int nroNivel, Escenario escenario) {
		this.padre = padre;
		this.escenario = escenario;
		this.nivel = new Nivel(nroNivel);
		misilesEnemigos = nivel.getMisilesEnemigos();
		misilesCruceros = nivel.getMisilesCruceros();
		bombarderos = nivel.getBombarderos();

	}

	/**
	 * Avanza de nivel o avanza al bonus si bonus es true.
	 * 
	 * @param bonus
	 *            True para ir a bonus, false para avanzar de nivel.
	 */
	private void avanzarNivel(boolean bonus) {
		if (bonus)
			prepararNivel(nivel.getNumero(), true);
		else
			prepararNivel(nivel.getNumero() + 1, false);
	}

	private boolean usarBombarderos() {
		assert (bombarderos >= 0);
		if (bombarderos > 0) {
			bombarderos--;
			return true;
		} else
			return false;
	}

	private boolean usarMisilesCruceros() {
		assert (misilesCruceros >= 0);
		if (misilesCruceros > 0) {
			misilesCruceros--;
			return true;
		} else
			return false;
	}

	private int usarMisilesEnemigos(int cantidad) {
		assert (misilesEnemigos >= 0);
		if (cantidad > misilesEnemigos) {
			int aux = misilesEnemigos;
			misilesEnemigos = 0;
			return aux;
		} else {
			assert (cantidad <= misilesEnemigos);
			misilesEnemigos -= cantidad;
			assert (misilesEnemigos >= 0);
			return cantidad;
		}
	}

	/**
	 * Genera una oleada de enemigos
	 */
	void generarEnemigos() {

		/*
		 * Crear misiles interplanetarios
		 */
		final boolean misilesBifurcan = nivel.misilesBifurcan();
		final int cantidadMisiles = usarMisilesEnemigos(2 + (int) (Math
				.random() * 3));
		for (int i = 1; i <= cantidadMisiles; i++) {
			final double posicionX = escenario.getAncho() * Math.random();
			MisilBalisticoInterplanetario misil = new MisilBalisticoInterplanetario();
			misil.setPosicion(new Vector2D(posicionX, -5));
			misil.setDestino(escenario.obtenerTerrestreRandom()
					.getPosicionCentrada(), nivel.getRapidezEnemigos());
			if (misilesBifurcan && Math.random() > 0.80) {
				misil.setBifurcable(true);
			}
			padre.registrarMovilEnemigo(misil);
		}

		/*
		 * Crear misiles cruceros
		 */
		if (usarMisilesCruceros()) {
			final double posicionX = escenario.getAncho() * Math.random();
			MisilCrucero misil;
			final double rapidez = nivel.getRapidezEnemigos();

			if (nivel.hayMisilesCrucerosInteligentes())
				misil = new MisilCruceroInteligente();
			else
				misil = new MisilCruceroTonto();

			// Tiene que aparecer justo arriba de la pantalla.
			misil.setPosicion(new Vector2D(posicionX, (-1) * misil.getAlto()));
			misil.setDestino(escenario.obtenerTerrestreRandom()
					.getPosicionCentrada(), rapidez);
			padre.registrarMovilEnemigo(misil);

		}
		/*
		 * Crear aviones y satelites
		 */
		if (usarBombarderos()) {
			Bombardero bombardero = (Math.random() > 0.5) ? new Avion()
					: new Satelite();
			final double posicionX = (Math.random() > 0.5) ? (-1)
					* bombardero.getAncho() : escenario.getAncho();

			double aux = nivel.getNumero();
			if (aux > 10)
				aux = 10;

			final double posicionY = (escenario.getAlto() / 3) + 8 * aux;

			bombardero.setPosicion(new Vector2D(posicionX, posicionY));
			bombardero.setRapidez(nivel.getRapidezEnemigos() / 2);
			bombardero.setBombardeoActivo(nivel.bombardeoActivo());
			bombardero.setAnchoRango(escenario.getAncho());
			bombardero.inicializar();

			padre.registrarMovilEnemigo(bombardero);
		}

	}

	void imprimirReporte() {
		assert (bombarderos >= 0);
		assert (misilesBalisticosDerribados >= 0);
		assert (misilesCrucerosDerribados >= 0);
		PrintStream salida = System.out;
		salida.println("*** REPORTE DE PARTIDA! ***");
		salida.println("	Ciudades en pie: " + escenario.getCantidadCiudades());

		salida.println("	Misiles enemigos interceptados (incluyendo bifurcaciones): "
				+ misilesBalisticosDerribados);
		salida.println("	Misiles cruceros interceptados: "
				+ misilesCrucerosDerribados);
		salida.println("	Bombarderos interceptados: " + bombarderosDerribados);
		salida.println("	Misiles antiaereos restantes: "
				+ escenario.getCantidadMisiles());

		salida.println("	Puntaje hasta ahora: " + getPuntajeNormal());
		salida.println("*** FIN REPORTE DE PARTIDA ***\n");

	}

	/**
	 * Recibe un elemento enemigo y incrementa contadores correspondientes al
	 * tipo de MovilEnemigo. Si se manda un tipo de MovilEnemigo no considerado,
	 * simplemente no se hace nada
	 * 
	 * @param enemigo
	 *            Elemento enemigo
	 */
	void sumarDerribado(MovilEnemigo enemigo) {
		if (enemigo instanceof MisilCrucero)
			misilesCrucerosDerribados++;
		else if (enemigo instanceof MisilBalisticoInterplanetario)
			misilesBalisticosDerribados++;
		else if (enemigo instanceof Bombardero)
			bombarderosDerribados++;
	}

	public void reset() {
		misilesEnemigos = nivel.getMisilesEnemigos();
		misilesCruceros = nivel.getMisilesCruceros();
		bombarderos = nivel.getBombarderos();
	}

	/**
	 * Avanza a un nivel bonus
	 */
	public void irABonus() {
		this.avanzarNivel(true);

	}

	/**
	 * Avanza de nivel, bonus o no basado en el resultado de la partida
	 */
	public void avanzarNivel() {
		if (this.getPuntajeNormal() > 10000)
			avanzarNivel(true);
		else
			avanzarNivel(false);
	}

	/**
	 * Prepara una nueva partida en un nivel dado.
	 * 
	 * @param nivel
	 *            Nivel al cual jugar.
	 * @param bonus
	 *            True si el nivel es bonus, false si no.
	 */
	public void prepararNivel(int nivel, boolean bonus) {
		bombarderosDerribados = 0;
		misilesBalisticosDerribados = 0;
		misilesCrucerosDerribados = 0;
		escenario.resetSilos();
		if (bonus) {
			escenario.resetCiudades(true);
			this.nivel = new Nivel(nivel, true);
		} else
			this.nivel = new Nivel(nivel, false);
	}

	/**
	 * @return True si se lanzaron todos los enemigos.
	 */
	public boolean isTodoEnemigoLanzado() {
		assert (bombarderos >= 0 && misilesCruceros >= 0 && misilesEnemigos >= 0);
		return (bombarderos + misilesCruceros + misilesEnemigos == 0);
	}

	public boolean isBonus() {
		return nivel.isBonus();
	}

	public Nivel getNivel() {
		return nivel;
	}

	/**
	 * Calcula el puntaje actual de la partida. Sin contar los puntajes bonus
	 * (ciudades en pie y misiles restantes)
	 * 
	 * @return El puntaje de la partida
	 */
	public int getPuntajeNormal() {
		assert (misilesCrucerosDerribados >= 0);
		assert (misilesBalisticosDerribados >= 0);

		return ((misilesCrucerosDerribados * MisilCrucero.PUNTAJE) + (misilesBalisticosDerribados * MisilBalisticoInterplanetario.PUNTAJE));
	}

	public int getPuntajeBonus() {
		return escenario.getCantidadCiudades() * Ciudad.PUNTAJE
				+ escenario.getCantidadMisiles() * MisilAntiaereo.PUNTAJE;
	}

	public double getRapidezAntiaereos() {
		return nivel.getRapidezAntiaereos();
	}

	public double getRapidezEnemigos() {
		return nivel.getRapidezEnemigos();
	}

	public PuntajePartida getPuntajes() {
		if (!padre.terminoNivel())
			throw new IllegalStateException();
		return new PuntajePartida(misilesCrucerosDerribados,
				misilesBalisticosDerribados, escenario.getCantidadCiudades(),
				escenario.getCantidadMisiles());
	}

	/**
	 * Contenedor de los puntajes ESTRICTAMENTE AL FINAL de un nivel.
	 */
	public class PuntajePartida {
		public final int misilesCrucerosDerribados;
		public final int misilesBalisticosDerribados;
		/**
		 * Cantidad de ciudades en pie
		 */
		public final int cantidadCiudades;
		public final int cantidadMisilesRestantes;

		PuntajePartida(int mci, int mbi, int ciudades, int misiles) {
			misilesCrucerosDerribados = mci;
			misilesBalisticosDerribados = mbi;
			cantidadCiudades = ciudades;
			cantidadMisilesRestantes = misiles;
		}

	}

}