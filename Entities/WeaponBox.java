package Entities;

import java.awt.Graphics2D;

import Fundamentals.Entity;
import Main.GamePanel;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class WeaponBox extends Entity {

	private final int type;
	public static final int MACHINEGUN = 1;
	public static final int SHOTGUN = 2;
	public static final int FLAMETHROWER = 3;
	
	public WeaponBox(LevelState ls, double x, double y, double dx, double dy,
			int type) {
		super(ls, x, y, dx, dy, 0, 0);
		image = SpriteStore.get().getImage("weaponbox");
		this.type = type;
	}

	@Override
	public boolean tick() {
		vel.x += (0 - vel.x) / 20;
		vel.y += (0 - vel.y) / 20;
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
		ls.player.newWeapon(type);
		return true;
	}

}
