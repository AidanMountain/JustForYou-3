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
	public void update(Player player) {
		
        xDistFromPlayer = Math.pow((this.getX() - player.getX()), 2);
        yDistFromPlayer = Math.pow((this.getY() - player.getY()), 2);
        dist = Math.sqrt(xDistFromPlayer + yDistFromPlayer);
        
		// while npc is being talked to, it raises its tail up (in excitement?)
		if (talkedTo) {
			currentAnimationName = "TAIL_UP";
		} else if (dist < 150 && !this.getTalkedTo()) {
			currentAnimationName = "PLAYER_CLOSE";
		}
		else {
			currentAnimationName = "TAIL_DOWN";
		}
		super.update(player);
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("TAIL_DOWN", new Frame[] { new FrameBuilder(spriteSheet.getSprite(0, 0), 0).withScale(3)
						.withImageEffect(ImageEffect.FLIP_HORIZONTAL).build() });
				put("TAIL_UP", new Frame[] { new FrameBuilder(spriteSheet.getSprite(1, 0), 0).withScale(3)
						.withImageEffect(ImageEffect.FLIP_HORIZONTAL).build() });
				put("PLAYER_CLOSE", new Frame[] { new FrameBuilder(spriteSheet.getSprite(0, 1), 0).withScale(3)
	                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL).build() });
			}
		};
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
	}
}
