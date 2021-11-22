package Enemies;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

// This class is for the Lawn Mower Enemy
// This enemy walks in one direction like the bug and mouse enemies
// However, after enough time it stops for a second and then charges forward at a fast speed
public class LawnMowerOfDeath extends BasicEnemy {

	private LawnMowerState state;
	private Stopwatch dashTimer = new Stopwatch();
	private Stopwatch dashWaiter = new Stopwatch();
	public Stopwatch charging = new Stopwatch();
	private boolean isDashing, isCharging = false;

	public LawnMowerOfDeath(Point location, Direction facingDirection) {
		super(location, facingDirection, new SpriteSheet(ImageLoader.load("LawnMower.png"), 39, 39));

		gravity = 1f;
		movementSpeed = 1f;
		this.initialize();
	}

	@Override
	public void initialize() {
		super.initialize();
		state = LawnMowerState.WALK;
		dashWaiter.setWaitTime(5000);
	}

	@Override
	public void update(Player player) {

		if (dashWaiter.isTimeUp() && airGroundState == AirGroundState.GROUND) 
		{
			state = LawnMowerState.DASH;
        }
		
		if(state == LawnMowerState.DASH)
		{
			if (!isDashing) 
				{
					if(!isCharging)
					{
						charging.setWaitTime(1000);
						this.setMovementSpeed(0f);
						isCharging = true;
					}
					else if (charging.isTimeUp())
					{
						isCharging = false;
						dashTimer.setWaitTime(1000);
						isDashing = true;
					}
				}
			else
			{
				this.setMovementSpeed(3f);
				if (dashTimer.isTimeUp())
				{
					this.setMovementSpeed(1f);
					state = LawnMowerState.WALK;
					dashWaiter.reset();
					isDashing = false;
				}
			}
		}

		super.update(player);

	}
	
	public void setMovementSpeed(float speed) { this.movementSpeed = speed; }

	@Override
	public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
		return new HashMap<String, Frame[]>() {
			{
				put("WALK_LEFT",
						new Frame[] {
								new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3).withBounds(1, 24, 25, 8)
								.build(),
							new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3).withBounds(1, 24, 25, 8)
								.build(), 
								});

				put("WALK_RIGHT", new Frame[] {
						new FrameBuilder(spriteSheet.getSprite(0, 0), 100).withScale(3)
							.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(15, 24, 23, 8).build(),
						new FrameBuilder(spriteSheet.getSprite(0, 1), 100).withScale(3)
							.withImageEffect(ImageEffect.FLIP_HORIZONTAL).withBounds(15, 24, 23, 8).build() });
			}
		};
	}
	
	public enum LawnMowerState
	{
		WALK, DASH;
	}
}
