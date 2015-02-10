package Display;
import java.awt.BorderLayout;
import Features.FeatureExtraction;
import Features.ARatio;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//The following class takes the input , scales it to a size of 300x100
//Uses FeatureExtraction class to extract all the features and prints them on the screen...
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static BufferedImage InputImage = new BufferedImage(300, 100,
			BufferedImage.TYPE_BYTE_BINARY);

	// Features to be extracted out of the input signature
	public double AspectRatio;
	public double SlantAngle;
	public double Variance;
	public double Skewness;
	public double Entropy;
	public double Kurtosis;
	public double JointEntropy;

	// Extra parameters..!!
	public int Xb;
	public int Yb;
	public int x, y, width, height;

	public static void main(String[] arg) throws Exception {

		 new Main(new File("/home/micky/Desktop/sid2.png"));

	}

	public static BufferedImage Filter(BufferedImage img, double alpha) {
		BufferedImage rotatedImage = new BufferedImage(img.getWidth(),
				img.getHeight(), img.getType());
		alpha = alpha * 3.14 / 180;
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				int Xnew = (int) (i - (j * Math.tan(alpha)));
				int rgb = img.getRGB(i, j);
				if (Xnew > 0 && Xnew < img.getWidth()) {
					rotatedImage.setRGB(Xnew, j, rgb);
				} else {
					rotatedImage.setRGB(i, j, rgb);
				}
			}
		}

		return rotatedImage;

	}

	public Main(File file) throws Exception {
		InputImage = LoadImage(file);
		// IntializeFrame(InputImage,"img");

		ExtractFeature(InputImage);
	}

	private static void IntializeFrame(BufferedImage InputImage, String title) {

		int ImgWidth, ImgHeight;

		ImgWidth = InputImage.getWidth();
		ImgHeight = InputImage.getHeight();
		JFrame Frame = new JFrame();
		Frame.setTitle(title);
		Jlabel PicLabel = new Jlabel(InputImage);
		Frame.getContentPane().add(PicLabel, BorderLayout.CENTER);
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setSize(ImgWidth, ImgHeight + 35);

	}

	private void draw(ARatio ar) {
		BufferedImage timg = InputImage;
		Graphics g = timg.createGraphics();
		g.setColor(Color.RED);
		g.drawLine(Xb, 0, Xb, timg.getHeight());
		g.drawLine(0, Yb, timg.getWidth(), Yb);
		g.drawRect(x, y, width, height);
		g.dispose();
		IntializeFrame(timg, "GrayScale Image");
	}

	private static BufferedImage LoadImage(File file) {
		try {
			BufferedImage img = ImageIO.read(file);
			Image Img = img.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
			img = new BufferedImage(300, 100, img.getType());
			Graphics g = img.createGraphics();
			g.drawImage(Img, 0, 0, null);
			g.dispose();
			return img;
		} catch (IOException e) {
			System.out.println("Cant read the input file.");
			return null;
		}
	}

	public void ExtractFeature(BufferedImage IpImage) {

		FeatureExtraction FtEx = new FeatureExtraction(IpImage);
		Xb = FtEx.Xb;
		Yb = FtEx.Yb;
		ARatio AR = FtEx.aRatio();
		x = AR.topx;
		y = AR.topy;
		width = AR.getWidth();
		height = AR.getHeight();
		draw(AR);
		AspectRatio = AR.getAspectRatio();
		Skewness = FtEx.SkewnessX();
		SlantAngle = FtEx.BaseAngle();
		Entropy = FtEx.entropy();
		Kurtosis = FtEx.KurtosisX();
		FtEx.horizontalProjections();
		FtEx.verticalProjections();
		FtEx.baselineShift();
		IntializeFrame(FtEx.returnImage(), "Binary Image");
	}

	public void PrintValues() {
		System.out.println("AspectRatio is " + AspectRatio);
		System.out.println("Skewness is " + Skewness);
		System.out.println("BaseAngle is " + SlantAngle);
		System.out.println("Entropy is " + Entropy);
		System.out.println("Kurtosis is " + Kurtosis);
	}

}
