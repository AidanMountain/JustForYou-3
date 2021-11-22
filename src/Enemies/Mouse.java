package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

// This class is for the mouse enemy
// enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
// if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
public class Mouse extends BasicEnemy {


	public Mouse(Point location, Direction facingDirection) {
		super(location, facingDirection,  new SpriteSheet(ImageLoader.load("Mouse.png"), 23, 24));
		gravity = 1f;
		movementSpeed = 1f;
		this.initialize();
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("WALK_LEFT",
						new Frame[] {
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3).withBounds(8, 8, 11, 12)
										.build(),
								new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3).withBounds(8, 8, 11, 12)
										.build() });

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build(),
						new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build() });
			}
		};
	}
}
