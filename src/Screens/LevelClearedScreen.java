package Screens;
import Engine.*;
import SpriteFont.SpriteFont;
import Level.Player;

import java.awt.*;

// This class is for the level cleared screen
public class LevelClearedScreen extends Screen {
    protected SpriteFont winMessage;

    private Player player;
    public LevelClearedScreen(Player player) {
        this.player = player;
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Level Cleared", (Config.WIDTH / 2) - 80 , (Config.HEIGHT / 2) - 50, "Comic Sans", 30, Color.white);
        player.setCheckPoint(null);
    }

    @Override
    public void update() {

    }

    public void draw(GraphicsHandler graphicsHandler) {
        // paint entire screen black and dislpay level cleared text
        graphicsHandler.drawFilledRectangle(0, 0, Config.WIDTH, Config.HEIGHT, Color.black);
        winMessage.draw(graphicsHandler);
    }
}
