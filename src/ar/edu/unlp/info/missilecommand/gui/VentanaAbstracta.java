package ar.edu.unlp.info.missilecommand.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 *  Clase abstracta que da alguna funcionalidad común a las que hereden de ella.
 */
public abstract class VentanaAbstracta extends JFrame {
	private static final long serialVersionUID = 1L;

	JFrame padre;

	public VentanaAbstracta() {
	}

	public VentanaAbstracta(String titulo) {
		super(titulo);
	}

	public VentanaAbstracta(JFrame padre, String titulo) {
		this(titulo);
		this.padre = padre;
		configurarVisibilidadPadre();
		
	}

	protected void centrarVentana() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
	}

	protected void esconderCursor() {
		BufferedImage cursorImg = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor");
		this.getContentPane().setCursor(blankCursor);
		this.setVisible(true);
	}

	protected void configurarVisibilidadPadre() {
		if (padre != null) {
			this.padre.setVisible(false);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					VentanaAbstracta.this.padre.setVisible(true);
					System.gc();
				}
			});
		}
	}

}
