package ExtraTools;
import javax.swing.JFrame;

public class makeGraph extends JFrame {

	@SuppressWarnings("unused")
	private int[] data;

	public makeGraph(int[] data , String title) {
		this.data = data;
		add(new graph(data));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(500, 500);
		this.setTitle(title);
	}

}
