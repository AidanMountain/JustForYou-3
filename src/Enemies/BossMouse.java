package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy.ShootingState;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

// this behaves like a cross of the bug and dinosaur, it walks left and right while shooting
public class BossMouse extends BasicEnemy {

	// timer is used to determine when a cheese is to be shot out
	protected Stopwatch shootTimer = new Stopwatch();

	// can be either WALK or SHOOT based on what the enemy is currently set to do
	protected ShootingState shootingState;
	protected ShootingState previousShootingState;


	public BossMouse(Point location, Direction facingDirection) {
		super(location, facingDirection, new SpriteSheet(ImageLoader.load("Mouse.png"), 23, 24));
		gravity = 1f;
		movementSpeed = 1f;

		this.initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		shootingState = ShootingState.WALK;
		shootTimer.setWaitTime(50);
	}

	@Override
	public void update(Player player) {

		if (shootTimer.isTimeUp() && shootingState != ShootingState.SHOOT) {
			shootingState = ShootingState.SHOOT;
		}

		super.update(player);

		if (shootingState == ShootingState.SHOOT) {
			if (previousShootingState == ShootingState.WALK) {
				shootTimer.setWaitTime(100);
				currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
			} else if (shootTimer.isTimeUp()) {

				// define where cheese will spawn on map (x location) relative to dinosaur
				// enemy's location
				// and define its movement speed
				int cheeseX;
				float movementSpeed;
				if (facingDirection == Direction.RIGHT) {
					cheeseX = Math.round(getX()) + getScaledWidth();
					movementSpeed = 5f;
				} else {
					cheeseX = Math.round(getX());
					movementSpeed = -5f;
				}

				// define where cheese will spawn on the map (y location) relative to dinosaur
				// enemy's location
				int cheeseY = Math.round(getY()) + 120;

				// create cheese enemy
				Cheese cheese = new Cheese(new Point(cheeseX, cheeseY), movementSpeed, 10000);

				// add cheese enemy to the map for it to offically spawn in the level
				map.addEnemy(cheese);

				// change back to its WALK state after shooting, reset shootTimer to
				// wait another .7 seconds before shooting again
				shootingState = ShootingState.WALK;
				shootTimer.setWaitTime(700);
			}
		}
		previousShootingState = shootingState;
	}

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("WALK_LEFT",
						new Frame[] {
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(10)
										.withBounds(8, 8, 11, 12).build(),
								new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(10)
										.withBounds(8, 8, 11, 12).build() });

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(10)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build(),
						new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(10)
								.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(8, 8, 11, 12).build() });
			}
		};
	}
}
