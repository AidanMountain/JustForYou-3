package Players;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.MapEntityStatus;
import Level.Player;
import Level.PlayerProjectile;
import Utils.Direction;
import Utils.Point;
import Utils.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;

public class Hairball extends PlayerProjectile {
    private float movementSpeed;
    private static int numShots;
    private boolean removedHairball;
    private Stopwatch existenceTimer = new Stopwatch();

    public Hairball(Point location, float movementSpeed, int existenceTime) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Hairball.png"), 7, 7), "DEFAULT");
        this.movementSpeed = movementSpeed;
        existenceTimer.setWaitTime(existenceTime);
        isRespawnable = false;
        removedHairball = false;
        numShots++;
        initialize();
    }

    @Override
    public void update(ArrayList<Enemy> enemies) {
        // if timer is up, set map entity status to REMOVED
        // the camera class will see this next frame and remove it permanently from the map
        if (existenceTimer.isTimeUp()) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            // move fireball forward
            moveXHandleCollision(movementSpeed);
            super.update(enemies);
        }

        if(numShots < 0) numShots = 0;
        
        //If a hairball gets removed and it hasn't already been removed from the counter, remove one from the counter
        if(this.mapEntityStatus == MapEntityStatus.REMOVED && !removedHairball)
        	{
        		numShots--;
        		removedHairball = true;
        	}
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {
        // if fireball collides with anything solid on the x axis, it is removed
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }


    @Override
    public void touchedEnemy(Enemy enemy) {
        super.touchedEnemy(enemy);
        this.mapEntityStatus = MapEntityStatus.REMOVED;

    }
    
    public static void setNum(int num)
    {
    	numShots = num;
    }
    
    public static int getNum()
    {
    	return numShots;
    }

    @Override
    public HashMap<String, Frame[]> getAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                            .withScale(3)
                            .withBounds(1, 1, 5, 5)
                            .build()
            });
        }};
    }
}
