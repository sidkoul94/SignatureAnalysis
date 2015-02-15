package PreProcessing;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Display.CreateWindow;
import ExtraTools.RotateImage;
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
		
		//CreateWindow.ofImage(timg, "CroppedImageBeforeResizing");
		//timg =  reSized(timg , ar);
		//CreateWindow.ofImage(timg, "CroppedImageAfterResizing");
		
		return reSized(timg,ar);
	}

	private static BufferedImage reSized(BufferedImage img , ARatio ar) {
		
		int newWidth = 300;
		Image Img = img.getScaledInstance(newWidth, (int) (newWidth/ar.getAspectRatio()), Image.SCALE_AREA_AVERAGING);
		img = new BufferedImage(newWidth, (int) (newWidth/ar.getAspectRatio()), img.getType());
		Graphics g = img.createGraphics();
		g.drawImage(Img, 0, 0, null);
		g.dispose();
		return img;
	}
	
}
