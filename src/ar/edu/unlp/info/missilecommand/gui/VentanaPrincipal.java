package ar.edu.unlp.info.missilecommand.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class VentanaPrincipal extends VentanaAbstracta {

	private static final long serialVersionUID = 1L;

	PanelFondo fondo = new PanelFondo();

	public VentanaPrincipal() {
		super("Menú");
        try {
			UIManager.setLookAndFeel(
			    UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setSize(fondo.getSize());
		this.setTitle("Menú Principal - Missile Command");
		this.setContentPane(fondo);
		this.setLayout(null);
		this.centrarVentana();

		Container contenedor = new Container();
		fondo.add(contenedor);
		
		final Dimension d = new Dimension(100,25);
		contenedor.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
		final JButton botonInstrucciones = new JButton("Instrucciones");
		botonInstrucciones.setPreferredSize(d);
		final JButton botonJugar = new JButton("Jugar");
		botonJugar.setPreferredSize(d);
		final JButton botonPuntajes = new JButton("Puntajes");
		botonPuntajes.setPreferredSize(d);
		botonInstrucciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new VentanaInstrucciones(VentanaPrincipal.this);
			}
		});
		
		botonJugar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new VentanaPrejuego(VentanaPrincipal.this);
			}
		});
		
		botonPuntajes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new VentanaPuntajes(VentanaPrincipal.this);
			}
		});
		
		contenedor.add(botonInstrucciones);
		contenedor.add(botonJugar);
		contenedor.add(botonPuntajes);

		contenedor.setSize(new Dimension(500,40));
		contenedor.setLocation(this.getSize().width/2 - contenedor.getSize().width/2,(int)(this.getSize().height*0.60));
		this.setResizable(false);
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame mp = new VentanaPrincipal();
				mp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	private class PanelFondo extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image imagen;

		public PanelFondo() {
			final String path = "images/fondo-main.png";
			URL url = getClass().getClassLoader().getResource(path);
			if (url == null) {
				throw new Error("No se encontró imagen de fondo: " + path);
			}
			try {
				this.imagen = ImageIO.read(url);
			} catch (IOException e) {
				throw new Error("No se pudo cargar" + url);
			}
			Dimension tamano = new Dimension(imagen.getWidth(null),
					imagen.getHeight(null));
			setPreferredSize(tamano);
			setMinimumSize(tamano);
			setMaximumSize(tamano);
			setSize(tamano);
			setLayout(null);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(imagen, 0, 0, null);
		}

	}

}
