import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Help {

	public static BufferedImage img;
	static int X;
	static int Y;
	static double angle;
	static double[] histo = new double[256];
	static int numOfPix = 0;

	public static void main(String[] arg) {

		try {
			BufferedImage timg = ImageIO.read(new File(
					"/home/micky/workspace/Signature/res/Signatures/help4.PNG"));
			img = new BufferedImage(timg.getWidth(), timg.getHeight(),
					BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = img.createGraphics();
			g.drawImage(timg, 0, 0, null);
			g.dispose();
			ImageIO.write(
					img,
					"PNG",
					new File(
							"/home/micky/workspace/Signature/res/Signatures/sid1gray.png"));
			IntializeFrame(img, 'a');
			histogram();
			// InitalizeGraph();
			System.out.println("Image entropy is " + -entropy());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static double entropy() {
		int cnt = 0;
		double ent = 0;
		for (int i = 0; i < 256; i++) {
			double Pi = histo[i] / numOfPix;
			if (Pi != 0) {

				double lnPi = Math.log(Pi);
				ent += (Pi * lnPi);
			cnt++;

			System.out.println(ent);
			}

		}
		
		System.out.println("Count is + " + cnt );

		return ent;

	}

	private static void histogram() {

		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				int pix = img.getRGB(i, j);
				Color c = new Color(pix);
				histo[c.getRed()]++;
				numOfPix++;
			}
		}

	}

	private static BufferedImage LoadImage(File file) {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Cant read the input file.");
			return null;
		}
	}

	/*
	 * public static void drawline() { FeatureExtraction fe = new
	 * FeatureExtraction(img); ARatio ar = fe.ARatio(); int topx = (int)
	 * ar.getTopd().getWidth(); int topy = (int) ar.getTopd().getHeight(); int
	 * bottomx = (int) ar.getBottomd().getWidth(); int bottomy = (int)
	 * ar.getBottomd().getHeight(); int width = bottomx - topx; int ht = bottomy
	 * - topy; System.out.println(angle); Graphics g = img.createGraphics();
	 * g.setColor(Color.RED); if (angle < 0) { g.drawLine( topx, bottomy + ht /
	 * 2, (int) (topx + width * Math.cos(Math.abs(angle))), (int) (bottomy + ht
	 * / 2 - width * Math.sin(Math.abs(angle)))); } if (angle > 0) {
	 * g.drawLine(topx, topy + ht / 2, (int) (topx + width *
	 * Math.cos(Math.abs(angle))), (int) (topy + ht / 2 + width *
	 * Math.sin(Math.abs(angle)))); }
	 * 
	 * }
	 */
	public static void dr() {
		Graphics g = img.createGraphics();
		g.setColor(Color.RED);
		g.drawLine(X, 0, X, img.getHeight());
		g.drawLine(0, Y, img.getWidth(), Y);
		g.dispose();
	}

	public static double m(int p, int q) {
		double sum = 0;
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				int v = bij(i, j);
				sum += v * Math.pow(i, p) * Math.pow(j, q);
			}
		return sum;
	}

	public static double u(int p, int q) {
		double sum = 0;
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				int v = bij(i, j);
				sum += v * Math.pow(i - X, p) * Math.pow(j - Y, q);
			}
		return sum;
	}

	public static BufferedImage scale(BufferedImage img) {

		BufferedImage newimg = new BufferedImage(img.getWidth(),
				img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);

		int count = 0;
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				if (img.getRGB(i, j) == Color.BLACK.getRGB()) {
					int x, y;
					// north
					x = i;
					y = j - 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// south
					x = i;
					y = j + 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// east
					x = i + 1;
					y = j;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// west
					x = i - 1;
					y = j;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// ne
					x = i + 1;
					y = j - 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// nw
					x = i - 1;
					y = j - 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// se
					x = i + 1;
					y = j + 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;

					// sw
					x = i - 1;
					y = j + 1;
					if (x < 0 || x > img.getWidth() || y < 0
							|| y > img.getHeight())
						count++;
					else if (img.getRGB(x, y) == Color.BLACK.getRGB())
						count++;
				}
				if (count == 8) {
					img.setRGB(i, j, Color.BLACK.getRGB());
				} else
					img.setRGB(i, j, Color.WHITE.getRGB());
				count = 0;
			}
		}

		return img;

	}

	private static void IntializeFrame(BufferedImage InputImage, char a) {

		int ImgWidth, ImgHeight;
		ImgWidth = InputImage.getWidth();
		ImgHeight = InputImage.getHeight();

		JFrame Frame = new JFrame();

		Jlabel PicLabel = new Jlabel(InputImage);
		Frame.getContentPane().add(PicLabel, BorderLayout.CENTER);
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setSize(ImgWidth, ImgHeight + 35);
		Frame.setTitle("Picture" + a);

	}

	private static BufferedImage Binarize(BufferedImage img) {

		BufferedImage pic = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_BYTE_BINARY);
		BufferedImage npic = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics g = pic.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		g = npic.createGraphics();
		g.drawImage(pic, 0, 0, null);

		return npic;
	}

	public static int bij(int i, int j) {

		if (img.getRGB(i, j) == Color.BLACK.getRGB())
			return 1;
		else
			return 0;
	}

	public static void calc() {

		int a = 0, b = 0, c = 0;
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				int x = i - X;
				int y = j - Y;
				int v = bij(i, j);
				a += x * x * v;
				b += x * y * v;
				c += y * y * v;
			}

		System.out.println(a + " " + b + " " + c);
		double d = (double) 2 * b / (a - c);
		System.out.println((angle = (Math.atan(d) / 2)));
	}

	private static void COM(BufferedImage img) {
		int sumx = 0;
		int sumy = 0;
		int num = 0;
		System.out.println(img.getWidth() + " " + img.getHeight());
		for (int i = 0; i < img.getWidth(); i++)
			for (int j = 0; j < img.getHeight(); j++) {
				if (img.getRGB(i, j) == Color.BLACK.getRGB()) {
					sumx += i;
					sumy += j;
					num++;
				}
			}

		X = sumx / num;
		Y = sumy / num;

		System.out.println("Com Coordinate : ( " + (sumx / num) + ","
				+ (sumy / num) + " )");
	}
}
