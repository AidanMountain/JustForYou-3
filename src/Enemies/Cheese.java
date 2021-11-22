package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntityStatus;
import Level.Player;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

// This class is for the Cheese projectile that the mouse boss shoots
// it will travel in a straight line (x axis) for a set time before disappearing
// it will disappear early if it collides with a solid map tile
public class Cheese extends EnemyProjectile {

	public Cheese(Point location, float movementSpeed, int existenceTime) {
		super(location, movementSpeed, existenceTime, new SpriteSheet(ImageLoader.load("Cheese.png"), 32, 32));
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("DEFAULT", new Frame[] { new FrameBuilder(spriteSheet.getSprite(0, 0), 0).withScale(2)
						.withBounds(1, 1, 32, 32).build() });
			}
		};
	}
}
