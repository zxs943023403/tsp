package astar;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class readMazeImage {
	public wayPoint[][] read(String path) throws Exception {
		BufferedImage bi = ImageIO.read(new File(path));
		int [][] data = new int[bi.getWidth()][bi.getHeight()];
		wayPoint[][] way = new wayPoint[bi.getWidth()][bi.getHeight()];
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				data[i][j] = bi.getRGB(i, j);
			}
		}
		int[] rgb = new int[3];
		for (int j = 0; j < data.length; j++) {
			for (int i = 0; i < data[j].length; i++) {
				int pixel = data[i][j];
				rgb[0] = (pixel & 0xff0000) >> 16;
				rgb[1] = (pixel & 0xff00) >> 8;
				rgb[2] = (pixel & 0xff);
//				System.out.print(rgb[0]<100 && rgb[1]<100&&rgb[2]<100?"1":"0"+"");
				way[j][i] = new wayPoint(j, i, !(rgb[0]<100 && rgb[1]<100&&rgb[2]<100));
			}
//			System.out.println("");
		}
		return way;
	}
	
	public static void main(String[] args) {
		try {
			wayPoint[][] ways = new readMazeImage().read("d:\\maze5.jpg");
			for (int i = 0; i < ways.length; i++) {
				for (int j = 0; j < ways[i].length; j++) {
					System.out.print(ways[i][j].isCanGo()?"1":"0");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
