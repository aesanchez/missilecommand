package ar.edu.unlp.info.missilecommand.moviles;

import ar.edu.unlp.info.missilecommand.Juego;
import ar.edu.unlp.info.missilecommand.gui.Dibujador;

/**
 * Clase que representa un misil crucero "tonto". Un misil crucero tonto
 * desciende en una l�nea recta como un m�sil interplanetario.
 */
public class MisilCruceroTonto extends MisilCrucero {

	public MisilCruceroTonto() {
		super();
	}

	@Override
	public void actualizar() {
		if (getDestino() == null)
			throw new IllegalStateException();
		if (this.getPosicion().getY() > this.getDestino().getY()) {
			System.out.println(Juego.getInstancia().getFotogramaActual()
					+ this.getClass().getSimpleName()
					+ " llego a su destino,no se encontro con nada");
			this.recibirAtaque();
		} else {
			this.setPosicion(this.getPosicion().suma(this.getVelocidad()));
		}
	
	}
	
	@Override
	public void serDibujado(Dibujador d) {
		d.dibujar(this);
	}

}