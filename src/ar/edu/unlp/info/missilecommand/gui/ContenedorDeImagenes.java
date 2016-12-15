package ar.edu.unlp.info.missilecommand.gui;

import java.awt.Image;

import ar.edu.unlp.info.missilecommand.Elemento;
import ar.edu.unlp.info.missilecommand.Escenario;
import ar.edu.unlp.info.missilecommand.OndaExpansiva;
import ar.edu.unlp.info.missilecommand.Puntero;
import ar.edu.unlp.info.missilecommand.moviles.*;
import ar.edu.unlp.info.missilecommand.terrestres.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

/**
 * Clase contenedora de imagenes de los Elementos. La idea es que todo elemento
 * tenga exactamente una instancia de cada uno de sus fotogramas. Los objetos
 * pueden obtener referencias a instancias unicas de sus imagenes. También
 * pueden cargarse imagenes que no esten asociadas en ninguna clase, pero sí a
 * una String. En su construcción se realiza la carga de todas las imagenes
 */
public class ContenedorDeImagenes {

	/*
	 * Mapa de tuplas (Clase,Imagen[])
	 */
	final private HashMap<Class<? extends Elemento>, Image[]> mapaClaseImagenes;

	final private HashMap<String, Image[]> mapaStringImagenes;

	public ContenedorDeImagenes() {
		mapaClaseImagenes = new HashMap<>();
		mapaStringImagenes = new HashMap<>();
		cargarImagenes();
	}

	private void agregarImagen(Class<? extends Elemento> clase, Image imagen) {
		agregarImagenes(clase, new Image[] { imagen });
	}

	private void agregarImagenes(Class<? extends Elemento> clase,
	// Cualquier imagen nula deberia haberse manejado en la carga!
			Image[] imagenes) {
		assert (imagenes != null && imagenes.length > 0 && clase != null);
		for (Image imagen : imagenes)
			assert (imagen != null);

		if (!mapaClaseImagenes.containsKey(clase))
			mapaClaseImagenes.put(clase, imagenes);
	}

	private void agregarImagen(String clave, Image imagen) {
		agregarImagenes(clave, new Image[] { imagen });
	}

	private void agregarImagenes(String clave,
	// Cualquier imagen nula deberia haberse manejado en la carga!
			Image[] imagenes) {
		assert (imagenes != null && imagenes.length > 0 && clave != null && !clave
				.isEmpty());
		for (Image imagen : imagenes)
			assert (imagen != null);

		clave = clave.toLowerCase();
		if (!mapaStringImagenes.containsKey(clave))
			mapaStringImagenes.put(clave, imagenes);
	}

	/**
	 * Carga una imagen del directorio /images
	 * 
	 * @param nombreArchivo
	 *            nombre del archivo o resto del path del directorio.
	 * @return Una instancia de Image.
	 */
	private Image cargarImagen(String nombreArchivo) {
		ClassLoader loader = getClass().getClassLoader();
		URL url = loader.getResource("images/" + nombreArchivo);
		Image img = null;
		if (url != null) {
			try {
				System.out.println("Cargando: " + url);
				img = ImageIO.read(url);
			} catch (IOException e) {
				e.printStackTrace();
				throw new Error("No se pudo cargar " + url);
			}
		} else
			throw new Error("No se encontró la imagen " + nombreArchivo);
		assert (img != null);
		return img;

	}

	/**
	 * Carga a memoria las imagenes de todos los objetos
	 */
	private void cargarImagenes() {

		agregarImagen(Puntero.class, cargarImagen("crosshair.png"));
		agregarImagenes(OndaExpansiva.class,
				cargarSecuencia("secuenciaExplosion/explosion", 1, 27, ".png"));

		final Image imagenMisilCrucero = cargarImagen("MCI.png");
		agregarImagen(MisilCruceroTonto.class, imagenMisilCrucero);
		agregarImagen(MisilCruceroInteligente.class, imagenMisilCrucero);
		agregarImagen(MisilAntiaereo.class, cargarImagen("MBI.png"));
		agregarImagen(Escenario.class, cargarImagen("fondo.png"));
		agregarImagenes(Silo.class, cargarSecuencia("silo", 1, 2, ".png"));
		agregarImagen(Ciudad.class, cargarImagen("ciudad.png"));
		agregarImagen(Satelite.class, cargarImagen("satelite.png"));
		agregarImagen(Avion.class, cargarImagen("avion.png"));
		agregarImagen(MisilBalisticoInterplanetario.class,
				cargarImagen("misilInterplanetario.png"));
		
		agregarImagen("TheEnd", cargarImagen("carteles/end.png"));
		agregarImagen("Pausa",cargarImagen("carteles/pausa.png"));
		agregarImagen("ciudadPuntaje",cargarImagen("ciudad_puntaje.png"));
	}

	/**
	 * @param prefijo
	 *            Texto antes de la numeración del archivo
	 * @param primerIndice
	 *            primer numero que toma la secuencia de archivos
	 * @param ultimoIndice
	 *            ultimo numero que toma la secuencia de archivos
	 * @param sufijo
	 *            texto siguiente a la numeración del archivo (incluyendo
	 *            extension)
	 * @return arreglo con las imagenes cargadas.
	 * @throws java.lang.IllegalArgumentException Si los indices estan mal.
	 */
	private Image[] cargarSecuencia(String prefijo, int primerIndice,
			int ultimoIndice, String sufijo) {
		if (ultimoIndice <= primerIndice || primerIndice < 0
				|| ultimoIndice <= 0)
			throw new IllegalArgumentException("Indices inválidos!");

		Image[] imagenes = new Image[ultimoIndice - primerIndice + 1];

		for (int i = primerIndice; i <= ultimoIndice; i++) {
			imagenes[i - primerIndice] = cargarImagen(prefijo + i + sufijo);

		}
		return imagenes;
	}

	/**
	 * 
	 * @param elemento
	 *            Objeto cuyas imagenes se necesitan.
	 * @return Referencia a instancia unica de la imagen
	 */
	public Image[] obtenerImagenes(Elemento elemento) {
		Class<? extends Elemento> clase = elemento.getClass();
		if (!mapaClaseImagenes.containsKey(clase))
			throw new NoSuchElementException("No existe imagen para "
					+ elemento.getClass().getSimpleName());
		assert (mapaClaseImagenes.containsKey(clase));
		return mapaClaseImagenes.get(clase);
	}

	/**
	 * 
	 * @param claveElemento
	 *            Objeto cuyas imagenes se necesitan.
	 * @return Referencia a instancia unica de la imagen
	 */
	public Image[] obtenerImagenes(String claveElemento) {
		claveElemento = claveElemento.toLowerCase();
		if (!mapaStringImagenes.containsKey(claveElemento))
			throw new NoSuchElementException("No existe imagen para la clave: "
					+ claveElemento);
		// HashMap devuelve null si no existe! y deberia existir en este punto
		assert (mapaStringImagenes.containsKey(claveElemento));
		return mapaStringImagenes.get(claveElemento);
	}
}