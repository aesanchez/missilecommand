package ar.edu.unlp.info.missilecommand.gui;

/**
 * Clase que representa cada tupla correspondiente a la tabla de puntajes.
 */
public class EntradaPuntaje implements Comparable<EntradaPuntaje> {
	final private String nombre;
	//puede ser nulo.
	private Integer puntaje;

	EntradaPuntaje(String nombre) {
		this(nombre,null);
	}
	
	EntradaPuntaje(String nombre, Integer puntaje)  {
		if (puntaje!=null && puntaje < 0)
			throw new IllegalArgumentException(
					"Puntaje no puede ser negativo!");
		if (nombre == null)
			throw new NullPointerException("Nombre no puede ser nulo!");
		if (nombre.isEmpty())
			throw new IllegalArgumentException("Nombre no puede ser vacio!");

		this.puntaje = puntaje;
		nombre = nombre.toUpperCase();
		if (nombre.length() == Puntajes.LONGITUD_NOMBRE) {
			this.nombre = nombre;
		} else if (nombre.length() > 20) {
			this.nombre = nombre.substring(0, Puntajes.LONGITUD_NOMBRE);
			assert(this.nombre.length()==Puntajes.LONGITUD_NOMBRE);
		} else {
			StringBuilder sb = new StringBuilder(nombre);
			while (sb.length() < 20)
				sb.append(' ');
			this.nombre = new String(sb);
		}
		assert (this.nombre.length() == 20);
	}

	String getNombre() {
		return this.nombre;
	}

	int getPuntaje() {
		return this.puntaje;
	}

	void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	@Override
	public int compareTo(EntradaPuntaje o) {
		return this.puntaje.compareTo(o.puntaje);
	}


	@Override
	public boolean equals(Object obj) {
		//este equals NO es consistente con compareTo!
		EntradaPuntaje otro = (EntradaPuntaje) obj;
		return this.nombre.equals(otro.nombre);
	}
	
	@Override
	public String toString() {
		return nombre+puntaje.toString();
	}

}