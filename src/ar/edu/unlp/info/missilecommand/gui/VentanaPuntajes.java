package ar.edu.unlp.info.missilecommand.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaPuntajes extends VentanaAbstracta {
	private static final long serialVersionUID = 1L;
	
	private JTable tabla;

	public VentanaPuntajes(JFrame padre) {
		super(padre,"Puntajes");
		this.setLayout(new BorderLayout());
		
		tabla = new JTable();
		tabla.setModel(Puntajes.getInstancia());
		tabla.setVisible(true);
		JButton botonReiniciar = new JButton("Reiniciar puntajes");
		botonReiniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Puntajes.getInstancia().reiniciarPuntajes();
				
			}
			
		});
		this.add(botonReiniciar,BorderLayout.PAGE_END);
		this.add(new JScrollPane(tabla));
		tabla.setFillsViewportHeight(true);
		
		this.setSize(400,400);
		this.centrarVentana();
		this.setVisible(true);
	}
	
}
