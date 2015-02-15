package PreProcessing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Convert {

	private static BufferedImage binarizedImg = null;

	public static BufferedImage rgbTobinary(BufferedImage Img) {

		binarizedImg = new BufferedImage(Img.getWidth(), Img.getHeight(),
				Img.getType());
		for (int i = 0; i < Img.getWidth(); i++) {
			for (int j = 0; j < Img.getHeight(); j++) {
				int pixel = Img.getRGB(i, j);
				if (pixel == Color.WHITE.getRGB())
					binarizedImg.setRGB(i, j, Color.WHITE.getRGB());
				else
					binarizedImg.setRGB(i, j, Color.BLACK.getRGB());
			}
		}

		return binarizedImg;
	}

	public static BufferedImage binaryTorgb(BufferedImage img) {
		BufferedImage rgbimg = new BufferedImage(img.getWidth(), img.getHeight(),
				10);
		Graphics2D g = rgbimg.createGraphics();
		g.drawImage(rgbimg, 0, 0, null);
		g.dispose();

		return rgbimg;
	}
}
