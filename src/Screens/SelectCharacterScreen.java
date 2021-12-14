package Screens;

import java.awt.Color;

import javax.swing.JFrame;

import Players.Cat;
import Players.Baby;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.ImageEffect;
import GameObject.Sprite;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import Engine.*;
import Level.*;
import Maps.LevelFour;
import Maps.LevelThree;
import Maps.LevelTwo;
import Maps.LevelOne;
import Screens.RebuildScreen;

import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;


public class SelectCharacterScreen extends Screen{
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected int optionHovered = 0;
    protected int optionSelected = -1;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    protected Player player;
    protected SpriteFont choseCharacterFont;
    protected SpriteFont returnInstructionsLabel;
    protected static int choseCharacter = 0;
    
    protected Sprite cat, baby;
    
    public SelectCharacterScreen(ScreenCoordinator screenCoordinator) {
    	this.screenCoordinator = screenCoordinator;
    }
	@Override
	public void initialize() {
	screenCoordinator.getGameWindow().getInputManager().setKeyLocker(keyLocker);

    // setup graphics on screen (background map, spritefont text)
	background = new TitleScreenMap();
    background.setAdjustCamera(false);
    keyLocker.lockKey(Key.ENTER);
    optionSelected = -1;
    
    // setup player
    
    cat =  new Sprite(ImageLoader.loadSubImage("Cat.png", 0, 0, 24, 24), 200, 75, 3, ImageEffect.NONE);
    baby = new Sprite(ImageLoader.loadSubImage("Baby.png", 0, 0, 24, 24), 200, 162, 3, ImageEffect.NONE);
    this.player = new Cat(background.getPlayerStartPosition().x, background.getPlayerStartPosition().y);
    returnInstructionsLabel = new SpriteFont("Press ENTER to select Player. Press ESC to return to the menu.", 20, 560, "Times New Roman", 30, Color.WHITE);
    keyTimer.setWaitTime(200);
    keyLocker.lockKey(Key.SPACE);
    
	}

	@Override
	public void update() {
		//update background map (to play title animations)
		  background.update(player);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
	         if ((Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) && keyTimer.isTimeUp()) {
	             keyTimer.reset();
	             optionHovered++;
	         } else if ((Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W))&& keyTimer.isTimeUp()) {
	             keyTimer.reset();
	             optionHovered--;
	         }
	         if(optionHovered > 1) optionHovered = 0;
	         if(optionHovered < 0) optionHovered = 1;

	         // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
	         if (optionHovered > 3) {
	        	 optionHovered = 0;
	         } else if (optionHovered == 1) {
	        	 optionHovered = 1;
	         }         
	         // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
	         if (optionHovered == 0) {
	             pointerLocationX = 170;
	             pointerLocationY = 100;
	         } else if (optionHovered == 1) {
	             pointerLocationX = 170;
	             pointerLocationY = 180;
	         }
	        if (Keyboard.isKeyUp(Key.ENTER)) {
	            keyLocker.unlockKey(Key.ENTER);
	        }
	        
	     // if space is pressed on character it is choosen, ESC to go back to main menu after that 
	        if(!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER) && optionHovered == 0){
	            choseCharacter = 0;
	        }
	        else if(!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER) && optionHovered == 1){
	        	choseCharacter = 1;
	        }
	         
	       // if Esc is pressed, go back to main menu
	       if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
	           screenCoordinator.setGameState(GameState.MENU);
	       }
	       else if(!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER)){
	    	   //screenCoordinator.setGameState(GameState.MENU);
	       }
	       
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		background.draw(graphicsHandler);
        cat.draw(graphicsHandler);
        baby.draw(graphicsHandler);
      returnInstructionsLabel.draw(graphicsHandler);
     graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
	
	}
}

/*
cat = new Sprite(ImageLoader.loadSubImage("Cat.png", 0, 0, 24, 24), 200, 75, 3, ImageEffect.NONE);
aidan = new Sprite(ImageLoader.loadSubImage("Baby.png", 0, 0, 24, 24), 200, 162, 3, ImageEffect.NONE);


*/