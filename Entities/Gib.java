package Entities;

import java.awt.Color;
import java.awt.Graphics2D;

import Fundamentals.Entity;
import Fundamentals.Particle;
import Main.GamePanel;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Gib extends Entity {
	boolean resting = false;
	double destY;
	private long restStartTime;
	private long restDelay = 30000;
	
	public Gib(LevelState ls, double x, double y, double dx, double dy, double ddx, double  ddy, double theta, double destY, int type) {
		super(ls, x, y, dx, dy, ddx, ddy);
		this.theta = theta;
		this.destY = destY;
		this.image = SpriteStore.get().getImage("gib".concat(String.valueOf(type)));
	}

	@Override
	public boolean tick() {
		if( resting ) {
			long elapsed = (System.nanoTime() - restStartTime) / 1000000;
			if (elapsed > restDelay) {
				return true;
			}else {
				return false;
			}
		}
		
		int size = (int) (Math.random() * 8) + 5;
		ls.particles.add(new Particle(pos.x, pos.y, 0, 0.3, 0, 0, 20, 20,
				new Color( (int) (120 + Math.random() * 135), 80,80), size));
		
		if( pos.y > destY ) {
			resting = true;
			restStartTime = System.nanoTime();
		}
		
		setRotation(getRotation()+theta);
		acc.x += (0 - acc.x) / 10;
		acc.y += (0.5 - acc.y) / 10;
		vel.x += acc.x;
		vel.y += acc.y;
		pos.x += vel.x;
		pos.y += vel.y;
		
		if( pos.y < 220 ) {
			pos.y = 220;
			vel.y = -vel.y;
		}
		if( pos.y > GamePanel.HEIGHT - 40 ) {
			pos.y = GamePanel.HEIGHT - 40;
			vel.y = 0;
		}
		if( pos.x < 20 ) {
			pos.x = 20;
			vel.x = -vel.x;
		}
		if( pos.x > GamePanel.WIDTH - 30 ) {
			pos.x = GamePanel.WIDTH - 30;
			vel.x = -vel.x;
		}
		
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		g.rotate(getRotation(), pos.x, pos.y);
		g.drawImage(image, (int) pos.x - image.getWidth() / 2, (int) pos.y
				- image.getHeight() / 2, null);
		g.rotate(-getRotation(), pos.x, pos.y);
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}
}
