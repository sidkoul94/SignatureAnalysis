package Display;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jlabel extends JLabel {

	BufferedImage InputImage;

	public Jlabel(BufferedImage InputImage) {
		this.InputImage  = InputImage;
	}

	public void paint(Graphics g) {

		ImageIcon imgicon = new ImageIcon(InputImage);
		imgicon.paintIcon(this, g, 0, 0);
	}

}
