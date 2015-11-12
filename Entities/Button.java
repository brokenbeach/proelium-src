package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Fundamentals.Entity;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Button extends Entity {

	public AudioPlayer sound = SoundStore.get().getSound("gameover");
	boolean pressed;
	BufferedImage pressedImage;

	public Button(LevelState ls, double x, double y) {
		super(ls, x, y, 0, 0, 0, 0);
		image = SpriteStore.get().getImage("button");
		pressedImage = SpriteStore.get().getImage("buttonpressed");
		updateHitbox();
	}

	@Override
	public boolean tick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		if (pressed) {
			g.drawImage(pressedImage, (int) pos.x, (int) pos.y, null);
		} else {
			g.drawImage(image, (int) pos.x, (int) pos.y, null);
		}
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean isPressed() {
		return this.pressed;
	}
	
	public void press() {
		ls.sm.game.shake(10);
		sound.play();
		this.pressed = true;
	}
	
	public void release() {
		this.pressed = false;
	}

}
