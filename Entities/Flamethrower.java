package Entities;

import Fundamentals.Vector2D;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import StateManager.LevelState;

public class Flamethrower extends Weapon{

	public AudioPlayer shot0 = SoundStore.get().getSound("flamethrower");
	
	private long shootStartTime;
	private long shootDelay = 40;
	
	private long soundStartTime;
	private long soundDelay = 150;
	
	public Flamethrower(LevelState ls, Humanoid owner) {
		super(ls, owner, "flamethrower");
		// TODO Auto-generated constructor stub
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
		pos.y = owner.pos.y + 11;
		updateHitbox();
		return false;
	}

	@Override
	public void shoot() {
		if (owner.isPlayer()) {
			ls.sm.game.shake(2);
		}
		kickBack = 4;
		if (owner.isPlayer()) {
			if (shootDelay == -1)
				return;
			long elapsed0 = (System.nanoTime() - shootStartTime) / 1000000;
			if (elapsed0 > shootDelay) {
				elapsed0 = (System.nanoTime() - soundStartTime) / 1000000;
				if( elapsed0 > soundDelay ) {
					shot0.play();
					soundStartTime = System.nanoTime();
				}
				
				owner.ammo.x--;
				for(int i = 0; i < 4; i++ ) {
					double rotation = (this.getRotation() + Math.PI / 2) + 0.5 * (-0.5 + Math.random());
					double dx = Math.cos(rotation) * 9 + (Math.random() * 2);
					double dy = Math.sin(rotation) * 9 + (Math.random() * 2);
					int life = (int) (60 + (Math.random() * 60));
					ls.playerFlame.add(new Flame(ls, pos.x, pos.y + 5, dx, dy, 0, 0, rotation, life));
				}
				shootStartTime = System.nanoTime();
			}

		} else {
			int rand = (int) (Math.random() * 10);
			if (rand == 0) {
				for(int i = 0; i < 8; i++ ) {
					double rotation = (this.getRotation() + Math.PI / 2) + 1.5 * (-0.5 + Math.random());
					double dx = Math.cos(rotation) * 10 + (Math.random() * 2);
					double dy = Math.sin(rotation) * 10 + (Math.random() * 2);
					int life = (int) (18 + (Math.random() * 15));
					ls.enemyBullets.add(new EnemyBullet(ls, pos.x, pos.y + 5, dx, dy, 0, 0, rotation, life, true));
				}
			}
		}
	}

}
