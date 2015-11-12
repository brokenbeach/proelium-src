package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Fundamentals.Entity;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public abstract class Weapon extends Entity {
	
	protected Humanoid owner;
	protected BufferedImage hitImage;
	protected double kickBack = 0.0;

	public Weapon(LevelState ls, Humanoid owner, String ref) {
		super(ls, owner.pos.x, owner.pos.y, 0, 0, 0, 0);
		this.owner = owner;
		this.image = SpriteStore.get().getImage(ref);
		this.hitImage = SpriteStore.get().getImage("hit".concat(ref));
	}

	@Override
	public boolean tick() {
		Vector2D target = new Vector2D(0, 0);
		if( owner.isPlayer() ) {
			target = new Vector2D( ls.CURSOR.x, ls.CURSOR.y );
		}else {
			target = ls.player.pos;
		}
		
		double xDiff = (target.x - pos.x);
		double yDiff = (target.y - pos.y);
		setRotation(Math.atan2(yDiff, xDiff) - Math.PI / 2);
		
		kickBack += (0 - kickBack) / 10;
		if( kickBack < 0.1 ) kickBack = 0;
		pos.x = owner.pos.x + 2;
		pos.y = owner.pos.y + 4;
		updateHitbox();
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		int translate = (owner.getLeftImage()) ? 1 : 0;
		int transform = (owner.getLeftImage()) ? -1 : 1;
		Point rp = new Point( (int)pos.x + 5, (int) pos.y + 9);
		g.rotate(getRotation(), rp.x , rp.y);
		
		if( owner.hit()) {
			g.drawImage(hitImage, (int) pos.x + translate * 10, (int) pos.y , transform * image.getWidth(), image.getHeight(), null);
		}else {
			g.drawImage(image, (int) pos.x + translate * 10, (int) pos.y, transform * image.getWidth(), image.getHeight(), null);
		}
		g.rotate(-getRotation(), rp.x, rp.y);
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void unloadShell(int type) {
		int dir = (owner.getLeftImage()) ? 1 : -1;
		double dx = 1.5 * dir * (0.5 + Math.random());
		double dy = -Math.random();
		double theta = dir * Math.random();
		ls.shells.add(new Shell(ls, pos.x, pos.y, dx, dy, 0, 0, theta, pos.y + 10, type));
	}
	
	public abstract void shoot();

}
