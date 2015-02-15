package ExtraTools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Display.CreateWindow;
import Features.ARatio;
import PreProcessing.Binarize;
import PreProcessing.Convert;
import PreProcessing.Cropping;
import PreProcessing.Thinning;

public class RotateImage {

	public static BufferedImage rotateBy(BufferedImage IpImg, double degrees) {
		Image ImgOp = new Image(IpImg);
		ImgOp.rotate(degrees);

		BufferedImage tempImg = ImgOp.getBufferedImage();
		BufferedImage nimg = new BufferedImage(tempImg.getWidth(),
				tempImg.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g1 = nimg.createGraphics();
		g1.drawImage(tempImg, 0, 0, null);
		g1.dispose();
		for (int i = 0; i < nimg.getWidth(); i++) {
			for (int j = 0; j < nimg.getHeight(); j++) {
				if (nimg.getRGB(i, j) == Color.BLACK.getRGB())
					nimg.setRGB(i, j, Color.WHITE.getRGB());
			}
		}
		
		nimg = Thinning.Thin(Binarize.binarize(nimg, 230));
		nimg = Convert.binaryTorgb(nimg);
		tempImg = Cropping.Crop(nimg, new ARatio().getARatio(nimg));
		tempImg =Thinning.Thin( Convert.rgbTobinary(tempImg));
		//CreateWindow.ofImage(tempImg, "Rotated Image");
		return tempImg;
	}
	

}
