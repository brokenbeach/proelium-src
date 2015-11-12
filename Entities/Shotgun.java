package Entities;

import java.awt.Graphics2D;
import java.awt.Point;

import Fundamentals.Vector2D;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import StateManager.LevelState;

public class Shotgun extends Weapon {

	public AudioPlayer shot = SoundStore.get().getSound("shotgun");
	private long shootStartTime;
	private long shootDelay = 800;

	public Shotgun(LevelState ls, Humanoid owner) {
		super(ls, owner, "shotgun");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot() {
		if (owner.isFiring())
			return;
		if (shootDelay == -1)
			return;
		long elapsed0 = (System.nanoTime() - shootStartTime) / 1000000;
		if (elapsed0 > shootDelay) {
			if (owner.isPlayer()) {
				ls.sm.game.shake(3);
			}
			kickBack = 10;
			if (owner.isPlayer()) {
				shot.play();
				unloadShell(1);
				owner.ammo.x--;
				for (int i = 0; i < 14; i++) {
					double rotation = (this.getRotation() + Math.PI / 2) + 1.5
							* (-0.5 + Math.random());
					double dx = Math.cos(rotation) * 14 + (Math.random() * 1);
					double dy = Math.sin(rotation) * 14 + (Math.random() * 5);
					int life = (int) (30 + (Math.random() * 15));
					ls.playerBullets.add(new PlayerBullet(ls, pos.x, pos.y + 5,
							dx, dy, 0, 0, rotation, life, true));
				}
			} else {
				shot.play();
				unloadShell(1);
				for (int i = 0; i < 8; i++) {
					double rotation = (this.getRotation() + Math.PI / 2) + 1.5
							* (-0.5 + Math.random());
					double dx = Math.cos(rotation) * 10 + (Math.random() * 2);
					double dy = Math.sin(rotation) * 10 + (Math.random() * 2);
					int life = (int) (18 + (Math.random() * 15));
					ls.enemyBullets.add(new EnemyBullet(ls, pos.x, pos.y + 5,
							dx, dy, 0, 0, rotation, life, true));
				}
			}
			shootStartTime = System.nanoTime();
		}

	}

	@Override
	public boolean tick() {
		Vector2D target = new Vector2D(0, 0);
		if (owner.isPlayer()) {
			target = new Vector2D(ls.CURSOR.x, ls.CURSOR.y);
		} else {
			target = ls.player.pos;
		}

		double xDiff = (target.x - pos.x);
		double yDiff = (target.y - pos.y);
		setRotation(Math.atan2(yDiff, xDiff) - Math.PI / 2);

		kickBack += (0 - kickBack) / 10;
		if (kickBack < 0.1)
			kickBack = 0;
		pos.x = owner.pos.x + 2;
		pos.y = owner.pos.y + 11;
		updateHitbox();
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		int translate = (owner.getLeftImage()) ? 1 : 0;
		int transform = (owner.getLeftImage()) ? -1 : 1;
		Point rp = new Point((int) pos.x + 5, (int) pos.y + 4);
		g.rotate(getRotation(), rp.x, rp.y);
		if (owner.hit) {
			g.drawImage(hitImage, (int) pos.x + translate * 10,
					(int) (pos.y + (-kickBack)), transform * image.getWidth(),
					image.getHeight(), null);
		} else {
			g.drawImage(image, (int) pos.x + translate * 10,
					(int) (pos.y + (-kickBack)), transform * image.getWidth(),
					image.getHeight(), null);
		}
		g.rotate(-getRotation(), rp.x, rp.y);
	}

}
