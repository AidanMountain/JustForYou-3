package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TitleScreenMap;
import Players.Cat;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;
import java.awt.*;

// This is the class for the secrets screen
public class AchievementsScreen extends Screen{
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected Player player;
    protected String creditScreenAchievement;
    protected String levelOneAchievement;
    protected SpriteFont returnInstructionsLabel;
    protected SpriteFont overTheTree;
    protected SpriteFont journeyBegins;


    public AchievementsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        overTheTree = new SpriteFont("Over The Tree", 153, 135, "Comic Sans", 15, new Color(49, 207, 240));
        overTheTree.setOutlineColor(Color.black);
        overTheTree.setOutlineThickness(2);
        journeyBegins = new SpriteFont("Journey Begins", 300, 135, "Comic Sans", 15, new Color(49, 207, 240));
        journeyBegins.setOutlineColor(Color.black);
        journeyBegins.setOutlineThickness(2);
        creditScreenAchievement = "LockedAchievement.png";
        levelOneAchievement = "LockedAchievement.png";
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        returnInstructionsLabel = new SpriteFont("Press Esc to return to the menu", 20, 560, "Times New Roman", 30, Color.white);
        this.player = new Cat(background.getPlayerStartPosition().x, background.getPlayerStartPosition().y);
    }

    @Override
    public void update() {
        background.update(player);
        if(Screen.creditAchievementStatus){creditScreenAchievement = "CreditScreenAchievement.png";}
        if(Screen.levelOneAchievementStatus){levelOneAchievement = "LevelOneAchievement.png";}

        // if space is escape, go back to main menu
    	if(Keyboard.isKeyUp(Key.ESC)) {
    		keyLocker.unlockKey(Key.ESC);
    		
    	}
        else if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
        	screenCoordinator.setGameState(GameState.MENU);
        }
    	
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
        if(Screen.creditAchievementStatus){overTheTree.draw(graphicsHandler);}
        graphicsHandler.drawFilledRectangleWithBorder(150, 150, 100, 100, Color.BLACK, Color.DARK_GRAY, 5);
        graphicsHandler.drawImage(ImageLoader.load(creditScreenAchievement), 153, 153, 95, 95);
        if(Screen.levelOneAchievementStatus){journeyBegins.draw(graphicsHandler);}
        graphicsHandler.drawFilledRectangleWithBorder(300, 150, 100, 100, Color.BLACK, Color.DARK_GRAY, 5);
        graphicsHandler.drawImage(ImageLoader.load(levelOneAchievement), 303, 153, 95, 95);
    }
}