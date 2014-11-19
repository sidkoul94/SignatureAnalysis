import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static BufferedImage InputImage = new BufferedImage(300, 100,
			BufferedImage.TYPE_BYTE_BINARY);
	// Features to be extracted out of the input signature
	public static double AspectRatio;
	public static double SlantAngle;
	public static double Variance;
	public static double Skewness;
	public static double Entropy;
	public static double Kurtosis;
	public static int Xb;
	public static int Yb;
	public static int x, y, width, height;

	public static void main(String[] arg) {

		File file = new File(
				"/home/micky/workspace/Signature/res/Signatures/sid1.png");
		InputImage = LoadImage(file);
		 ExtractFeature(InputImage);
		 PrintValues();
		
	}

	private static void IntializeFrame(BufferedImage InputImage) {

		int ImgWidth, ImgHeight;
		ImgWidth = InputImage.getWidth();
		ImgHeight = InputImage.getHeight();
		JFrame Frame = new Main();
		Jlabel PicLabel = new Jlabel(InputImage);
		Frame.getContentPane().add(PicLabel, BorderLayout.CENTER);
		Frame.setVisible(true);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.setSize(ImgWidth, ImgHeight + 35);

	}

	private static void draw(ARatio ar) {
		BufferedImage timg = InputImage;
		Graphics g = timg.createGraphics();
		g.setColor(Color.RED);
		g.drawLine(Xb, 0, Xb, timg.getHeight());
		g.drawLine(0, Yb, timg.getWidth(), Yb);
		g.drawRect(x, y, width, height);
		g.dispose();
		IntializeFrame(timg);
	}

	private static BufferedImage LoadImage(File file) {
		try {
			BufferedImage img = ImageIO.read(file);
			Image Img = img.getScaledInstance(300, 100,
					img.SCALE_AREA_AVERAGING);
			img = new BufferedImage(300, 100, BufferedImage.TYPE_BYTE_GRAY);
			Graphics g = img.createGraphics();
			g.drawImage(Img, 0, 0, null);
			g.dispose();
			return img;
		} catch (IOException e) {
			System.out.println("Cant read the input file.");
			return null;
		}
	}

	public static void ExtractFeature(BufferedImage IpImage) {

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
		IntializeFrame(FtEx.returnImage());
	}

	public static void PrintValues() {
		System.out.println("AspectRatio is " + AspectRatio);
		System.out.println("Skewness is " + Skewness);
		System.out.println("BaseAngle is " + SlantAngle);
		System.out.println("Entropy is " + Entropy);
		System.out.println("Kurtosis is " + Kurtosis);
	}

}
