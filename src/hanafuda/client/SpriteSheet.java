package hanafuda.client;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public static BufferedImage spriteSheet;
	
	public final static int cardWidth = 56;
	public final static int cardHeight = 90;
	public final static String path = "res/hanafuda.png";
	
	/**
	 * Opens the SpriteSheet, exiting the program upon failure.
	 */
	
	static void initialize() {
		try { spriteSheet = ImageIO.read(new File(path)); }
		catch (IOException e) {
			System.err.println("Couldn't load the spritesheet!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Gets a card's image from the spreadsheet. Zero-indexed.
	 * @param x : The x index of the image.
	 * @param y : The y index of the image.
	 * @return A BufferedImage of the card.
	 */
	
	public static BufferedImage getCard(int x, int y) {
		return spriteSheet.getSubimage(cardWidth*x, cardHeight*y, cardWidth, cardHeight);
	}
	
}
