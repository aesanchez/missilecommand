package ar.edu.unlp.info.missilecommand.terrestres;

public class MisilDemasiadoBajoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "No se puede disparar debajo del umbral!";
	}	
}
