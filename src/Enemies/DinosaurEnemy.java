package Enemies;

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
import Utils.Stopwatch;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// This class is for the green dinosaur enemy that shoots fireballs
// It walks back and forth between two set points (startLocation and endLocation)
// Every so often (based on shootTimer) it will shoot a Fireball enemy
public class DinosaurEnemy extends Enemy {

    // start and end location defines the two points that it walks between
    // is only made to walk along the x axis and has no air ground state logic, so make sure both points have the same Y value
    protected Point startLocation;
    protected Point endLocation;
    private float gravity = 1f;
    protected float movementSpeed = 1f;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    File FireBallSound = new File("Resources/FireBall.wav");
    public static float dB;


    // timer is used to determine when a fireball is to be shot out
    protected Stopwatch shootTimer = new Stopwatch();

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;

    public DinosaurEnemy(Point startLocation, Point endLocation, Direction facingDirection) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("DinosaurEnemy.png"), 14, 17), "WALK_RIGHT");
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();
        dinosaurState = DinosaurState.WALK;
        previousDinosaurState = dinosaurState;
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
        }
        airGroundState = AirGroundState.GROUND;

        // every 2 seconds, the fireball will be shot out
        shootTimer.setWaitTime(2000);
    }

    @Override
    public void update(Player player) {
        float startBound = startLocation.x;
        float endBound = endLocation.x;
        float moveAmountX = 0;
        float moveAmountY = 0;
        
        //add gravity (if in air then dino will fall to ground)
        moveAmountY += gravity;
        

        // if shoot timer is up and dinosaur is not currently shooting, set its state to SHOOT
        if (shootTimer.isTimeUp() && dinosaurState != DinosaurState.SHOOT) {
            dinosaurState = DinosaurState.SHOOT;
            PlaySound(FireBallSound,0.15);
        }

        super.update(player);
     

        // if dinosaur is walking, determine which direction to walk in based on facing direction
        if (dinosaurState == DinosaurState.WALK) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
            }
            moveYHandleCollision(moveAmountY);
            // if dinosaur reaches the start or end location, it turns around
            // dinosaur may end up going a bit past the start or end location depending on movement speed
            // this calculates the difference and pushes the enemy back a bit so it ends up right on the start or end location
            if (getX1() + getScaledWidth() >= endBound) {
                float difference = endBound - (getScaledX2());
                moveXHandleCollision(-difference);
                facingDirection = Direction.LEFT;
            } else if (getX1() <= startBound) {
                float difference = startBound - getX1();
                moveXHandleCollision(difference);
                facingDirection = Direction.RIGHT;
            }

            // if dinosaur is shooting, it first turns read for 1 second
            // then the fireball is actually shot out
        } else if (dinosaurState == DinosaurState.SHOOT) {
            if (previousDinosaurState == DinosaurState.WALK) {
                shootTimer.setWaitTime(1000);
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer.isTimeUp()) {

                // define where fireball will spawn on map (x location) relative to dinosaur enemy's location
                // and define its movement speed
                int fireballX;
                float movementSpeed;
                if (facingDirection == Direction.RIGHT) {
                    fireballX = Math.round(getX()) + getScaledWidth();
                    movementSpeed = 1.5f;
                } else {
                    fireballX = Math.round(getX());
                    movementSpeed = -1.5f;
                }

                // define where fireball will spawn on the map (y location) relative to dinosaur enemy's location
                int fireballY = Math.round(getY()) + 4;

                // create Fireball enemy
                Fireball fireball = new Fireball(new Point(fireballX, fireballY), movementSpeed, 1000);

                // add fireball enemy to the map for it to offically spawn in the level
                map.addEnemy(fireball);

                // change dinosaur back to its WALK state after shooting, reset shootTimer to wait another 2 seconds before shooting again
                dinosaurState = DinosaurState.WALK;
                shootTimer.setWaitTime(2000);
            }
        }
        previousDinosaurState = dinosaurState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if dinosaur enemy collides with something on the x axis, it turns around and walks the other way
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
        }
    }
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
    	if (direction == Direction.DOWN) {
			if (hasCollided) {
				airGroundState = AirGroundState.GROUND;
			} else {
				airGroundState = AirGroundState.AIR;
			}
		}
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("WALK_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 200)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("SHOOT_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 0)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("SHOOT_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 0)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });
        }};
    }
    public static void PlaySound(File Sound, double vol)
    {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Sound));
            clip.getLevel();
            setVol(vol,clip);
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void setVol(double vol, Clip clip)
    {
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        dB = (float)(Math.log(vol)/(Math.log(10)) * 20);
        gain.setValue(dB);
    }
    

    public enum DinosaurState {
        WALK, SHOOT
    }
}
