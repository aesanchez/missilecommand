package ar.edu.unlp.info.missilecommand.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ar.edu.unlp.info.missilecommand.Juego;

public class VentanaPrejuego extends VentanaAbstracta {
	private static final long serialVersionUID = 1L;

	private JTextField campoNombre;
	private JSpinner selectorNivel;

	public VentanaPrejuego(JFrame padre) {
		super(padre, "Jugar");

		inicializarUI();
	}

	private void inicializarUI() {
		// Instanciacion de componentes
		JButton botonJugar = new JButton("Jugar ahora");
		botonJugar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (campoNombre.getText().isEmpty()) {
					JOptionPane.showMessageDialog(VentanaPrejuego.this,
							"Ingrese un nombre para jugar.");
					return;
				}
				Juego.getInstancia()
						.setNivelPorDefecto(
								(Integer) VentanaPrejuego.this.selectorNivel
										.getValue());
				new VentanaJuego(VentanaPrejuego.this.padre,
						new EntradaPuntaje(campoNombre.getText()));
				VentanaPrejuego.this.setVisible(false);
				VentanaPrejuego.this.dispose();
			}
		});
		this.getRootPane().setDefaultButton(botonJugar);
		campoNombre = new JTextField();
		selectorNivel = new JSpinner(new SpinnerNumberModel(1, 1, 25, 1));

		// Configuracion del container con los controles
		Container contenedor = new Container();
		contenedor.setLayout(new GridBagLayout());
		contenedor.setSize(300, 300);

		GridBagConstraints c;
		Insets s = new Insets(10, 10, 10, 10);

		// Label nombre
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = s;
		contenedor.add(new JLabel("Nombre"), c);

		// Campo de nombre
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipadx = 200;
		c.ipady = 5;
		c.insets = s;
		contenedor.add(campoNombre, c);

		// Label nivel
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = 2;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = s;
		contenedor.add(new JLabel("Nivel"), c);

		// Selector de nivel
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = 2;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = s;
		contenedor.add(selectorNivel, c);

		// Boton jugar ahora
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		// c.fill=2;
		c.ipadx = 0;
		c.ipady = 5;
		c.insets = s;
		contenedor.add(botonJugar, c);

		// Configuracion de la ventana
		this.setLayout(null);
		this.setContentPane(contenedor);
		this.pack();
		this.setResizable(false);
		this.centrarVentana();
		this.setVisible(true);
	}

}
