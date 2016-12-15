package ar.edu.unlp.info.missilecommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ar.edu.unlp.info.missilecommand.moviles.MovilAliado;
import ar.edu.unlp.info.missilecommand.moviles.MovilEnemigo;
import ar.edu.unlp.info.missilecommand.terrestres.Ciudad;
import ar.edu.unlp.info.missilecommand.terrestres.Silo;

/**
 * Clase principal que controla todos los aspectos lógicos del juego. Tiene como
 * responsabilidad principal contener, controlar y actualizar los elementos del
 * juego.
 */
public class Juego {
	/**
	 * Fotogramas por segundo
	 */
	public final static int FPS = 60;

	public static Juego getInstancia() {
		if (instanciaUnica == null) {
			instanciaUnica = new Juego();
		}
		return instanciaUnica;
	}

	/**
	 * Hace la conversion de un valor en unidades pixel/segundo a
	 * pixel/fotograma.
	 * 
	 * @param tasaPixelPorSegundo
	 *            valor a convertir.
	 * @return Valor convertido a pixel/fotograma.
	 */
	public static double pixelPorSegundoAPixelPorFotograma(
			double tasaPixelPorSegundo) {
		return tasaPixelPorSegundo / FPS;
	}

	static private Juego instanciaUnica = null;
	private boolean pausado;
	
	private boolean huboBonus = false;

	/**
	 * Colección de no enemigos agregar en cada fotograma.
	 */
	private Collection<MovilAliado> aliadosParaAgregar;

	/**
	 * Colección de no enemigos a borrar de Juego.
	 */
	private Collection<MovilAliado> aliadosParaBorrar;

	private int countdown;

	/**
	 * Coleccion de objetos dibujables que pueden colisionar, no enemigos.
	 */
	private Collection<MovilAliado> elementosAliados;

	/**
	 * Coleccion de objetos de tipo móviles Y que son enemigos
	 */
	private Collection<MovilEnemigo> elementosEnemigos;

	/**
	 * Coleccion de objetos dibujables que NO pueden colisionar ni moverse
	 */
	private Collection<Elemento> elementosEstacionarios;
	/**
	 * Colección de enemigos a agregar en cada fotograma.
	 */
	private Collection<MovilEnemigo> enemigosParaAgregar;
	/**
	 * Colección de enemigos a borrar de Juego.
	 */
	private Collection<MovilEnemigo> enemigosParaBorrar;

	private Escenario escenario;

	private int fotogramaActual = 0;
	/**
	 * En cual nivel empezar
	 */
	private int nivelInicial = 1;
	/**
	 * Colección de ondas expansivas.
	 */
	private Collection<OndaExpansiva> ondasExpansivas;
	/**
	 * Colección de ondas expansivas a agregar en cada fotograma.
	 */
	private Collection<OndaExpansiva> ondasExpansivasParaAgregar;
	/**
	 * Colección de ondas expansivas a eliminar en cada fotograma.
	 */
	private Collection<OndaExpansiva> ondasExpansivasParaEliminar;
	private Partida partida;

	/*
	 * Se usan colas de eliminación porque no se puede borrar durante un
	 * for-each
	 */

	/**
	 * Puntaje total desde el comienzo del juego.
	 */
	private int puntajeTotal = 0;

	private Puntero puntero;

	// patrón Singleton
	private Juego() {
		escenario = new Escenario(this);
		partida = new Partida(this, nivelInicial, escenario);
	}

	/*
	 * Los elementos enemigos y no enemigos estan separados para que solo se
	 * chequen las colisiones entre sí.
	 */

	/**
	 * Actualiza el estado de todos los elementos.
	 * 
	 */
	private void actualizar() {
		escenario.actualizar();
		for (Elemento item : elementosEstacionarios)
			item.actualizar();

		for (Elemento item : elementosEnemigos)
			item.actualizar();

		for (Elemento item : elementosAliados)
			item.actualizar();

		for (Elemento item : ondasExpansivas)
			item.actualizar();

		chequearColisiones();
		actualizarColecciones();
	}

	/**
	 * Este metodo borra los elementos que hayan requerido su eliminación ya que
	 * no se puede hacerlo durante el for-each y agrega los nuevos.
	 * 
	 */
	private void actualizarColecciones() {
		elementosEnemigos.removeAll(enemigosParaBorrar);
		elementosEnemigos.addAll(enemigosParaAgregar);
		elementosAliados.removeAll(aliadosParaBorrar);
		elementosAliados.addAll(aliadosParaAgregar);
		ondasExpansivas.removeAll(ondasExpansivasParaEliminar);
		ondasExpansivas.addAll(ondasExpansivasParaAgregar);
		enemigosParaBorrar.clear();
		enemigosParaAgregar.clear();
		aliadosParaBorrar.clear();
		aliadosParaAgregar.clear();
		ondasExpansivasParaEliminar.clear();
		ondasExpansivasParaAgregar.clear();
	}

	/**
	 * Chequea colisiones entre enemigos y aliados.
	 * 
	 */
	private void chequearColisiones() {

		for (MovilEnemigo enemigo : elementosEnemigos) {
			for (MovilAliado afin : elementosAliados) {
				if (afin.colisionaCon(enemigo)) {
					afin.recibirAtaque();
					enemigo.recibirAtaque();
					partida.sumarDerribado(enemigo);
				}
			}
			for (OndaExpansiva onda : ondasExpansivas) {
				if (onda.colisionaCon(enemigo)) {
					enemigo.recibirAtaque();
					partida.sumarDerribado(enemigo);
				}
			}
			for (Ciudad ciudad : escenario.getCiudades()) {
				if (ciudad.colisionaCon(enemigo)) {
					ciudad.recibirAtaque();
					enemigo.recibirAtaque();
				}
			}
			for (Silo silo : escenario.getSilos()) {
				if (silo.colisionaCon(enemigo)) {
					silo.recibirAtaque();
					enemigo.recibirAtaque();
				}
			}
		}
	}

	/**
	 * Devuelve si countdown llego a cero o no
	 * 
	 * 
	 * @return True si countdown llego a cero, false si no.
	 * @see restarCountdown
	 */
	private boolean countdownLlegado() {
		if (countdown == 0) {
			/* System.out.println("Countdown 0!"); */
			return true;
		} else
			return false;
	}

	/**
	 * Setea el contador countdown.
	 * 
	 * @param segundos
	 *            Tiempo en segundos.
	 */
	private void setContadorCountdownSegundos(int segundos) {
		countdown = segundos * Juego.FPS;
	}

	private void restarCountdown() {
		assert (countdown >= 0);
		if (countdown > 0)
			countdown--;
	}

	/**
	 * Arranca una nueva partida en el nivel indicado por defecto. El juego
	 * termina al terminar el nivel si Test es true, si no, continua
	 * normalmente. Despues de esto se debe llamar a {@link #avanzarCiclo()}
	 */
	public void correr() {
		System.gc();
		huboBonus = false;
		fotogramaActual = 0;
		puntajeTotal = 0;
		escenario.resetSilos();
		escenario.resetCiudades(false);
		
		puntero = new Puntero();
		partida.prepararNivel(nivelInicial, false);
		vaciarContenedores();
		escenario.setRapidezAntiaereos(partida.getRapidezAntiaereos());
		setContadorCountdownSegundos(1);
	}

	private void vaciarContenedores() {
		elementosEstacionarios = new ArrayList<Elemento>();
		elementosAliados = new HashSet<MovilAliado>();
		elementosEnemigos = new HashSet<MovilEnemigo>();
		enemigosParaBorrar = new ArrayList<MovilEnemigo>(4);
		aliadosParaBorrar = new ArrayList<MovilAliado>(4);
		aliadosParaAgregar = new ArrayList<MovilAliado>(4);
		enemigosParaAgregar = new ArrayList<MovilEnemigo>(4);
		ondasExpansivas = new HashSet<OndaExpansiva>();
		ondasExpansivasParaAgregar = new ArrayList<OndaExpansiva>(4);
		ondasExpansivasParaEliminar = new ArrayList<OndaExpansiva>(4);
	}

	/**
	 * Avanza un fotograma del juego, ejecutando toda la lógica y las
	 * animaciones. Si ya termino el nivel, no hace nada y retorna.
	 */
	public void avanzarCiclo() {
		if (terminoNivel())
			return;
		/*
		 * La generación de enemigos se regula con un contador. Cada vez que
		 * esté se vence, se generan los enemigos y se lo resetea.
		 */
		if (countdownLlegado()) {
			partida.generarEnemigos();
			setContadorCountdownSegundos(3 + (int) (6 * Math.random()));
		}
		restarCountdown();
		actualizar();

		this.fotogramaActual++;
	}

	/**
	 * Pide la eliminación de un elemento móvil afín.
	 * 
	 * @param elemento
	 *            elemento de tipo Movil a agregar a colección. * @see
	 * @see #actualizarColecciones()
	 */
	public void eliminarMovil(MovilAliado elemento) {
		this.aliadosParaBorrar.add(elemento);
	}

	/**
	 * Pide la eliminación de un elemento enemigo.
	 * 
	 * @param elementoEnemigo
	 *            elemento de tipo Movil a eliminar de colección.
	 * @see #actualizarColecciones()
	 */
	public void eliminarMovilEnemigo(MovilEnemigo elementoEnemigo) {
		this.enemigosParaBorrar.add(elementoEnemigo);
	}

	/**
	 * Elimina una OndaExpansiva.
	 * 
	 * @param ondaExpansiva
	 *            OndaExpansiva a eliminar
	 * @see #actualizarColecciones()
	 */
	public void eliminarOndaExpansiva(OndaExpansiva ondaExpansiva) {
		this.ondasExpansivasParaEliminar.add(ondaExpansiva);
		System.out.println(this.getFotogramaActual()
				+ " Onda expansiva dispersada - pos:"
				+ ondaExpansiva.getPosicion().toString());

	}

	/**
	 * Agrega un elemento estacionario
	 * 
	 * @param elemento
	 *            elemento de tipo Movil a agregar a colección.
	 * @see #actualizarColecciones()
	 */
	/*
	 * void registrarDibujable(Elemento elemento) {
	 * this.elementosEstacionarios.add(elemento); }
	 */

	/**
	 * Agrega un elemento móvil
	 * 
	 * @param elemento
	 *            elemento de tipo Movil a agregar a colección.
	 * @see #actualizarColecciones()
	 */
	public void registrarMovil(MovilAliado elemento) {
		this.aliadosParaAgregar.add(elemento);
		System.out.println(this.getFotogramaActual()
				+ " Nuevo aliado registrado: " + elemento);
	}

	/**
	 * Agrega un elemento enemigo móvil
	 * 
	 * @param elementoEnemigo
	 *            elemento de tipo Movil a agregar a colección.
	 * @see #actualizarColecciones()
	 */
	public void registrarMovilEnemigo(MovilEnemigo elementoEnemigo) {
		this.enemigosParaAgregar.add(elementoEnemigo);
		System.out.println(this.getFotogramaActual()
				+ " Nuevo enemigo registrado: " + elementoEnemigo);
	}

	/**
	 * Agrega una OndaExpansiva
	 * 
	 * @param ondaExpansiva
	 *            instancia de tipo OndaExpansiva.
	 * @see #actualizarColecciones()
	 */
	public void registrarOndaExpansiva(OndaExpansiva ondaExpansiva) {
		this.ondasExpansivasParaAgregar.add(ondaExpansiva);
		System.out.println(this.getFotogramaActual()
				+ " Onda expansiva registrada - pos:"
				+ ondaExpansiva.getPosicion().toString());

	}

	/**
	 * @return True si termino. False, si no.
	 */
	public boolean terminoNivel() {
		if (partida.isTodoEnemigoLanzado() && elementosEnemigos.isEmpty()
				&& ondasExpansivas.isEmpty())
			return true;
		else
			// Sigue el juego
			return false;
	}

	/**
	 * @return True si la cantidad de ciudades en pie es cero.
	 */
	public boolean terminoJuego() {
		return escenario.getCantidadCiudades() == 0;
	}

	/**
	 * Avanza de nivel. Prepara una partida con un nivel siguiente o un nivel
	 * bonus si corresponde.
	 */
	public void avanzarNivel() {
		if (!terminoNivel())
			throw new IllegalStateException();
		if (terminoJuego() && hayBonus()) {
			partida.irABonus();
			huboBonus = true;
		}
		else
			partida.avanzarNivel();
		partida.reset();
	}

	public Collection<MovilAliado> getElementosNoEnemigos() {
		return elementosAliados;
	}

	public Collection<MovilEnemigo> getElementosEnemigos() {
		return elementosEnemigos;
	}

	public Collection<OndaExpansiva> getOndasExpansivas() {
		return ondasExpansivas;
	}

	public Escenario getEscenario() {
		return this.escenario;
	}

	public Partida getPartida() {
		return partida;
	}

	public Puntero getPuntero() {
		return puntero;
	}

	public int getNumFotograma() {
		return fotogramaActual;
	}

	public String getFotogramaActual() {
		return "[Instante: " + fotogramaActual + "]";
	}

	public boolean hayBonus() {
		if (!terminoNivel())
			throw new IllegalStateException();
		return (puntajeTotal >= 10000) && !huboBonus;
	}

	public boolean isPausado() {
		return pausado;
	}

	public void setNivelPorDefecto(int nivelPorDefecto) {
		this.nivelInicial = nivelPorDefecto;
	}

	public void setPausado(boolean pausado) {
		this.pausado = pausado;
	}

	/**
	 * Suma al puntaje total el puntaje de la ultima partida terminada.
	 * @throws IllegalStateException si no termino la partida.
	 */
	public void acumularPuntaje() {
		if(!terminoNivel())
			throw new IllegalStateException("El nivel tiene que haber terminado.");
		puntajeTotal += partida.getPuntajeNormal();
		puntajeTotal += partida.getPuntajeBonus();
	}
	
	
	public int getPuntajeTotal() {
		return puntajeTotal;
	}
	


}