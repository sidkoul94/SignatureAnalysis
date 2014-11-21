import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OptionSelect extends JFrame {

	JButton save = new JButton("Select"), load = new JButton("Analyze");
	JTextField file = new JTextField(30);
	File nfile;
	JLabel lb = new JLabel("File");

	public OptionSelect() {
		JPanel sp = new JPanel();
		sp.add(save);
		sp.add(load);
		save.addActionListener(new saveListener());
		load.addActionListener(new loadListener());
		Container cp = getContentPane();
		cp.add(sp, BorderLayout.SOUTH);
		JPanel np = new JPanel();
		np.add(lb);
		np.add(file);

		cp.add(np, BorderLayout.NORTH);
		this.pack();
		this.setTitle("Select a File");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] a) {
		new OptionSelect();
	}

	private class saveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			JFileChooser c = new JFileChooser();
			int rval = c.showOpenDialog(OptionSelect.this);
			if (rval == JFileChooser.APPROVE_OPTION) {
				file.setText(c.getSelectedFile().getAbsolutePath());
				nfile = new File(file.getText());
			}

		}

	}

	private class loadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				//BufferedImage img = ImageIO.read(nfile);
				new Display(nfile);
			} catch (Exception e) {
				e.printStackTrace();
				file.setText(" ");
				JOptionPane.showMessageDialog(OptionSelect.this,
						"Error File cant be Opened..(Inside OS)");
			}

		}

	}

}
