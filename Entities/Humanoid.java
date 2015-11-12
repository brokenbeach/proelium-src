package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Fundamentals.Entity;
import Fundamentals.Particle;
import Fundamentals.Vector2D;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import StateManager.LevelState;

public class Humanoid extends Entity {

	protected Vector2D health;
	protected Vector2D ammo;
	boolean firing;

	private boolean player;
	private boolean leftImage = false;
	private Weapon weapon;

	protected boolean hit = false;
	protected BufferedImage hitImage;
	protected long hitStartTime;
	protected long hitDelay = 75;
	
	AudioPlayer hurt;

	public Humanoid(LevelState ls, double x, double y, double dx, double dy,
			double ddx, double ddy, boolean player) {
		super(ls, x, y, dx, dy, ddx, ddy);
		this.player = player;
		hurt = SoundStore.get().getSound("hurt");
	}

	@Override
	public boolean tick() {
		if( health.x <= 0 ) {
			hit();
			return true;
		}
		if (!player) {
			if (ls.player.pos.x < pos.x) {
				leftImage = true;
			} else {
				leftImage = false;
			}
		}
		if( hitDelay == -1 ) return false;
		long elapsed0 = (System.nanoTime() - hitStartTime) / 1000000;
		if(elapsed0 > hitDelay) {
			hit = false;
			hitStartTime = System.nanoTime();
		}
		getWeapon().tick();
		updateHitbox();
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		int translate = (getLeftImage()) ? 1 : 0;
		int transform = (getLeftImage()) ? -1 : 1;

		if (hit) {
			g.drawImage(hitImage, (int) pos.x + translate * image.getWidth(),
					(int) pos.y, transform * image.getWidth(),
					image.getHeight(), null);
		} else {
			g.drawImage(image, (int) pos.x + translate * image.getWidth(),
					(int) pos.y, transform * image.getWidth(),
					image.getHeight(), null);
		}
		getWeapon().render(g);
	}

	public void shoot() {
		getWeapon().shoot();
	}

	@Override
	public boolean hit(Vector2D bulletvel) {
		hit = true;
		hurt.play();
		if(player) {
			ls.damageFlash(0.2f);
		}
		getHealth().x--;
		for (int i = 0; i < 10; i++) {
			double dx = 2 * ((bulletvel.x / 2) * (Math.random()));
			double dy = 2 * ((bulletvel.y / 2) * (Math.random()));
			int size = (int) (Math.random() * 12) + 5;
			ls.particles.add(new Particle(pos.x, pos.y, dx, dy, 0, 0, 20, 20,
					new Color( (int) (120 + Math.random() * 135), 80,80), size));
		}
		if (getHealth().x <= 0) {
			if(!player) {
				int rand = (int) (Math.random() * 4);
				if( rand == 0 || (this instanceof ZombieEnemy)) {
					explode();
				}
				rand = (int) (Math.random() * 5);
				if( rand == 0 ) {
					rand = (int) (Math.random() * 4);
					if( rand != 0 ) {
						rand = (int) (Math.random() * 3) + 1;
						ls.pickups.add(new WeaponBox(ls, pos.x, pos.y, bulletvel.x / 3, bulletvel.y / 3, rand));
					}else {
						ls.pickups.add(new HealthKit(ls, pos.x, pos.y, bulletvel.x / 3, bulletvel.y / 3));
					}
				}
				rand = (int) (Math.random() * 3) + 1;
				for(int i = 0; i < rand; i++ ) {
					double dx = (10 * (-0.5 * Math.random()));
					double dy = (10 * (-0.5 * Math.random()));
					ls.pickups.add(new Coin(ls, pos.x, pos.y, dx * (bulletvel.x / 5), dy * (bulletvel.y / 5)));
				}
			}
			if( player ) {
				bigexplode();
			}
			return true;
		}
		return false;
	}
	
	private void explode() {
		gib(0);
		int rand = (int) (Math.random() * 3) + 1;
		for(int i = 0; i < rand; i++ ) {
			gib(1);
		}
		rand = (int) (Math.random() * 3) + 1;
		for(int i = 0; i < rand; i++ ) {
			gib(2);
		}
	}
	
	private void bigexplode() {
		gib(0);
		int rand = (int) (Math.random() * 4) + 3;
		for(int i = 0; i < rand; i++ ) {
			gib(1);
		}
		rand = (int) (Math.random() * 4) + 3;
		for(int i = 0; i < rand; i++ ) {
			gib(2);
		}
	}
	
	public void gib(int type) {
		double dx = 9 * (-0.5 + Math.random());
		double dy = -((2 * Math.random()) + 2);
		double theta = Math.random();
		ls.gibs.add(new Gib(ls, pos.x, pos.y, dx, dy, 0, 0, theta, pos.y + ((-0.5 + Math.random()) * 30), type));
	}

	public boolean getLeftImage() {
		return leftImage;
	}

	public void setLeftImage(boolean leftImage) {
		this.leftImage = leftImage;
	}
	public boolean isPlayer() {
		return this.player;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public boolean isFiring() {
		return this.firing;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}

	public Vector2D getHealth() {
		return health;
	}

	public void setHealth(Vector2D health) {
		this.health = health;
	}

}
