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
public class Human extends NPC {

	protected double xDistFromPlayer;
    protected double yDistFromPlayer;
    protected double dist;

	public Human(Point location, Map map) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Human.png"), 63, 63), "TAIL_DOWN", 5000);
	}
	public Human(Point location, Map map, String speech) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Human.png"), 24, 24), "TAIL_DOWN", 5000, speech);
	}

	@Override
	protected SpriteFont createMessage() {
		return new SpriteFont("There you are!", getX(), getY() - 10, "Arial", 12, Color.BLACK);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}

	@Override
	public void drawMessage(GraphicsHandler graphicsHandler) {
		// draws a box with a border (think like a speech box)
		graphicsHandler.drawFilledRectangleWithBorder(Math.round(getCalibratedXLocation() + 40),
				Math.round(getCalibratedYLocation() - 24), 90, 25, Color.WHITE, Color.BLACK, 2);
/*
		// draws message "Hello" in the above speech box
		message.setLocation(getCalibratedXLocation() + 44, getCalibratedYLocation() - 8);
		message.draw(graphicsHandler);
		*/
	}
}
