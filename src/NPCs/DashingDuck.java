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
public class DashingDuck extends NPC {
	public double xDistFromPlayer;
	public double yDistFromPlayer;
	public double dist;
    protected SpriteFont spacePrompt;

	public DashingDuck(Point location, Map map) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("DashingDuck.png"), 31, 31), "TAIL_DOWN", 5000);
	}
	
	public DashingDuck(Point location, Map map, String speech) {
		super(location.x, location.y, new SpriteSheet(ImageLoader.load("DashingDuck.png"), 31, 31), "TAIL_DOWN", 5000, speech);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}
}