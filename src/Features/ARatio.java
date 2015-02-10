package Features;
import java.awt.Dimension;

public class ARatio {
	
	//Coordinates of top left corner
	public int topx;
	public int topy;
	
	//Coordinates of bottom right corner
	int bottomx;
	int bottomy;

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

}
