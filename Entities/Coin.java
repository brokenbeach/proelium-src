package Entities;

import java.awt.Color;
import java.awt.Graphics2D;

import Fundamentals.Entity;
import Fundamentals.Particle;
import Main.GamePanel;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Coin extends Entity{

	public Coin(LevelState ls, double x, double y, double dx, double dy) {
		super(ls, x, y, dx, dy, 0, 0);
		image = SpriteStore.get().getImage("coin");
	}

	@Override
	public boolean tick() {
		vel.x += (0 - vel.x) / 10;
		vel.y += (0 - vel.y) / 10;
		pos.x += vel.x;
		pos.y += vel.y;
		if( pos.y < 220 ) {
			pos.y = 220;
			vel.y = -vel.y;
		}
		if( pos.y > GamePanel.HEIGHT - 40 ) {
			pos.y = GamePanel.HEIGHT - 40;
			vel.y = -vel.y;
		}
		if( pos.x < 20 ) {
			pos.x = 20;
			vel.x = -vel.x;
		}
		if( pos.x > GamePanel.WIDTH - 36 ) {
			pos.x = GamePanel.WIDTH - 36;
			vel.x = -vel.x;
		}
		updateHitbox();
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(image, (int) pos.x, (int) pos.y, null);
	}

	@Override
	public boolean hit() {
		ls.coin();
		for (int i = 0; i < 15; i++) {
			double dx = 7 * (-0.5 + Math.random());
			double dy = 5 * (-0.9 + Math.random());
			int size = (int) (Math.random() * 5) + 3;
			ls.particles.add(new Particle(pos.x, pos.y, dx, dy, 0, 0.3, 20, 20,
					Color.YELLOW, size));
		}
		return true;
	}

}
