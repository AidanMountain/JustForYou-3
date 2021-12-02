package NPCs;

import java.awt.Color;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Map;
import Level.NPC;
import Level.Player;
import SpriteFont.SpriteFont;
import Utils.Point;

// This class is for the walrus NPC
public class Crab extends NPC {

	protected double xDistFromPlayer;
    protected double yDistFromPlayer;
    protected double dist;

	public Crab(Point location, Map map) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Crab.png"), 31, 31), "TAIL_DOWN", 5000);
	}
	public Crab(Point location, Map map, String speech) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Crab.png"), 31, 31), "TAIL_DOWN", 5000);
	}

	//@Override
	//protected SpriteFont createMessage() {
	//	return new SpriteFont("Howdy!", getX(), getY() - 10, "Arial", 12, Color.BLACK);
	//}


//hello
	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}
/*
	@Override
	public void drawMessage(GraphicsHandler graphicsHandler) {
		// draws a box with a border (think like a speech box)
		graphicsHandler.drawFilledRectangleWithBorder(Math.round(getCalibratedXLocation() - 2),
				Math.round(getCalibratedYLocation() - 24), 50, 25, Color.WHITE, Color.BLACK, 2);

		// draws message "Hello" in the above speech box
		//message.setLocation(getCalibratedXLocation() + 2, getCalibratedYLocation() - 8);
		//message.draw(graphicsHandler);
	}*/
}
