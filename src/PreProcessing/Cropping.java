package PreProcessing;

import java.awt.image.BufferedImage;

import Features.ARatio;

public class Cropping {

	public static BufferedImage Crop(BufferedImage InputImage, ARatio ar) {
		BufferedImage timg = new BufferedImage(ar.getWidth(), ar.getHeight(),
			InputImage.getType());
		for (int i = 0; i < timg.getWidth(); i++) {
			for (int j = 0; j < timg.getHeight(); j++) {
				timg.setRGB(i, j, InputImage.getRGB(i + ar.topx, j + ar.topy));
			}
		}
		return timg;
	}

}
