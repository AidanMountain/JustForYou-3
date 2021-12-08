package Level;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import Utils.Stopwatch;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
	protected boolean talkedTo = false;
	protected SpriteFont message;
	protected int talkedToTime;
	protected Stopwatch timer = new Stopwatch();

	public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation, int talkedToTime) {
		super(x, y, spriteSheet, startingAnimation);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}
	
	public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation, int talkedToTime, String speech) {
		super(x, y, spriteSheet, startingAnimation);
		this.message = createMessage();
		this.message.setText(speech);
		this.talkedToTime = talkedToTime;
	}

	public NPC(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation, int talkedToTime) {
		super(x, y, animations, startingAnimation);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	public NPC(BufferedImage image, float x, float y, String startingAnimation, int talkedToTime) {
		super(image, x, y, startingAnimation);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	public NPC(BufferedImage image, float x, float y, int talkedToTime) {
		super(image, x, y);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale) {
		super(image, x, y, scale);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale, ImageEffect imageEffect) {
		super(image, x, y, scale, imageEffect);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	public NPC(BufferedImage image, float x, float y, int talkedToTime, float scale, ImageEffect imageEffect,
			Rectangle bounds) {
		super(image, x, y, scale, imageEffect, bounds);
		this.message = createMessage();
		this.talkedToTime = talkedToTime;
	}

	protected SpriteFont createMessage() {
		return new SpriteFont("default", getX(), getY() - 10, "Arial", 12, Color.BLACK);
	}

	public void update(Player player) {
		super.update();
		checkTalkedTo(player);
	}

	public void checkTalkedTo(Player player) {
		if (intersects(player) && Keyboard.isKeyDown(Key.SPACE)) {
			talkedTo = true;
			timer.setWaitTime(talkedToTime);
		}
		;
		if (talkedTo && timer.isTimeUp()) {
			talkedTo = false;
		}
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		super.draw(graphicsHandler);
		if (message != null && talkedTo) {
			drawMessage(graphicsHandler);
		}
	}

	public void drawMessage(GraphicsHandler graphicsHandler) {
		// draws a box with a border (think like a speech box)
		graphicsHandler.drawFilledRectangleWithBorderAndText(Math.round(getCalibratedXLocation() - 2), Math.round(getCalibratedYLocation() - 24), 25, Color.WHITE, Color.BLACK, 2, message.getText());
				
		// draws message in the above speech box
		message.setLocation(getCalibratedXLocation() + 2, getCalibratedYLocation() - 8);
		message.draw(graphicsHandler);
	}
	
	public boolean getTalkedTo(){ return talkedTo;}
	
}

