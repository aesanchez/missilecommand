package ar.edu.unlp.info.missilecommand.terrestres;

public class SiloVacioException extends Exception {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Este silo no tiene mas misiles disponibles!";
	}	
	
}
