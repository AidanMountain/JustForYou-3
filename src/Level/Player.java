package Level;

import Engine.*;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import Players.Hairball;
import Utils.*;
import Utils.Stopwatch;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;

    // values used to handle player movement
    protected float jumpForce = 0;
    protected float momentumY = 0;
    protected float moveAmountX, moveAmountY;
    protected boolean ignoreRight = false;
    protected boolean ignoreLeft = false;

    public static boolean walkSoundPlayed = true;
    public static float dB;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected PowerState powerState;
    protected PowerState previousPowerState;
    protected LevelState levelState;
    protected boolean unlockedPowerUpOne = false;
    protected boolean underwater;
    protected boolean milkedUp;
    protected boolean flashingPlayer;
    protected String previousAnimation;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key JUMP_KEY = Key.UP;
    protected Key MOVE_LEFT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_KEY = Key.RIGHT;
    protected Key CROUCH_KEY = Key.DOWN;
    //powerup attack
    protected Key POWERUP_ONE_KEY = Key.ONE;
    protected Stopwatch shootCoolDownTimer = new Stopwatch();

    // if true, player cannot be hurt by enemies (good for testing)
    //TODO: Where to set god mode
    protected boolean isInvincible = false;
    
    // variables for used for invincibility frames
    protected Timer timer;
    protected int iFrameTime;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName, 1);
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        powerState = PowerState.SAFE;
        previousPowerState = powerState;
        levelState = LevelState.RUNNING;
        underwater = false;
        milkedUp = false;
        iFrameTime = 0;
        flashingPlayer = false;
        previousAnimation = currentAnimationName;
        
        timer = new Timer(25000 / Config.FPS, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerFlash();
                if(iFrameTime > 12){
                    timer.stop();
                    isInvincible = false;
                }
                iFrameTime++;
            }
        });
        timer.setRepeats(true);

        File jumpSound = new File("Jump.wav");
        File walkSound = new File("Walking on concrete sound effect YouTube.wav");
    }

    public void update() {
        moveAmountX = 0;
        moveAmountY = 0;

        // if player is currently playing through level (has not won or lost)
        if (levelState == LevelState.RUNNING) {
            applyGravity();

            //prevents user from walking off the edge of the map (fix for map boundaries)
            if(super.x < 0 - map.tileset.getScaledSpriteWidth()/2) {
                super.x += 0;
                if(Keyboard.isKeyDown(MOVE_LEFT_KEY)) {
                    moveAmountX += walkSpeed;
                }
            }
            if(super.x > map.endBoundX - map.tileset.getScaledSpriteWidth()) {
                super.x -= 0;
                if(Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
                    moveAmountX -= walkSpeed;
                }
            }

            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            previousAirGroundState = airGroundState;
            previousPowerState = powerState;

            // update player's animation
            super.update();

            // move player with respect to map collisions based on how much player needs to move this frame
            super.moveYHandleCollision(moveAmountY);
            super.moveXHandleCollision(moveAmountX);

            updateLockedKeys();

            if(keyLocker.isKeyLocked(POWERUP_ONE_KEY)) { UnlockPowerOneKey(); }
            if(Keyboard.isKeyDown(POWERUP_ONE_KEY) && !keyLocker.isKeyLocked(POWERUP_ONE_KEY)){
                shoot();
            }

        }

        // if player has beaten level
        else if (levelState == LevelState.LEVEL_COMPLETED) {
            updateLevelCompleted();
        }

        // if player has lost level
        else if (levelState == LevelState.PLAYER_DEAD) {
            updatePlayerDead();
        }
    }
    
    // set swimming state on player for water tiles
    public void setPlayerSwimming(boolean setState){underwater = setState;}

    // add gravity to player, which is a downward force
    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case CROUCHING:
                playerCrouching();
                break;
            case JUMPING:
                playerJumping();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // sets animation to a STAND animation based on which way player is facing
    	if(milkedUp) {
            currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_STAND_RIGHT" : "MILKED_STAND_LEFT";
        }
    	else {
    		currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";
    	}
        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.WALKING;
            walkSoundPlayed = true;
        }

        // if jump key is pressed, player enters JUMPING state
        else if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
            walkSoundPlayed = false;
        }

        // if crouch key is pressed, player enters CROUCHING state
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
            walkSoundPlayed = false;
        }
        
        if(flashingPlayer) {
            if (currentAnimationName != "INVINCIBLE") {
                previousAnimation = currentAnimationName;
                currentAnimationName = "INVINCIBLE";
            }
        }

    }

    // player WALKING state logic
    protected void playerWalking() {
        File walk = new File("Resources/Walk.wav");
        // sets animation to a WALK animation based on which way player is facing
        if(milkedUp) {
            currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_WALK_RIGHT" : "MILKED_WALK_LEFT";
        }
        else {
        	currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        }
        
        if(underwater){walkSpeed = 1.6f;}
        else{walkSpeed = 3.6f;}

        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) && !ignoreLeft) {
        	ignoreRight = true;
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) && !ignoreRight) {
        	ignoreLeft = true;
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
        } else if (Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY)) {
            playerState = PlayerState.STANDING;
        }
        
        if(Keyboard.isKeyUp(MOVE_LEFT_KEY)){ignoreRight = false;}
        if(Keyboard.isKeyUp(MOVE_RIGHT_KEY)){ignoreLeft = false;}

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed,
        else if (Keyboard.isKeyDown(CROUCH_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
        
        if(flashingPlayer) {
            if (currentAnimationName != "INVINCIBLE") {
                previousAnimation = currentAnimationName;
                currentAnimationName = "INVINCIBLE";
            }
        }

    }

    public void playWalkSound(boolean walkingCalled)
    {
        if(walkingCalled == true)
        {
            File walk = new File("Resources/Walk.wav");
            PlaySoundLoop(walk,0.25);
            walkSoundPlayed = false;
        }
    }

    // player CROUCHING state logic
    protected void playerCrouching() {
        // sets animation to a CROUCH animation based on which way player is facing
    	if(milkedUp) {
            currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_CROUCH_RIGHT" : "MILKED_CROUCH_LEFT";
        }
    	else {
    		currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
    	}

        // if crouch key is released, player enters STANDING state
        if (Keyboard.isKeyUp(CROUCH_KEY)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
        
        if(flashingPlayer) {
            if (currentAnimationName != "INVINCIBLE") {
                previousAnimation = currentAnimationName;
                currentAnimationName = "INVINCIBLE";
            }
        }
    }

    // player JUMPING state logic
    protected void playerJumping() {
    	
    	if(underwater){jumpHeight = 8; jumpDegrade = .3f; terminalVelocityY = 1.5f
    	        ;}
    	        else{jumpHeight = 14.5f; jumpDegrade = .5f; terminalVelocityY = 6;}
    	
        File jump = new File("Resources/Jump.wav");
        // if last frame player was on ground and this frame player is still on ground, the jump needs to be setup
        if (previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) {
            // sets animation to a JUMP animation based on which way player is facing
        	if(milkedUp) {
                currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_JUMP_RIGHT" : "MILKED_JUMP_LEFT";
            }
        	else {
        		currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
        	}
            PlaySound(jump, 0.15);
            // player is set to be in air and then player is sent into the air
            airGroundState = AirGroundState.AIR;
            jumpForce = jumpHeight;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
        }

        // if player is in air (currently in a jump) and has more jumpForce, continue sending player upwards
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            // if player is moving upwards, set player's animation to jump. if player moving downwards, set player's animation to fall
            if (previousY > Math.round(y)) {
            	if(milkedUp) {
                    currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_JUMP_RIGHT" : "MILKED_JUMP_LEFT";
                }
            	else {
            		currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            	}
            } else {
            	if(milkedUp) {
                    currentAnimationName = facingDirection == Direction.RIGHT ? "MILKED_FALL_RIGHT" : "MILKED_FALL_LEFT";
                }
            	else {
            		currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            	}
            }

            // allows you to move left and right while in the air
            if (Keyboard.isKeyDown(MOVE_LEFT_KEY) && !ignoreLeft) {
            	ignoreRight = true;
                moveAmountX -= walkSpeed;
                facingDirection = Direction.LEFT;
            }
            
            if(Keyboard.isKeyUp(MOVE_LEFT_KEY)){ignoreRight = false;}
            if(Keyboard.isKeyUp(MOVE_RIGHT_KEY)){ignoreLeft = false;}
            
            if (Keyboard.isKeyDown(MOVE_RIGHT_KEY) && !ignoreRight) {
            	ignoreLeft = true;
                moveAmountX += walkSpeed;
                facingDirection = Direction.RIGHT;
            }

            // if player is falling, increases momentum as player falls so it falls faster over time
            if (moveAmountY > 0) {
                increaseMomentum();
            }
            
            if(underwater){
                // if jump key is pressed underwater, player swims up
                if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
                    keyLocker.lockKey(JUMP_KEY);
                    airGroundState = AirGroundState.AIR;
                    jumpForce = jumpHeight;
                    if (jumpForce > 0) {
                        moveAmountY -= jumpForce;
                        jumpForce -= jumpDegrade;
                        if (jumpForce < 0) {
                            jumpForce = 0;
                        }
                    }
                }
            }
            
            if(flashingPlayer) {
                if (currentAnimationName != "INVINCIBLE") {
                    previousAnimation = currentAnimationName;
                    currentAnimationName = "INVINCIBLE";
                }
            }
        }

        // if player last frame was in air and this frame is now on ground, player enters STANDING state
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }
    }

    // while player is in air, this is called, and will increase momentumY by a set amount until player reaches terminal velocity
    protected void increaseMomentum() {
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(JUMP_KEY)) {
            keyLocker.unlockKey(JUMP_KEY);
        }
        if (Keyboard.isKeyUp(POWERUP_ONE_KEY)) {
            keyLocker.unlockKey(POWERUP_ONE_KEY);
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction) {

    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                playerState = PlayerState.JUMPING;
                airGroundState = AirGroundState.AIR;
            }
        }

        // if player collides with map tile upwards, it means it was jumping and then hit into a ceiling -- immediately stop upwards jump velocity
        else if (direction == Direction.UP) {
            if (hasCollided) {
                jumpForce = 0;
            }
        }
    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {
        if (!isInvincible) {
            // if map entity is an enemy, kill player on touch
            if (mapEntity instanceof Enemy) {
            	if(milkedUp){
                    milkedUp = false;
                    startIFrames();
                }
                else{
                    levelState = LevelState.PLAYER_DEAD;
                }
            }
        }
    }

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        // if player is not on ground, player should fall until it touches the ground
        if (airGroundState != AirGroundState.GROUND && map.getCamera().containsDraw(this)) {
        	if(milkedUp) {
                currentAnimationName = "MILKED_FALL_RIGHT";
            }
        	else {
        		currentAnimationName = "FALL_RIGHT";
        	}
            applyGravity();
            increaseMomentum();
            super.update();
            moveYHandleCollision(moveAmountY);
        }
        // move player to the right until it walks off screen
        else if (map.getCamera().containsDraw(this)) {
        	if(milkedUp) {
                currentAnimationName = "MILKED_WALK_RIGHT";
            }
        	else {
        		currentAnimationName = "WALK_RIGHT";
        	}
            super.update();
            moveXHandleCollision(walkSpeed);
        } else {
            // tell all player listeners that the player has finished the level
            for (PlayerListener listener : listeners) {
                listener.onLevelCompleted();
            }
        }
    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {
        // change player animation to DEATH
        if (!currentAnimationName.startsWith("DEATH")) {
            if (facingDirection == Direction.RIGHT) {
            	if(milkedUp) {
                    currentAnimationName = "DEATH_MILKED_RIGHT";
                }
            	else {
            		currentAnimationName = "DEATH_RIGHT";
            	}
            } else {
            	if(milkedUp) {
                    currentAnimationName = "DEATH_MILKED_LEFT";
                }
            	else {
            		currentAnimationName = "DEATH_LEFT";
            	}
            }
            super.update();
        }
        // if death animation not on last frame yet, continue to play out death animation
        else if (currentFrameIndex != getCurrentAnimation().length - 1) {
          super.update();
        }
        // if death animation on last frame (it is set up not to loop back to start), player should continually fall until it goes off screen
        else if (currentFrameIndex == getCurrentAnimation().length - 1) {
            if (map.getCamera().containsDraw(this)) {
                moveY(3);
            } else {
                // tell all player listeners that the player has died in the level
                for (PlayerListener listener : listeners) {
                    listener.onDeath();
                }
            }
        }
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public AirGroundState getAirGroundState() {
        return airGroundState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }
    
    public LevelState getLevelState() {
        return levelState;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }
    
    public void setMilkedUp(boolean setState){ milkedUp = setState;}

    public boolean getUnlockedPowerUpOne() { return unlockedPowerUpOne; }

    public void unlockPowerUpOne() { unlockedPowerUpOne = true; }

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
    public static void PlaySoundLoop(File Sound, double vol)
    {
        try
        {

            Clip clip = AudioSystem.getClip();

            if(walkSoundPlayed == true)
            {
                clip.open(AudioSystem.getAudioInputStream(Sound));
                clip.getLevel();
                setVol(vol,clip);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else if(walkSoundPlayed == false)
            {
                clip.stop();
                System.out.println("Stop the walk");
            }
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

    private void shoot(){
        if (getUnlockedPowerUpOne() && playerState != PlayerState.POWERUP_ONE) {

            powerState = PowerState.SAFE;

            if (previousPowerState == PowerState.SAFE && powerState == PowerState.SAFE) {
                int hairballX;
                float movementSpeed;
                if (facingDirection == Direction.RIGHT) {
                    hairballX = Math.round(getX()) + getScaledWidth();
                    movementSpeed = 1.5f;
                } else {
                    hairballX = Math.round(getX());
                    movementSpeed = -1.5f;
                }

                // define where hairball will spawn on the map (y location) relative to player's location

                int hairballY = Math.round(getY()) + 20;
                if(playerState == PlayerState.CROUCHING) hairballY += 10;

                //create a Hairball enemy
                Hairball hairball = new Hairball(new Point(hairballX, hairballY), movementSpeed, 2000);

                // add hairball enemy to the map for it to offically spawn in the level
                map.addPlayerProjectile(hairball);
                powerState = PowerState.FIRE;
                keyLocker.lockKey(POWERUP_ONE_KEY);
            }
        }
    }

    private void UnlockPowerOneKey(){
        if(Keyboard.isKeyUp(POWERUP_ONE_KEY)){
            keyLocker.unlockKey(POWERUP_ONE_KEY);
        }
    }
    
    // starts the player's invincibility frames
    public void startIFrames(){
        isInvincible = true;
        timer.start();
    }

    private void playerFlash(){
        flashingPlayer = !flashingPlayer;
    }

}
