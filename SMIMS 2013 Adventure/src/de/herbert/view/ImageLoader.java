package de.herbert.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.newdawn.slick.Image;

public class ImageLoader {

	private static final Map<String, Image> loadedImages = new HashMap<String, Image>();
	private static final Map<String, ImageIcon> loadedIcons = new HashMap<String, ImageIcon>();

	private ImageLoader() {
	}
	
	//
	public static Image getImage(String name) {

		Image returnImage = loadedImages.get(name);
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
				returnImage = new Image(is, name, false);
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