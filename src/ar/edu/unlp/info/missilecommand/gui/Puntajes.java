package ar.edu.unlp.info.missilecommand.gui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Clase que representa la tabla de puntajes. Existe solo una instancia porque
 * tambien se encarga de acceder el archivo de puntajes.
 *
 */
public class Puntajes implements TableModel {

	static private Puntajes instancia = null;

	private final String ARCHIVO_PUNTAJES = "puntajes.txt";

	public static final int LONGITUD_NOMBRE = 20;

	public synchronized static Puntajes getInstancia() {
		if (instancia == null)
			instancia = new Puntajes();
		return instancia;
	}

	private Collection<TableModelListener> observadores = new ArrayList<>();

	// Lista de entradas en la tabla, de mayor a menor
	private List<EntradaPuntaje> entradas;

	private Puntajes() {
		entradas = new ArrayList<EntradaPuntaje>();
		boolean ok = false;
		while (!ok) {
			try {
				try {
					cargarDatos();
					ok = true;
				} catch (IOException e) {
					// Archivo no existe, reintentar
					new FileWriter(ARCHIVO_PUNTAJES);
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					// Archivo invalido, reintentar
					System.err
							.println("Archivo de puntajes invalido, creando uno nuevo.");
					new FileWriter(ARCHIVO_PUNTAJES);
				}
			} catch (IOException e) {
				throw new Error(e.getMessage()); // es directorio
				// rendirse
			}
		}
	}

	private void cargarDatos() throws IOException, NumberFormatException {
		final List<String> listadoDatos = Files.readAllLines(
				Paths.get(ARCHIVO_PUNTAJES), Charset.defaultCharset());
		for (String str : listadoDatos) {
			String nombre = str.substring(0, LONGITUD_NOMBRE);
			assert(nombre.length()==LONGITUD_NOMBRE);
			int puntaje = Integer.parseInt(str.substring(LONGITUD_NOMBRE));
			entradas.add(new EntradaPuntaje(nombre, puntaje));
		}
		Collections.sort(entradas);
		Collections.reverse(entradas);
	}

	synchronized public void agregarPuntaje(EntradaPuntaje entrada) {
		for (EntradaPuntaje e : entradas) {
			if (e.equals(entrada)) { // Ya existe
				e.setPuntaje(Math.max(entrada.getPuntaje(),
						e.getPuntaje()));
				escribirCambios();
				return;
			}
		}

		entradas.add(entrada);

		escribirCambios();
	}
	
	synchronized public void reiniciarPuntajes(){
		this.entradas.clear();
		this.escribirCambios();
	}

	private void escribirCambios() {
		Collections.sort(entradas);
		Collections.reverse(entradas);
		FileWriter escritor = null;
		try {
			escritor = new FileWriter(ARCHIVO_PUNTAJES);
			for (EntradaPuntaje e : entradas) {
				escritor.write(e.toString());
				escritor.write(System.lineSeparator());
			}
			escritor.flush();
			notificarCambios();
		} catch (IOException e) {
			System.err.println("No se pudieron escribir los puntajes!");
			e.printStackTrace();
		} finally {
			try {
				escritor.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Error(e.getMessage());
			}
		}
	}

	private void notificarCambios() {
		for (TableModelListener l : observadores) {
			l.tableChanged(new TableModelEvent(this));
		}
	}

	@Override
	public int getRowCount() {
		return entradas.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == 0)
			return "Nombre";
		else if (columnIndex == 1)
			return "Puntaje máximo";
		else
			throw new IllegalArgumentException();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 0)
			return String.class;
		else if (columnIndex == 1)
			return Integer.class;
		else
			throw new IllegalArgumentException();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return (Object) entradas.get(rowIndex).getNombre();
		} else if (columnIndex == 1) {
			return (Object) entradas.get(rowIndex).getPuntaje();
		} else
			throw new IllegalArgumentException();
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		observadores.add(l);

	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		observadores.remove(l);
	}

	public void agregarPuntaje(String string, int i) {
		agregarPuntaje(new EntradaPuntaje(string,i));
		
	}

}
