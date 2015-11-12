package Entities;

import Fundamentals.Vector2D;
import Main.GamePanel;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Player extends Humanoid{
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	public Player(LevelState ls) {
		super(ls, GamePanel.WIDTH / 2 - 10, 205, 0, 10, 0, 0, true);
		int rand = (int) (Math.random() * 15);
		this.image = SpriteStore.get().getImage("player".concat(String.valueOf(rand)));
		this.hitImage = SpriteStore.get().getImage("playerhit".concat(String.valueOf(4 % (rand+1))));
		this.setWeapon(new Pistol(ls, this));
		ammo = new Vector2D(100,100);
		health = new Vector2D(40,40);
	}

	@Override
	public boolean tick() {
		if( ammo.x == 0 ) {
			this.setWeapon(new Pistol(ls, this));
		}
		if( up ) {
			acc.y -= 0.2;
		}
		if( down ) {
			acc.y += 0.2;
		}
		if( left ) {
			acc.x -= 0.2;
		}
		if( right ) {
			acc.x += 0.2;
		}
		
		vel.x += acc.x;
		vel.y += acc.y;
		pos.x += vel.x;
		pos.y += vel.y;
		
		if( pos.y < 205 ) {
			pos.y = 205;
		}
		if( pos.y > GamePanel.HEIGHT - 40 ) {
			pos.y = GamePanel.HEIGHT - 40;
		}
		if( pos.x < 20 ) {
			pos.x = 20;
		}
		if( pos.x > GamePanel.WIDTH - 36 ) {
			pos.x = GamePanel.WIDTH - 36;
		}
		
		if( vel.x > 2 ) vel.x = 2;
		if( vel.x < -2 ) vel.x = -2;
		if( vel.y > 2 ) vel.y = 2;
		if( vel.y < -2) vel.y = -2;
		
		acc.x += (0 - acc.x) / 2;
		acc.y += (0 - acc.y) / 2;
		if(!(up || down)) {
			vel.y += (0 - vel.y) / 5;
		}
		if(!(left || right)) {
			vel.x += (0 - vel.x) / 5;
		}
		if( firing ) {
			shoot();
		}
		super.tick();
		return false;
	}
	
	public void newWeapon(int type) {
		switch(type) {
		case 1:
			this.setWeapon(new MachineGun(this.ls, this));
			ammo.x = 100;
			ammo.y = 100;
			break;
		case 2:
			this.setWeapon(new Shotgun(this.ls, this));
			ammo.x = 12;
			ammo.y = 12;
			break;
		case 3:
			this.setWeapon(new Flamethrower(this.ls, this));
			ammo.x = 120;
			ammo.y = 120;
			break;
		}
	}
	
	public void heal() {
		this.health.x += (int) (Math.random() * health.y) + (health.y / 4);
		if( health.x > health.y ) {
			health.x = health.y;
		}
	}

	public boolean getUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean getDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean getLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
