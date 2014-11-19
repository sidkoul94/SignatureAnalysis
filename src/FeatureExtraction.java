import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class FeatureExtraction {

	// InputImage
	private BufferedImage InputImage = null;
	private BufferedImage Gray_InputImage = null;

	// Coordinates for the Centre of Gravity
	public int Xb, Yb;

	// histogram bins
	private double[] histogram = new double[256];

	// number of pixels in the InputImage
	private int numOfPixels = 0;

	// Constructor
	public FeatureExtraction(BufferedImage InputImage) {
		this.InputImage = new BufferedImage(InputImage.getWidth(),
				InputImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics g = this.InputImage.createGraphics();
		g.drawImage(InputImage, 0, 0, null);
		g.dispose();
		Gray_InputImage = new BufferedImage(InputImage.getWidth(),
				InputImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		g = this.Gray_InputImage.createGraphics();
		g.drawImage(InputImage, 0, 0, null);
		g.dispose();
		COG();
		histogram();
		//IntializeFrame(Gray_InputImage);
	}

	public BufferedImage returnImage() {
		return InputImage;
	}

	// IntializingFrames
	private void IntializeFrame(BufferedImage InputImage) {

		int ImgWidth, ImgHeight;
		ImgWidth = InputImage.getWidth();
		ImgHeight = InputImage.getHeight();
		JFrame Frame = new JFrame("Feature Extraction");
		Jlabel PicLabel = new Jlabel(InputImage);
		Frame.getContentPane().add(PicLabel, BorderLayout.CENTER);
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setSize(ImgWidth, ImgHeight + 35);

	}

	// Calculating Centre of Gravity of the InputImage
	private void COG() {
		int sumx = 0;
		int sumy = 0;

		for (int i = 0; i < InputImage.getWidth(); i++)
			for (int j = 0; j < InputImage.getHeight(); j++) {
				if (InputImage.getRGB(i, j) == Color.BLACK.getRGB()) {
					sumx += i;
					sumy += j;
					numOfPixels++;
				}
			}

		Xb = sumx / numOfPixels;
		Yb = sumy / numOfPixels;

	}

	// Calculating the Aspect Ratio
	public ARatio aRatio() {

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

		/*
		 * System.out.println(bottomx + " " + topx); System.out.println(righty +
		 * " " + lefty); System.out.println("Height of the image is:" + (bottomx
		 * - topx)); System.out.println("Width of the image is:" + (righty -
		 * lefty));
		 */

		width = righty - lefty;
		height = bottomx - topx;
		ARatio ar = new ARatio();
		ar.setTopLeft(lefty, topx);
		ar.setBottomRight(righty, bottomx);
		return ar;
	}

	// Calculating BaseAngle of the InputImage
	private int bij(int i, int j) {

		if (InputImage.getRGB(i, j) == Color.BLACK.getRGB())
			return 1;
		else
			return 0;
	}

	public double BaseAngle() {

		int a = 0, b = 0, c = 0;

		for (int i = 0; i < InputImage.getWidth(); i++)
			for (int j = 0; j < InputImage.getHeight(); j++) {
				int x = i - Xb;
				int y = j - Yb;
				int v = bij(i, j);
				a += x * x * v;
				b += x * y * v;
				c += y * y * v;
			}

		double d = (double) 2 * b / (a - c);
		double angle = Math.atan(d) / 2;

		// angle in radians
		return angle;

	}

	// Extracting Skewness of the InputImage
	// Central Moment of order (p+q)
	private double u(int p, int q) {

		double sum = 0;
		for (int i = 0; i < InputImage.getWidth(); i++)
			for (int j = 0; j < InputImage.getHeight(); j++) {
				int v = bij(i, j);
				sum += v * Math.pow(i - Xb, p) * Math.pow(j - Yb, q);
			}
		return sum;
	}

	// Raw Moment of order (p+q)
	private double m(int p, int q) {
		double sum = 0;
		for (int i = 0; i < InputImage.getWidth(); i++)
			for (int j = 0; j < InputImage.getHeight(); j++) {
				int v = bij(i, j);
				sum += v * Math.pow(i, p) * Math.pow(j, q);
			}
		return sum;
	}

	// Normal Skewness
	public double Skewness() {

		double u33 = u(3, 3);
		double u22 = u(2, 2);
		double S = u33 / Math.sqrt(Math.pow(u22, 3));
		return S;
	}

	// Skewness along X-axis
	public double SkewnessX() {

		/*
		 * double u30 = (m(3, 0) - 3 * Xb * m(2, 0) + 2 * Xb * Xb * m(1, 0));
		 * double u20 = m(2, 0) - Xb * m(1, 0); double Sx = u30 /
		 * Math.sqrt(Math.pow(u20, 3)); return Sx;
		 */
		double u30 = u(3, 0);
		double u20 = u(2, 0);
		return (u30 / (Math.pow(Math.sqrt(u20), 3)));
	}

	// Skewness along Y-axis
	public double SkewnessY() {

		double u03 = (m(0, 3) - 3 * Yb * m(0, 2) + 2 * Yb * Yb * m(0, 1));
		double u02 = m(0, 2) - Yb * m(0, 1);
		double Sy = u03 / Math.sqrt(Math.pow(u02, 3));
		return Sy;
	}

	// Calculating Entropy of the InputImage
	// Calculating the histogram bins
	private void histogram() {
		numOfPixels = 0;
		for (int i = 0; i < Gray_InputImage.getWidth(); i++) {
			for (int j = 0; j < Gray_InputImage.getHeight(); j++) {
				int pix = Gray_InputImage.getRGB(i, j);
				Color c = new Color(pix);
				histogram[c.getRed()]++;
				numOfPixels++;

			}
		}

	}

	// Calculating entropy
	public double entropy() {
		double ent = 0;
		for (int i = 0; i < 256; i++) {
			double Pi = histogram[i] / numOfPixels;
			if (Pi != 0) {

				double lnPi = Math.log(Pi);
				ent += (Pi * lnPi);
			}
		}
		
		if(ent != 0)
			return (-ent);
		
		return 0;
	}

	// Calculating Kurtosis
	public double KurtosisX() {
		double u40 = u(4, 0);
		double u20 = u(2, 0);

		return ((u40 / (u20 * u20)) - 3);
	}

	public double KurtosisY() {
		double u04 = u(0, 4);
		double u02 = u(0, 2);

		return ((u04 / (u02 * u02)) - 3);
	}

}
