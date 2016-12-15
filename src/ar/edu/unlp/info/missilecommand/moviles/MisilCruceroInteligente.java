package ar.edu.unlp.info.missilecommand.moviles;

import java.util.Collection;

import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;
import ar.edu.unlp.info.missilecommand.Vector2D;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;
import ar.edu.unlp.info.missilecommand.terrestres.Ciudad;

/**
 * Clase que representa un misil crucero inteligente. El misil crucero
 * inteligente desciende en una linea recta y además es capaz de esquivar ondas
 * expansivas y misiles antiaéreos.
 */
public class MisilCruceroInteligente extends MisilCrucero {

	/**
	 * Radio del semicirculo dentro del cual puede detectar objetos a esquivar.
	 */
	final private double radioRadar = 40;
	private Vector2D velocidadDerecha;
	private Vector2D velocidadIzquierda;
	/**
	 * Si es true es por que hay que redefinir la velocidad despues de esquivar
	 */
	private boolean esquivo = false;

	public MisilCruceroInteligente() {
		super();
	}

	@Override
	public void setDestino(Vector2D destino, double rapidez) {
		super.setDestino(destino, rapidez);
		final double spd = Juego.pixelPorSegundoAPixelPorFotograma(rapidez) / 8;
		velocidadDerecha = new Vector2D(spd*(3/4.0), 0);
		velocidadIzquierda = new Vector2D(-spd*(3/4.0), 0);
	}

	@Override
	public void actualizar() {
		if (getDestino() == null)
			throw new IllegalStateException();
		if (this.getPosicion().getY() > this.getDestino().getY()) {// llego a su
																	// destino
			System.out
					.println(Juego.getInstancia().getFotogramaActual()
							+ " "
							+ "CruceroInteligente llego a su destino,no se encontro con nada");
			this.recibirAtaque();// crea la onda expansiva y elimina el misil

		} else {
			/*
			 * busca elementos cercanos
			 */
			boolean hay = false;
			if (Juego.getInstancia().getEscenario().getAlto() - Ciudad.ALTO
					- 10 > this.getPosicion().getY()) {

				final Collection<OndaExpansiva> ondas = Juego.getInstancia()
						.getOndasExpansivas();

				for (OndaExpansiva onda : ondas) {

					/*
					 * si la onda se encuentra dentro de su radio de deteccion y
					 * por debajo de el
					 */
					if ((onda.getPosicion().getY() > this.getPosicion().getY())
							&& onda.isColisionable()
							&& (this.getPosicionCentrada()
									.resta(onda.getPosicionCentrada()).modulo() <= (radioRadar + onda.getRadioActual()))) {
						hay = true;
						/*
						 * ahora debe decidir si moverse hacia la derecha o
						 * hacia la izquierda
						 */
						if (onda.getPosicionCentrada().getX() < this
								.getPosicionCentrada().getX()) {
							/*
							 * Debe girar a a la derecha
							 */

							this.setVelocidad(this.getVelocidad().suma(
									velocidadDerecha));
						} else {
							this.setVelocidad(this.getVelocidad().suma(
									velocidadIzquierda));
						}
					}
					if (hay) {
						esquivo = true;
						System.out.println(Juego.getInstancia()
								.getFotogramaActual()
								+ " "
								+ this.getClass().getSimpleName()
								+ " encontro una onda expansiva en su radar");
						break;// sale del for porque encontro un elemento
					}
				}
			}
			if (!hay) {
				/*
				 * Si no habia nada solo avanza, actualiza la velocidad para
				 * llegar a su destino por si se corrió
				 */
				if (esquivo) {
					this.establecerVelocidad(this.getDestino(), getRapidez());
					esquivo = false;
				}

			}
			this.setPosicion(this.getPosicion().suma(this.getVelocidad()));

		}

	}
	
	public double getRadioRadar() {
		return this.radioRadar;
	}

	
	@Override
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}
	
}