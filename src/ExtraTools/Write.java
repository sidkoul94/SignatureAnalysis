package ExtraTools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import Display.Main;

public class Write {

	public static void main(String[] aq) throws Exception {
		String[] Fname = { "TE1", "TE2", "TE3" };
		String path = "/home/micky/Box/";
		String TextFile = "/home/micky/resultSet.txt";
		Main obj;
		int i = 0;
		String[] content = new String[3];
		while (i < 3) {
			String fileName = Fname[i];
			File file = new File(path + fileName + ".png");
			obj = new Main(file);
			content[i] = "Signature" + (i+1) 
					+ "\n-------------"
					+"\nAspectRatio " + obj.AspectRatio
					+ "\nSkewness " + obj.Skewness + "\nBaseangle "
					+ obj.SlantAngle + "\nEntropy " + obj.Entropy
					+ "\nKurtosis " + obj.Kurtosis + "\n***************\n\n";

			i++;
		}

		String result = content[0] + content[1] + content[2];
		Files.write(Paths.get(TextFile), result.getBytes(),
				StandardOpenOption.CREATE);
	}

}
