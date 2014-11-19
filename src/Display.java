import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Display extends JFrame {

	JPanel np = new JPanel();
	JPanel cp = new JPanel();
	JPanel sp = new JPanel();
	JLabel lt = new JLabel("AspectRatio", JLabel.LEFT), ct = new JLabel(
			"SlantAngle", JLabel.LEFT),
			rt = new JLabel("Skewness", JLabel.LEFT),
			et = new JLabel("Entropy"), kt = new JLabel("Kutosis");
	JLabel ltans = new JLabel(), ctans= new JLabel(), rtans= new JLabel(), etans= new JLabel(), ktans= new JLabel();
	File file;

	public Display(File file) {
		this.file = file;
		BufferedImage img = null;
		try {
			img = ImageIO.read(file.getAbsoluteFile());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Display.this,
					"Error File cant be Opened..(Inside Display)");
		}
		np.setBackground(Color.WHITE);
		np.add(new JLabel("Signature Analysis", JLabel.CENTER));
		add(np, BorderLayout.NORTH);
		cp.setLayout(new GridLayout(0, 1, 10, 10));
		cp.add(label(img));
		cp.setBackground(Color.WHITE);
		add(cp, BorderLayout.CENTER);
		sp.setLayout(new GridLayout(5, 1, 10, 10));
		setValues();
		sp.add(lt);
		sp.add(ltans);
		sp.add(ct);
		sp.add(ctans);
		sp.add(rt);
		sp.add(rtans);
		sp.add(et);
		sp.add(etans);
		sp.add(kt);
		sp.add(ktans);
		sp.setSize(300, 300);
		sp.setBackground(Color.WHITE);
		add(sp, BorderLayout.SOUTH);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void setValues() {
		Main obj = new Main(file);
		ltans.setText(obj.AspectRatio + "");
		ctans.setText(obj.SlantAngle + " ");
		rtans.setText(obj.Skewness + " ");
		etans.setText(obj.Entropy + " ");
		ktans.setText(obj.Kurtosis + " ");
	}

	public JLabel label(BufferedImage img) {
		BufferedImage nimg;
		Image timg = img.getScaledInstance(100, 100,
				BufferedImage.SCALE_AREA_AVERAGING);
		nimg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = nimg.createGraphics();
		g.drawImage(timg, 0, 0, null);
		return new JLabel(new ImageIcon(nimg) , JLabel.CENTER);
	}

}