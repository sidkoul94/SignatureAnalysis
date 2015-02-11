package Display;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class CreateWindow {

	private CreateWindow() {
	}

	public static void ofImage(BufferedImage Image, String Title) {

		JFrame newFrame = new JFrame(Title);
		Jlabel picture = new Jlabel(Image);
		newFrame.add(picture, BorderLayout.CENTER);
		newFrame.pack();
		newFrame.setLocationRelativeTo(null);
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setVisible(true);

	}

}
