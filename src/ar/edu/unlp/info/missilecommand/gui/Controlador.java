package ar.edu.unlp.info.missilecommand.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingUtilities;

import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.Vector2D;

/**
 * Clase que une la interfaz gráfica y el juego. Es en sí un Thread y manda
 * pedidos periodicos a VentanaJuego para actualizar su panel.
 */
public class Controlador extends Thread {

	private VentanaJuego ventanaJuego;
	private Juego juego;
	/**
	 * Listener de teclado Y mouse.
	 */
	private ListenerGeneral listener;
	private boolean seguir;
	final Runnable actualizador = new Runnable() {
		@Override
		public void run() {
			ventanaJuego.repaint();
		}
	};

	private EntradaPuntaje jugador;

	public Controlador(VentanaJuego ventanaJuego) {
		super("Thread controlador");
		this.juego = Juego.getInstancia();
		this.ventanaJuego = ventanaJuego;
		this.listener = new ListenerGeneral();
		ventanaJuego.getAreaDibujo().addMouseMotionListener(listener);
		ventanaJuego.getAreaDibujo().addMouseListener(listener);
		ventanaJuego.getAreaDibujo().addKeyListener(listener);
		ventanaJuego.addWindowListener(new ListenerVentana());

	}

	@Override
	public void run() {
		juego.correr();

		// Loop principal
		seguir = true;
		while (seguir) {
			mostrarCartelNivel();

			ventanaJuego
					.setModoDibujador(Dibujador.ModosDibujo.JUEGO_PRINCIPAL);
			while (!juego.terminoNivel() && seguir) {
				esperar(1000 / Juego.FPS);
				if (!juego.isPausado())
					juego.avanzarCiclo();
				SwingUtilities.invokeLater(actualizador);
			}
			if (juego.terminoNivel())
				juego.acumularPuntaje();
			if (juego.terminoJuego()) {
				if (juego.hayBonus()) {
					mostrarPuntaje();
					juego.avanzarNivel();
				} else
					seguir = false;
			} else {
				if (juego.terminoNivel()) {
					mostrarPuntaje();
					juego.avanzarNivel();
				}
			}
		}

		jugador.setPuntaje(juego.getPuntajeTotal());
		Puntajes.getInstancia().agregarPuntaje(jugador);

		mostrarTheEnd();
		ventanaJuego.dispose();
	}

	private void mostrarCartelNivel() {
		listener.mouseActivo = false;
		int cont = 2 * Juego.FPS;
		while (cont > 0) {
			ventanaJuego.setModoDibujador(Dibujador.ModosDibujo.PRELUDIO);
			SwingUtilities.invokeLater(actualizador);
			esperar(1000 / Juego.FPS);
			cont--;
		}
		listener.mouseActivo = true;

	}

	private void mostrarPuntaje() {
		listener.mouseActivo = false;
		int cont = Dibujador.tiempoPuntajes;
		while (cont > 0) {
			ventanaJuego
					.setModoDibujador(Dibujador.ModosDibujo.RESULTADO_PUNTAJES);
			SwingUtilities.invokeLater(actualizador);
			esperar(1000 / Juego.FPS);
			cont--;
		}
		listener.mouseActivo = true;
	}

	private void mostrarTheEnd() {
		listener.mouseActivo = false;
		int cont = 2 * Juego.FPS;
		while (cont > 0) {
			ventanaJuego.setModoDibujador(Dibujador.ModosDibujo.THE_END);
			SwingUtilities.invokeLater(actualizador);
			esperar(1000 / Juego.FPS);
			cont--;
		}
		listener.mouseActivo = true;
	}

	private void esperar(int milisegundos) {
		try {
			sleep(milisegundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Se hizo un poco grande como para una clase anónima
	private class ListenerGeneral extends MouseAdapter implements KeyListener {
		public boolean mouseActivo = false;

		@Override
		public void mouseMoved(MouseEvent e) {
			juego.getPuntero().setPosicionCentrada(
					e.getX(),
					(juego.getEscenario().getUmbralMisiles() > e.getY()) ? e
							.getY() : juego.getEscenario().getUmbralMisiles());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!juego.isPausado() && mouseActivo)
				juego.getEscenario().disparar(new Vector2D(e.getX(), e.getY()));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {

			final int tecla = e.getKeyCode();
			switch (tecla) {
			case KeyEvent.VK_1:
				if (!juego.isPausado())
					juego.getEscenario().setSiloActual(0);
				break;
			case KeyEvent.VK_2:
				juego.getEscenario().setSiloActual(1);
				break;
			case KeyEvent.VK_3:
				if (!juego.isPausado())
					juego.getEscenario().setSiloActual(2);
				break;
			case KeyEvent.VK_L:
				ventanaJuego.getDibujador().toggleResaltaLimites();
				break;
			case KeyEvent.VK_P:
				juego.setPausado(!juego.isPausado());
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

	private class ListenerVentana extends WindowAdapter {
		@Override
		public void windowOpened(WindowEvent e) {
			juego.setPausado(false);
		}

		@Override
		public void windowIconified(WindowEvent e) {
			juego.setPausado(true);
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			juego.setPausado(true);
		}

		@Override
		public void windowClosing(WindowEvent e) {
			Controlador.this.seguir = false;
		}
	}

	public void setJugador(EntradaPuntaje jugador) {
		this.jugador = jugador;

	}

}
