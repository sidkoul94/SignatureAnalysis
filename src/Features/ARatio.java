package Features;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ARatio {
	
	//Coordinates of top left corner
	public int topx;
	public int topy;
	
	//Coordinates of bottom right corner
	public int bottomx;
	public int bottomy;

	//get the width of the InputImage
	public int getWidth() {
		return (bottomx - topx);
	}
	
	//get the Height of the InputImage
	public int getHeight() {

		return (bottomy - topy);
	}

	public void setTopLeft(int a, int b) {
		topx = a;
		topy = b;
	}

	public void setBottomRight(int a, int b) {
		bottomx = a;
		bottomy = b;
	}

	//get the Aspect Ratio of the image
	public float getAspectRatio() {
		return (float) ((float)(bottomx - topx) / (bottomy - topy));
	}
	
	// Calculating the Aspect Ratio
		public ARatio getARatio(BufferedImage InputImage) {

			int height, width;
			height = InputImage.getHeight();
			width = InputImage.getWidth();

			// Retrieve Lefty
			int lefty = 0;
			boolean flag = false;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if ((InputImage.getRGB(i, j)) == Color.BLACK.getRGB()) {
						lefty = i;
						flag = true;
						break;
					}
					if (flag)
						break;
				}
			}

			// Retrieve Righty
			int righty = 0;
			flag = false;
			for (int i = width - 1; i >= 0; i--) {
				for (int j = 0; j < height; j++) {
					if ((InputImage.getRGB(i, j)) == Color.BLACK.getRGB()) {
						righty = i;
						flag = true;
						break;
					}

					if (flag)
						break;
				}
			}

			// Retrieve MaxXx
			int topx = 0;
			flag = false;
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					if ((InputImage.getRGB(i, j)) == Color.BLACK.getRGB()) {
						topx = j;
						flag = true;
						break;
					}

					if (flag)
						break;
				}
			}

			// Retrieve MinX
			int bottomx = 0;
			flag = false;
			for (int j = height - 1; j >= 0; j--) {
				for (int i = 0; i < width; i++) {
					if ((InputImage.getRGB(i, j)) == Color.BLACK.getRGB()) {
						bottomx = j;
						flag = true;
						break;
					}

					if (flag)
						break;
				}
			}

			width = righty - lefty;
			height = bottomx - topx;
			ARatio ar = new ARatio();
			ar.setTopLeft(lefty, topx);
			ar.setBottomRight(righty, bottomx);
			return ar;
		}

}
