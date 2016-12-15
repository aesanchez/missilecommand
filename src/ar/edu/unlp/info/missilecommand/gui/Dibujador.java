package ar.edu.unlp.info.missilecommand.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Graphics2D;

import ar.edu.unlp.info.missilecommand.Elemento;
import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.Estela;
import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;
import ar.edu.unlp.info.missilecommand.Puntero;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.moviles.*;
import ar.edu.unlp.info.missilecommand.terrestres.*;

/**
 * Clase que se encarga de dibujar elementos gráficos sobre un JPanel.
 */
public class Dibujador {

	public static final int tiempoPuntajes = 5 * Juego.FPS;

	/**
	 * Modos de dibujos, para cada una de las diferentes "pantallas" del juego.
	 */
	public enum ModosDibujo {
		JUEGO_PRINCIPAL, RESULTADO_PUNTAJES, PRELUDIO, THE_END
	};

	final private ContenedorDeImagenes repositorioImagenes;

	private boolean resaltaLimites = false;

	private Graphics2D contextoGrafico;

	private Juego juego;

	public Dibujador(Juego juego) {
		this.repositorioImagenes = new ContenedorDeImagenes();
		this.juego = juego;
	}

	private void dibujar(Elemento elemento) {
		Image[] imagen = repositorioImagenes.obtenerImagenes(elemento);
		if (resaltaLimites) {
			dibujarBorde(elemento);
		}
		contextoGrafico.drawImage(imagen[0],
				(int) Math.round(elemento.getPosicion().getX()),
				(int) (elemento.getPosicion().getY()), (elemento.getAncho()),
				(elemento.getAlto()), null);

	}

	private void dibujarEstela(Estela estela) {

		contextoGrafico.setColor(new Color(197, 197, 197, 60));
		contextoGrafico.setStroke(new BasicStroke(2));
		final Vector2D posicionEmisor = estela.getEmisor()
				.getPosicionCentrada();
		contextoGrafico.drawLine((int) estela.getOrigen().getX(), (int) estela
				.getOrigen().getY(), (int) posicionEmisor.getX(),
				(int) posicionEmisor.getY());
	}

	private void dibujarBorde(Elemento elemento) {
		contextoGrafico.setColor(new Color(0, 255, 0, 255));
		contextoGrafico.setStroke(new BasicStroke(1));
		contextoGrafico.drawRect(
				(int) Math.round(elemento.getPosicion().getX()),
				(int) (elemento.getPosicion().getY()), (elemento.getAncho()),
				(elemento.getAlto()));
	}

	public void toggleResaltaLimites() {
		resaltaLimites = !resaltaLimites;
	}

	public void dibujar(Escenario elemento) {
		dibujar((Elemento) elemento);
		contextoGrafico.setColor(new Color(197, 197, 197, 15));
		contextoGrafico.setStroke(new BasicStroke(2));
		contextoGrafico.drawLine(0, elemento.getUmbralMisiles(),
				elemento.getAncho(), elemento.getUmbralMisiles());
	}

	public void dibujar(MisilBalisticoInterplanetario elemento) {
		dibujarEstela(elemento.getEstela());
		dibujar((Elemento) elemento);
	}

	public void dibujar(MisilAntiaereo elemento) {
		dibujarEstela(elemento.getEstela());
		dibujar((Elemento) elemento);
		// dibujar cruz
		final int x = (int) elemento.getDestino().getX();
		final int y = (int) elemento.getDestino().getY();
		final int aux = 5;
		contextoGrafico.setColor(new Color(255, 255, 255, 100));
		contextoGrafico.setStroke(new BasicStroke(2));
		contextoGrafico.drawLine(x - aux, y - aux, x + aux, y + aux);
		contextoGrafico.drawLine(x - aux, y + aux, x + aux, y - aux);
	}

	public void dibujar(Ciudad ciudad) {
		if (!ciudad.isDestruido())
			dibujar((Elemento) ciudad);
	}

	public void dibujar(Silo silo) {
		if (!silo.isDestruido()) {
			int img = 0;
			Image[] imagen = repositorioImagenes.obtenerImagenes(silo);
			if (silo.isActivo())
				img = 1;
			contextoGrafico.drawImage(imagen[img],
					(int) Math.round(silo.getPosicion().getX()),
					(int) (silo.getPosicion().getY()), (silo.getAncho()),
					(silo.getAlto()), null);

			if (resaltaLimites)
				dibujarBorde(silo);

			// numero de misiles

			final int tamanoFuente = 25;
			final int misilesDisponibles = silo.getMisilesDisponibles();
			Font f = new Font("Digital-7", Font.PLAIN, tamanoFuente);
			contextoGrafico.setFont(f);
			if (misilesDisponibles > 0)
				contextoGrafico.setColor(new Color(255, 255, 255, 100));
			else
				contextoGrafico.setColor(Color.RED);
			contextoGrafico.drawString(
					String.format("%02d", misilesDisponibles), (int) silo
							.getPosicionCentrada().getX() - tamanoFuente / 2,
					(int) silo.getPosicion().getY() - 2);
		}
	}

	public void dibujar(OndaExpansiva elemento) {

		Image[] imagen = repositorioImagenes.obtenerImagenes(elemento);
		final int x = (int) elemento.getPosicion().getX();
		final int y = (int) elemento.getPosicion().getY();
		final int radio = (int) (OndaExpansiva.RADIO_MAX * 1.30);
		final int fotograma;
		float phi = elemento.getPhi();
		if (phi < Math.PI / 2) {
			fotograma = (int) Math.floor(Math.sin(phi / 2) * imagen.length);
			contextoGrafico.drawImage(imagen[fotograma], x - radio, y - radio,
					2 * radio, 2 * radio, null);
			if (elemento.isColisionable() && this.resaltaLimites) {
				contextoGrafico.setColor(new Color(0, 255, 0, 255));
				contextoGrafico.setStroke(new BasicStroke(1));
				contextoGrafico.drawOval(x - elemento.getAncho(),
						y - elemento.getAncho(),
						(int) (2 * elemento.getRadioActual()),
						(int) (2 * elemento.getRadioActual()));
			}
		}
	}

	public void dibujar(Puntero elemento) {
		Image[] imagen = repositorioImagenes.obtenerImagenes(elemento);
		Vector2D posicion = elemento.getPosicion();
		contextoGrafico.drawImage(imagen[0], (int) posicion.getX(),
				(int) posicion.getY(), elemento.getAncho(), elemento.getAlto(),
				null);
	}

	public void dibujar(Avion avion) {
		Image[] imagen = repositorioImagenes.obtenerImagenes(avion);
		Vector2D posicion = avion.getPosicion();
		final int flipHorizontal = (avion.getSentido() == Bombardero.Sentido.DERECHA) ? 1
				: -1;
		contextoGrafico.drawImage(imagen[0], (int) posicion.getX()
				+ ((flipHorizontal == -1) ? avion.getAncho() : 0),
				(int) posicion.getY(), flipHorizontal * avion.getAncho(),
				avion.getAlto(), null);
		if (resaltaLimites)
			dibujarBorde(avion);
	}

	public void dibujar(Satelite elemento) {
		dibujar((Elemento) elemento);
	}

	public void dibujar(MisilCruceroTonto misil) {
		dibujar((Elemento) misil);
	}

	public void dibujar(MisilCruceroInteligente misil) {
		dibujar((Elemento) misil);
		if (resaltaLimites)
			contextoGrafico.drawArc(
					(int) (misil.getPosicionCentrada().getX() - (int) misil
							.getRadioRadar() / 2), (int) (misil.getPosicion()
							.getY() - misil.getAlto()), (int) misil
							.getRadioRadar(), (int) misil.getRadioRadar(), 180,
					180);
	}

	/**
	 * Dibuja la pantalla segun el modo pasado.
	 * 
	 * @param g
	 *            El contexto gráfico donde dibujar.
	 * @param modo
	 *            El modo en que dibujar.
	 */
	public void dibujar(Graphics g, Dibujador.ModosDibujo modo) {
		this.contextoGrafico = (Graphics2D) g;
		contextoGrafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		switch (modo) {
		case JUEGO_PRINCIPAL:
			dibujarJuego();
			break;
		case PRELUDIO:
			dibujarCartel();
			break;
		case RESULTADO_PUNTAJES:
			dibujarPuntajes();
			break;
		case THE_END:
			dibujarFin();
			break;
		default:
			throw new IllegalArgumentException("Se esperaba un modo!");
		}

		contextoGrafico = null;
	}

	private void dibujarFin() {
		assert (contextoGrafico != null);
		juego.getEscenario().serDibujado(this);
		for (Terrestre e : juego.getEscenario().getCiudades())
			e.serDibujado(this);
		for (Terrestre e : juego.getEscenario().getSilos())
			e.serDibujado(this);
		contextoGrafico.drawImage(
				repositorioImagenes.obtenerImagenes("theEnd")[0], 0, 0, null);

	}

	
	private int contadorPuntajes;
	private void dibujarPuntajes() {
		assert (contextoGrafico != null);
		juego.getEscenario().serDibujado(this);
		for (Terrestre e : juego.getEscenario().getCiudades())
			e.serDibujado(this);
		for (Terrestre e : juego.getEscenario().getSilos())
			e.serDibujado(this);
		juego.getPuntero().serDibujado(this);

		contextoGrafico.setColor(Color.WHITE);
		contextoGrafico.setFont(new Font(contextoGrafico.getFont()
				.getFontName(), contextoGrafico.getFont().getStyle(), 25));

		int x = Escenario.ANCHO / 5;
		int y = Escenario.ALTO / 8;

		FontMetrics fm = contextoGrafico.getFontMetrics();

		// Cabecera puntajes centrada
		String puntajes = "PUNTAJE BONUS";
		contextoGrafico.drawString(puntajes,
				(Escenario.ANCHO - fm.stringWidth(puntajes)) / 2, y);

		// misiles y ciudades restantes
		int ciudades = Juego.getInstancia().getPartida().getPuntajes().cantidadCiudades;
		int misilesRestantes = Juego.getInstancia().getPartida().getPuntajes().cantidadMisilesRestantes;

		// Misiles Antiaereos restantes *5
		int imprimir = ((misilesRestantes > (contadorPuntajes / 6)) ? (contadorPuntajes / 6)
				: (misilesRestantes));
		String antiaereos = "MISILES RESTANTES (" + imprimir + "): "
				+ imprimir * MisilAntiaereo.PUNTAJE;
		contextoGrafico.drawString(antiaereos, x, y * 2);

		// Ciudades salvadas
		dibujarCiudadesPuntaje(ciudades, x, y * 3);
		// actualizar contador
		if (contadorPuntajes == (tiempoPuntajes - 1))
			contadorPuntajes = 0;
		else
			contadorPuntajes++;

	}

	private void dibujarCiudadesPuntaje(int cantCiudades, int x, int y) {
		Image imagen = repositorioImagenes.obtenerImagenes("ciudadPuntaje")[0];
		int maxCiudades = 0;
		// dibuja cada 1/3 de segundo
		for (int i = 0; i < cantCiudades; i++) {
			if (contadorPuntajes > 0.33 * Juego.FPS * (i + 1)) {
				contextoGrafico.drawImage(imagen,
						x + (int) ((imagen.getWidth(null)) * i * 1.5) + 20,
						y + 40, imagen.getWidth(null), imagen.getWidth(null),
						null);
				maxCiudades = (i + 1);
			}
		}
		String ciudad = "CIUDADES EN PIE (" + maxCiudades + "): "
				+ Ciudad.PUNTAJE * maxCiudades;
		contextoGrafico.drawString(ciudad, x, y);

	}

	private void dibujarCartel() {
		assert (contextoGrafico != null);
		juego.getEscenario().serDibujado(this);
		for (Terrestre e : juego.getEscenario().getCiudades())
			e.serDibujado(this);
		for (Terrestre e : juego.getEscenario().getSilos())
			e.serDibujado(this);
		juego.getPuntero().serDibujado(this);
		contextoGrafico.setColor(Color.WHITE);
		contextoGrafico.setFont(contextoGrafico.getFont().deriveFont(100f));
		if(juego.getPartida().isBonus())
			dibujarStringCentrado("BONUS!");
			else
		dibujarStringCentrado("NIVEL "
				+ juego.getPartida().getNivel().getNumero());
	}

	private void dibujarStringCentrado(String str) {
		FontMetrics fm = contextoGrafico.getFontMetrics();
		int x = (Escenario.ANCHO - fm.stringWidth(str)) / 2;
		int y = (fm.getAscent() + (Escenario.ALTO - (fm.getAscent() + fm
				.getDescent())) / 2);
		contextoGrafico.drawString(str, x, y);
	}

	private void dibujarJuego() {
		assert (contextoGrafico != null);
		contextoGrafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		juego.getEscenario().serDibujado(this);
		for (Terrestre e : juego.getEscenario().getCiudades())
			e.serDibujado(this);
		for (Terrestre e : juego.getEscenario().getSilos())
			e.serDibujado(this);
		for (Elemento e : juego.getElementosEnemigos())
			e.serDibujado(this);
		for (Elemento e : juego.getElementosNoEnemigos())
			e.serDibujado(this);
		for (OndaExpansiva e : juego.getOndasExpansivas())
			e.serDibujado(this);
		juego.getPuntero().serDibujado(this);
		contextoGrafico.setFont(contextoGrafico.getFont().deriveFont(16.0f));
		contextoGrafico.setColor(Color.WHITE);
		contextoGrafico.drawString("NIVEL "
				+ juego.getPartida().getNivel().getNumero()
				+ " PUNTAJE "
				+ (juego.getPuntajeTotal() + juego.getPartida()
						.getPuntajeNormal()) + " ("
				+ juego.getPartida().getPuntajeNormal() + ")", 50, 50);

		if (juego.isPausado()) {
			final Image imagenPausa = repositorioImagenes
					.obtenerImagenes("pausa")[0];
			contextoGrafico.drawImage(imagenPausa, Escenario.ANCHO / 2
					- imagenPausa.getWidth(null) / 2, Escenario.ALTO / 2
					- imagenPausa.getHeight(null) / 2, null);

		}
	}
}
