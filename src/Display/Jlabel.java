package Display;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jlabel extends JLabel {

	BufferedImage InputImage;
	int S2= 50;
	public Jlabel(BufferedImage InputImage) {
		this.InputImage  = InputImage;
		this.setBorder(BorderFactory.createEmptyBorder(S2, S2, S2, S2));
		this.setPreferredSize(new Dimension(InputImage.getWidth(),InputImage.getHeight()));
	}

	public void paint(Graphics g) {

		ImageIcon imgicon = new ImageIcon(InputImage);
		imgicon.paintIcon(this, g, 0, 0);
	}

}
