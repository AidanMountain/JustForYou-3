package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Level.Achievement;
import Maps.TitleScreenMap;
import Players.Cat;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class CreditsScreen extends Screen implements PlayerListener{
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont createdByLabel;
    protected SpriteFont contributorsLabel;
    protected SpriteFont returnInstructionsLabel;
    protected Player player;
    protected Achievement creditAchievement;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        screenCoordinator.getGameWindow().getInputManager().setKeyLocker(keyLocker);

        // setup graphics on screen (background map, spritefont text)
        creditAchievement = new Achievement("Over the Tree", ImageLoader.load("CreditScreenAchievement.png"), 0);
        background = new TitleScreenMap(creditAchievement);
        background.setAdjustCamera(false);
        creditsLabel = new SpriteFont("Credits", 15, 35, "Times New Roman", 30, Color.white);
        createdByLabel = new SpriteFont("Created by Alex Thimineur for Quinnipiac's SER225 Course.", 130, 140, "Times New Roman", 20, Color.white);
        contributorsLabel = new SpriteFont("Thank you to QU Alumni Brian Carducci, Joseph White,\nand Alex Hutman for their contributions.", 60, 220, "Times New Roman",20, Color.white);
        returnInstructionsLabel = new SpriteFont("Press Enter to return to the menu", 20, 560, "Times New Roman", 30, Color.white);
        keyLocker.lockKey(Key.ENTER);
        
        // setup player
        this.player = new Cat(background.getPlayerStartPosition().x, background.getPlayerStartPosition().y);
        this.player.setMap(background);
        this.player.addListener(this);
        this.player.setLocation(background.getPlayerStartPosition().x, background.getPlayerStartPosition().y);
    }

    public void update() {
    	player.update();
        background.update(player);
        
        if (Keyboard.isKeyUp(Key.ENTER)) {
            keyLocker.unlockKey(Key.ENTER);
        }

        // if escape is pressed, go back to main menu
    	if(Keyboard.isKeyUp(Key.ESC)) {
    		keyLocker.unlockKey(Key.ESC);
    		
    	}
        else if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
                screenCoordinator.setGameState(GameState.MENU);
        }
    
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        creditsLabel.draw(graphicsHandler);
        createdByLabel.draw(graphicsHandler);
        contributorsLabel.drawWithParsedNewLines(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
        player.draw(graphicsHandler);
    }

	@Override
	public void onLevelCompleted() {
	}

	@Override
	public void onDeath() {		
	}
}
