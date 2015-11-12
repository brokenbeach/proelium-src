package Entities;

import java.awt.Color;
import java.awt.Graphics2D;

import Fundamentals.Entity;
import Fundamentals.Particle;
import Main.GamePanel;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Bullet extends Entity {

	public AudioPlayer hit = SoundStore.get().getSound("hit");
	private int life;
	private boolean slow;

	public Bullet(LevelState ls, double x, double y, double dx, double dy,
			double ddx, double ddy, double rotation, String ref, int life,
			boolean slow) {
		super(ls, x, y, dx, dy, ddx, ddy);
		this.setRotation(rotation);
		image = SpriteStore.get().getImage(ref);
		this.life = life;
		this.slow = slow;
	}

	@Override
	public boolean tick() {
		life--;
		if (life <= 0) {
			explode();
			return true;
		}

		if (slow) {
			vel.x += (0 - vel.x) / 17;
			vel.y += (0 - vel.y) / 17;
		}

		if (pos.y < 210 || pos.y > GamePanel.HEIGHT - 10 || pos.x < 25 || pos.x > GamePanel.WIDTH - 30) {
			hit.play();
			explode();
			return true;
		}

		vel.x += acc.x;
		vel.y += acc.y;
		pos.x += vel.x;
		pos.y += vel.y;
		updateHitbox();
		return false;
	}
	
	private void explode() {
		for (int i = 0; i < 5; i++) {
			double dx = 2 * (-(vel.x / 2) * (Math.random()));
			double dy = 2 * (-(vel.y / 2) * (Math.random()));
			int size = (int) (Math.random() * 3) + 3;
			ls.particles.add(new Particle(pos.x, pos.y, dx, dy, 0, 0, 20,
					20, Color.YELLOW, size));
		}
		for (int i = 0; i < 5; i++) {
			double dx = 2 * (-(vel.x / 2) * (Math.random()));
			double dy = 2 * (-(vel.y / 2) * (Math.random()));
			int size = (int) (Math.random() * 3) + 3;
			ls.particles.add(new Particle(pos.x, pos.y, dx, dy, 0, 0, 20,
					20, Color.WHITE, size));
		}
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

		return true;
	}

}
