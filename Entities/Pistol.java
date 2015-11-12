package Entities;

import java.awt.Graphics2D;
import java.awt.Point;

import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import StateManager.LevelState;

public class Pistol extends Weapon {
	
	public AudioPlayer shot0 = SoundStore.get().getSound("pistol");
	public AudioPlayer shot1 = SoundStore.get().getSound("machinegun");

	public Pistol(LevelState ls, Humanoid owner) {
		super(ls, owner, "pistol");
	}

	@Override
	public void shoot() {
		if (owner.isFiring())
			return;
		kickBack = 7;
		double rotation = this.getRotation() + Math.PI / 2;
		double dx = Math.cos(rotation) * 8;
		double dy = Math.sin(rotation) * 8;
		if (owner.isPlayer()) {
			shot0.play();
			ls.sm.game.shake(2);
			unloadShell(0);
			ls.playerBullets.add(new PlayerBullet(ls, pos.x, pos.y + 5, dx, dy,
					0, 0, rotation, 50, false));
		} else {
			int rand = (int) (Math.random() * 100);
			if (rand == 0) {
				shot1.play();
				unloadShell(0);
				ls.enemyBullets.add(new EnemyBullet(ls, pos.x, pos.y + 5, dx,
						dy, 0, 0, rotation, 50, false));
			}
		}
	}

	@Override
	public void render(Graphics2D g) {

		int translate = (owner.getLeftImage()) ? 1 : 0;
		int transform = (owner.getLeftImage()) ? -1 : 1;
		Point rp = new Point((int) pos.x + 5, (int) pos.y + 9);
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
