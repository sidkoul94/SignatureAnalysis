package PreProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Binarize {

	public static BufferedImage binarize(BufferedImage img, int Threshold) {
		int h = img.getHeight();
		int w = img.getWidth();
		int rgb = img.getRGB(0, 0);
		int arr[][] = new int[w][h];

		// Get pictures of each pixel gray value
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				// getRGB() Returns the default RGB color model (decimal)
				arr[i][j] = getImageRgb(img.getRGB(i, j));// The gray value of
															// the point
			}

		}

		BufferedImage bufferedImage = new BufferedImage(w, h,
				BufferedImage.TYPE_BYTE_BINARY);//
		int FZ = 230;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (getGray(arr, i, j, w, h) > FZ) {
					int black = new Color(255, 255, 255).getRGB();
					bufferedImage.setRGB(i, j, black);
				} else {
					int white = new Color(0, 0, 0).getRGB();
					bufferedImage.setRGB(i, j, white);
				}
			}

		}

		return bufferedImage;
	}

	private static int getImageRgb(int i) {
		String argb = Integer.toHexString(i);

		int r = Integer.parseInt(argb.substring(2, 4), 16);
		int g = Integer.parseInt(argb.substring(4, 6), 16);
		int b = Integer.parseInt(argb.substring(6, 8), 16);
		int result = (int) ((r + g + b) / 3);
		return result;
	}

	//
	public static int getGray(int gray[][], int x, int y, int w, int h) {
		int rs = gray[x][y] + (x == 0 ? 255 : gray[x - 1][y])
				+ (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
				+ (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1])
				+ (y == 0 ? 255 : gray[x][y - 1])
				+ (y == h - 1 ? 255 : gray[x][y + 1])
				+ (x == w - 1 ? 255 : gray[x + 1][y])
				+ (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
				+ (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
		return rs / 9;
	}
}
