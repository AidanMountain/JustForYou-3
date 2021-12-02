package NPCs;

import java.awt.Color;
import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Achievement;
import Level.Map;
import Level.NPC;
import Level.Player;
import SpriteFont.SpriteFont;
import Utils.Point;

// This class is for the walrus NPC
public class Walrus extends NPC {
	
	protected double xDistFromPlayer;
    protected double yDistFromPlayer;
    protected double dist;

	public Walrus(Point location, Map map) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "TAIL_DOWN", 5000);
	}
	
	public Walrus(Point location, Map map, String speech) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "TAIL_DOWN", 5000, speech);
	}
	
	public Walrus(Point location, Map map, Achievement associatedAchievement) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "TAIL_DOWN", 5000, associatedAchievement);
	}
	
	public Walrus(Point location, Map map, String speech, Achievement associatedAchievement) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("Walrus.png"), 24, 24), "TAIL_DOWN", 5000, speech, associatedAchievement);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}
}
