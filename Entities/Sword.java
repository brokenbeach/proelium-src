package Entities;

import java.awt.Graphics2D;
import java.awt.Point;

import StateManager.LevelState;

public class Sword extends Weapon {
	private long shootStartTime;
	private long shootDelay = 5;

	public Sword(LevelState ls, Humanoid owner) {
		super(ls, owner, "sword");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot() {
		long elapsed0 = (System.nanoTime() - shootStartTime) / 1000000;
		if (elapsed0 > shootDelay) {
			double rotation = (this.getRotation() + Math.PI / 2) + 1.5
					* (-0.5 + Math.random());
			double dx = Math.cos(rotation) * 10 + (Math.random() * 2);
			double dy = Math.sin(rotation) * 10 + (Math.random() * 2);
			int life = 2;
			ls.enemyBullets.add(new EnemyBullet(ls, pos.x, pos.y + 5, dx,
					dy, 0, 0, rotation, life, true));
			shootStartTime = System.nanoTime();
		}
	}
	
	@Override
	public void render(Graphics2D g) {

		int translate = (owner.getLeftImage()) ? 1 : 0;
		int transform = (owner.getLeftImage()) ? 1 : -1;
		Point rp = new Point( (int)pos.x + 5, (int) pos.y + 9);
		g.rotate(getRotation(), rp.x , rp.y);
		if( owner.hit) {
			g.drawImage(hitImage, (int) pos.x + translate * 10, (int) (pos.y + (-kickBack)), transform * image.getWidth(), image.getHeight(), null);
		}else {
			g.drawImage(image, (int) pos.x + translate * 10, (int) (pos.y + (-kickBack)), transform * image.getWidth(), image.getHeight(), null);
		}
		g.rotate(-getRotation(), rp.x, rp.y);
	}
}
