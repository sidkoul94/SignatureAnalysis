import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Slant {

	static Point P1, P2;
	static Point P3, P4;
	static Point COG1, COG2;
	static int topx, topy, bottomx, bottomy;
	static int width, height;
	static BufferedImage InputImage;
	static double angle;

	public static void main(String[] arg) {

		File file = new File(
				"/home/micky/workspace/Signature/res/Signatures/help3.png");
		InputImage = LoadImage(file);
		AspectRatio();

	}

	private static void AspectRatio() {
		FeatureExtraction fe = new FeatureExtraction(InputImage);
		ARatio ar = fe.aRatio();
		topx = ar.topx;
		topy = ar.topy;
		bottomx = ar.bottomx;
		bottomy = ar.bottomy;
		width = ar.getWidth();
		height = ar.getHeight();
		Graphics g = InputImage.createGraphics();
		g.setColor(Color.RED);
		// g.drawRect(topx, topy, width, height);
		P1 = new Point(topx, topy);
		P2 = new Point(topx + width / 2, bottomy);
		P3 = new Point(topx + width / 2, topy);
		P4 = new Point(bottomx, bottomy);
		g.drawRect((int) P1.getX(), (int) P1.getY(), width, height);
		g.drawLine((int) P2.getX(), (int) P2.getY(), (int) P3.getX(),
				(int) P3.getY());
		COG1 = COG(P1, P2);
		COG2 = COG(P3, P4);

		g.setColor(Color.GREEN);
		g.drawLine((int) COG1.getX(), (int) COG1.getY(), (int) COG2.getX(),
				(int) COG2.getY());
		IntializeFrame(InputImage);
		System.out.println(Math.atan(((COG1.getY() - COG2.getY()) / (COG2
				.getX() - COG1.getX()))));
		angle = (Math.atan(((COG1.getY() - COG2.getY()) / (COG2
				.getX() - COG1.getX()))));
		transform();
	}

	private static void transform() {
		BufferedImage img = new BufferedImage(InputImage.getWidth(),
				InputImage.getHeight(), InputImage.getType());
		for(int i = 0 ; i < img.getWidth()  ; i++)
			for(int j = 0; j < img.getHeight() ; j++){
				int x = (int)((i-j)*Math.tan(angle));
				int y = j;
				if(x > 0 && y > 0 && x < img.getWidth() && y < img.getHeight()){
					img.setRGB(x, y, InputImage.getRGB(i, j));
				}
			}
		
		IntializeFrame(img);
	}

	private static Point COG(Point A, Point B) {

		int wid = (int) (B.getX() - A.getX());
		int hei = (int) (B.getY() - A.getY());
		System.out.println(wid + " " + hei);
		int sumx = 0;
		int sumy = 0;
		int numPix = 0;
		for (int i = (int) A.getX(); i < (int) (A.getX() + wid); i++)
			for (int j = (int) A.getY(); j < (int) (A.getY() + hei); j++) {
				if (InputImage.getRGB(i, j) == Color.BLACK.getRGB()) {
					sumx += i;
					sumy += j;
					numPix++;
				}
			}

		return new Point(sumx / numPix, sumy / numPix);
	}

	private static BufferedImage LoadImage(File file) {
		try {
			BufferedImage img = ImageIO.read(file);
			Image Img = img.getScaledInstance(300, 100,
					img.SCALE_AREA_AVERAGING);
			img = new BufferedImage(300, 100, BufferedImage.TYPE_INT_ARGB);
			Graphics g = img.createGraphics();
			g.drawImage(Img, 0, 0, null);
			g.dispose();
			return img;
		} catch (IOException e) {
			System.out.println("Cant read the input file.");
			return null;
		}
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
}
