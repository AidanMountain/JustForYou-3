package Screens;

import java.awt.Color;

import javax.swing.JFrame;
import Level.Player;
import Level.PlayerListener;
import Players.Cat;
import Engine.Config;
import Engine.GamePanel;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Camera;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import Engine.*;
import Level.*;
import Maps.LevelFour;
import Maps.LevelThree;
import Maps.LevelTwo;
import Maps.LevelOne;
import Players.Cat;
import Engine.GameWindow;
import Screens.RebuildScreen;

import java.awt.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JOptionPane;


public class SettingsScreen extends Screen{
	protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont settingsLabel;
    protected SpriteFont aspectRatioLabel, lowRatioLabel, mediumRatioLabel, largeRatioLabel;
    protected SpriteFont volumeLabel, muteVolumeLabel, mediumVolumeLabel, fullVolumeLabel;
    protected SpriteFont returnSettingsLabel, usagePrompt;
    protected int currentSettingHovered = 0;
    protected int settingSelected = -1;
    protected Stopwatch keyTimer = new Stopwatch();
    protected int pointerLocationX, pointerLocationY;
    private Config config;
    private MusicData mD;
    private GameWindow gameWindow;
    protected Player player;
    protected boolean settingsActive = false;
    protected boolean volActive = false;
    protected boolean aspectActive = false;
    public boolean screenS = true;
    public boolean screenM = false;
    public boolean screenL = false;
    protected SpriteFont volumeLevel;
    public static double vol = 0.25;
    
    protected SpriteFont aspectRatioLevel;
    
    public SettingsScreen(ScreenCoordinator screenCoordinator, GameWindow gameWindow, MusicData musicData) {
    	this.gameWindow = gameWindow;
    	this.mD = musicData;
    	this.screenCoordinator = screenCoordinator;
    }
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		background = new TitleScreenMap();
		background.setAdjustCamera(false);
		settingsLabel = new SpriteFont("Settings", 15, 35, "Times New Roman", 30, Color.white);
		returnSettingsLabel = new SpriteFont("Press Escape to return to the Menu", 20, 560, "Times New Roman", 30, Color.white);
		usagePrompt = new SpriteFont("Use the Arrow Keys and Enter to select your settings", 125, 75,"Times New Roman", 25, Color.white);
        volumeLabel = new SpriteFont("Volume:", 205, 125, "Times New Roman", 40, Color.white);
        volumeLabel.setOutlineColor(Color.black);
        volumeLabel.setOutlineThickness(2);
        muteVolumeLabel = new SpriteFont("Mute", 75, 175, "Times New Roman", 30, Color.white);
        muteVolumeLabel.setOutlineColor(Color.black);
        muteVolumeLabel.setOutlineThickness(2);
        mediumVolumeLabel = new SpriteFont("Medium", 225, 175, "Times New Roman", 30, Color.white);
        mediumVolumeLabel.setOutlineColor(Color.black);
        mediumVolumeLabel.setOutlineThickness(2);
        fullVolumeLabel = new SpriteFont("Full", 425, 175, "Times New Roman", 30, Color.white);
        fullVolumeLabel.setOutlineColor(Color.black);
        fullVolumeLabel.setOutlineThickness(2);
        aspectRatioLabel = new SpriteFont("Aspect Ratio:", 170, 250, "Times New Roman", 40, Color.white);
        aspectRatioLabel.setOutlineColor(Color.black);
        aspectRatioLabel.setOutlineThickness(2);
        lowRatioLabel = new SpriteFont("Small", 75, 300, "Times New Roman", 30, Color.white);
        lowRatioLabel.setOutlineColor(Color.black);
        lowRatioLabel.setOutlineThickness(2);
        mediumRatioLabel = new SpriteFont("Medium", 225, 300, "Times New Roman", 30, Color.white);
        mediumRatioLabel.setOutlineColor(Color.black);
        mediumRatioLabel.setOutlineThickness(2);
        largeRatioLabel = new SpriteFont("Large", 425, 300, "Times New Roman", 30, Color.white);
        largeRatioLabel.setOutlineColor(Color.black);
        largeRatioLabel.setOutlineThickness(2);
        keyTimer.setWaitTime(200);
		keyLocker.lockKey(Key.ESC);
        this.player = new Cat(background.getPlayerStartPosition().x, background.getPlayerStartPosition().y);
	}

	@Override
	public void update() {
		//update background map (to play title animations)
		  background.update(player);

        // if arrow keys are pressed, user navigates the settings menu
        if(Keyboard.isKeyDown(Key.RIGHT) && keyTimer.isTimeUp()) {
        	keyTimer.reset();
        	currentSettingHovered ++;
        }
        else if(Keyboard.isKeyDown(Key.LEFT) && keyTimer.isTimeUp()) {
        	keyTimer.reset();
        	currentSettingHovered --;
        }
        //if right is pressed on the last menu item or left is pressed on the first it loops back
        if(currentSettingHovered > 5) currentSettingHovered = 0;
        else if(currentSettingHovered < 0) currentSettingHovered = 5;
       
        //sets location for blue square in front of text (pointerLocations)
        if(currentSettingHovered == 0) {
    		muteVolumeLabel.setColor(Color.yellow);
    		mediumVolumeLabel.setColor(Color.white);
    		fullVolumeLabel.setColor(Color.white);
    		pointerLocationX = 50;
            pointerLocationY = 155;
            lowRatioLabel.setColor(Color.white);
        	mediumRatioLabel.setColor(Color.white);
        	largeRatioLabel.setColor(Color.white);
        	}
    	else if(currentSettingHovered == 1) {
    		muteVolumeLabel.setColor(Color.white);
    		mediumVolumeLabel.setColor(Color.yellow);
    		fullVolumeLabel.setColor(Color.white);
    		pointerLocationX = 200;
            pointerLocationY = 155;
            lowRatioLabel.setColor(Color.white);
        	mediumRatioLabel.setColor(Color.white);
        	largeRatioLabel.setColor(Color.white);
    	}
    	else if(currentSettingHovered == 2) {
    		muteVolumeLabel.setColor(Color.white);
    		mediumVolumeLabel.setColor(Color.white);
    		fullVolumeLabel.setColor(Color.yellow);
    		pointerLocationX = 400;
            pointerLocationY = 155;
            lowRatioLabel.setColor(Color.white);
        	mediumRatioLabel.setColor(Color.white);
        	largeRatioLabel.setColor(Color.white);
    	}
    	
    	else if(currentSettingHovered == 3) {
    		lowRatioLabel.setColor(Color.yellow);
    		mediumRatioLabel.setColor(Color.white);
    		largeRatioLabel.setColor(Color.white);
    		pointerLocationX = 50;
            pointerLocationY = 280;
            muteVolumeLabel.setColor(Color.white);
        	mediumVolumeLabel.setColor(Color.white);
        	fullVolumeLabel.setColor(Color.white);
    	}
    	else if(currentSettingHovered == 4) {
    		lowRatioLabel.setColor(Color.white);
    		mediumRatioLabel.setColor(Color.yellow);
    		largeRatioLabel.setColor(Color.white);
    		pointerLocationX = 200;
            pointerLocationY = 280;
            muteVolumeLabel.setColor(Color.white);
        	mediumVolumeLabel.setColor(Color.white);
        	fullVolumeLabel.setColor(Color.white);
    	}
    	else if(currentSettingHovered == 5) {
    		lowRatioLabel.setColor(Color.white);
    		mediumRatioLabel.setColor(Color.white);
    		largeRatioLabel.setColor(Color.yellow);
    		pointerLocationX = 400;
            pointerLocationY = 280;
            muteVolumeLabel.setColor(Color.white);
        	mediumVolumeLabel.setColor(Color.white);
        	fullVolumeLabel.setColor(Color.white);
    	}
        // if space is enter on settings item, change to appropriate setting based on which menu item was chosen
        if(Keyboard.isKeyUp(Key.ENTER)) {
        	keyLocker.unlockKey(Key.ENTER);
        }
        if(!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER)) {
        	settingSelected = currentSettingHovered;
        	if(settingSelected == 0) {
        		setVolMute();
        	}
        	else if(settingSelected == 1) {
        		setVolMid();
        	}
        	else if(settingSelected == 2) {
        		setVolFull();
        	}
        	else if(settingSelected == 3) {
        		setScreenSmall();
        	}
        	else if(settingSelected == 4) {
        		setScreenMid();
        	}
        	else if(settingSelected == 5) {
        		setScreenLarge();
        	}
        }
        // if escape is pressed, go back to main menu
	if(Keyboard.isKeyUp(Key.ESC)) {
		keyLocker.unlockKey(Key.ESC);
		
	}
        else if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// TODO Auto-generated method stub
		background.draw(graphicsHandler);
		settingsLabel.draw(graphicsHandler);
		returnSettingsLabel.draw(graphicsHandler);
		volumeLabel.draw(graphicsHandler);
		muteVolumeLabel.draw(graphicsHandler);
		mediumVolumeLabel.draw(graphicsHandler);
		fullVolumeLabel.draw(graphicsHandler);
		aspectRatioLabel.draw(graphicsHandler);
		lowRatioLabel.draw(graphicsHandler);
		mediumRatioLabel.draw(graphicsHandler);
		largeRatioLabel.draw(graphicsHandler);
		usagePrompt.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(49, 207, 240), Color.black, 2);
	}
	public void setVolMute()
    {
        //System.out.println("screen vol low");
        mD.setVolCall("Mute");
        //System.out.println("Current Vol: " + vol);

    }
    public void setVolMid()
    {
        //System.out.println("screen vol mid");
        mD.setVolCall("Mid");
       // System.out.println("Current Vol: " + vol);
    }
    public void setVolFull()
    {
        //System.out.println("screen vol high");
        mD.setVolCall("Full");
       // System.out.println("Current Vol: " + vol);
    }

    public void setScreenSmall() 
    {
    	screenS = true;
		screenM = false;
		screenL = false;
		config.WIDTH = 800;
		config.HEIGHT = 605;
        Camera.setMultiplyInt(140);
		gameWindow.paintWindow();
		
    }
    public void setScreenMid() 
    {
    	screenS = false;
		screenM = true;
		screenL = false;
		config.WIDTH = 950;
		config.HEIGHT = 705;
        Camera.setMultiplyInt(240);
		gameWindow.paintWindow();
    }
    public void setScreenLarge() 
    {
		screenS = false;
		screenM = false;
		screenL = true;
		
		config.WIDTH = 1100;
		config.HEIGHT = 710;
		Camera.setMultiplyInt(310);
		gameWindow.paintWindow();
    }
}