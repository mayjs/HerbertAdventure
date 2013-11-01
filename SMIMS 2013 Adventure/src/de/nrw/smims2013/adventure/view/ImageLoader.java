package de.nrw.smims2013.adventure.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageLoader {

	private static final Map<String, BufferedImage> loadedImages = new HashMap<String, BufferedImage>();
	private static final Map<String, ImageIcon> loadedIcons = new HashMap<String, ImageIcon>();

	private ImageLoader() {
	}

	public static ImageIcon getImageIcon(String name) {
		ImageIcon returnIcon = loadedIcons.get(name);
		if (returnIcon == null) {
			try {
				returnIcon = new ImageIcon(
						ImageLoader.class
								.getResource("/de/nrw/smims2013/adventure/story/image/"
										+ name + ".png"));
			} catch (Exception e) {
				try {
					returnIcon = new ImageIcon(
							ImageLoader.class
									.getResource("/de/nrw/smims2013/adventure/story/image/"
											+ name + ".gif"));
				} catch (Exception exception) {
					returnIcon = new ImageIcon(
							ImageLoader.class
									.getResource("/de/nrw/smims2013/adventure/story/image/"
											+ "GreenCube" + ".png"));
				}
			}
			returnIcon = getScaledIcon(returnIcon);
			loadedIcons.put(name, returnIcon);
		}
		
		return returnIcon;
	}
	
	private static ImageIcon getScaledIcon(ImageIcon i){
		ImageIcon ret = i;
		if(i.getIconWidth() > 70 || i.getIconHeight() > 70){
			BufferedImage bi = new BufferedImage(i.getIconWidth(),i.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.createGraphics();
			g.drawImage(i.getImage(),0,0,bi.getWidth(),bi.getHeight(),null);
			g.dispose();
			
			int width = 0; int height = 0;
			if(bi.getWidth() > bi.getHeight()){
				width = 70;
				height = (int)(70d*((double)bi.getHeight()/(double)bi.getWidth()));
			}
			else{
				height = 70;
				width = (int)(70d*((double)bi.getWidth()/(double)bi.getHeight()));
			}
			
			ret = new ImageIcon(bi.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		}
		return ret;
	}

	public static BufferedImage getImage(String name) {

		BufferedImage returnImage = loadedImages.get(name);
		if (returnImage == null) {
			InputStream is = ImageLoader.class
					.getResourceAsStream("/de/nrw/smims2013/adventure/story/image/"
							+ name + ".png");
			if (is == null) {
				is = ImageLoader.class
						.getResourceAsStream("/de/nrw/smims2013/adventure/story/image/"
								+ name + ".gif");
			}
			if (is == null) {
				is = ImageLoader.class
						.getResourceAsStream("/de/nrw/smims2013/adventure/view/image/"
								+ name + ".png");
			}
			if (is == null) {
				is = ImageLoader.class
						.getResourceAsStream("/de/nrw/smims2013/adventure/view/image/"
								+ name + ".gif");
			}
			try {
				returnImage = ImageIO.read(is);
				loadedImages.put(name, returnImage);
			} catch (Exception e) {

			}
		}
		if (returnImage == null) {
			returnImage = getImage("QuadratGruen");
		}
		return returnImage;
	}
}