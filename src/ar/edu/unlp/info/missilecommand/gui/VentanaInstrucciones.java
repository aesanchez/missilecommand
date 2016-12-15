package ar.edu.unlp.info.missilecommand.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class VentanaInstrucciones extends VentanaAbstracta {
	private static final long serialVersionUID = 1L;
	
	private JEditorPane contenido;
	private final String NOMBRE_ARCHIVO = "Instrucciones.html";
	final JButton botonVolver = new JButton("Volver");

	public VentanaInstrucciones(JFrame padre) {
		super("Instrucciones de juego");
		this.padre = padre;
		configurarVisibilidadPadre();
		this.setLayout(new BorderLayout());
		Container contenedor = new Container();
		Dimension tamaño = new Dimension(100,100);
		contenedor.setPreferredSize(tamaño);
		contenedor.setLayout(new FlowLayout());
		this.add(contenedor);
		
		setSize(640, 640);
		centrarVentana();
		setVisible(true);
		
		
		try {
			final URL url = getClass().getClassLoader().getResource(NOMBRE_ARCHIVO);
			this.contenido = new JEditorPane(url);
		} catch (IOException e) {
			//e.printStackTrace();
			throw new Error(e.getMessage());
		}
		this.contenido.setEditable(false);
		this.add(new JScrollPane(contenido));

		
		
		botonVolver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new VentanaPrincipal();
			}
		});
	}
}
