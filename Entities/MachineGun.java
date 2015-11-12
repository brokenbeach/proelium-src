package Entities;

import Fundamentals.Vector2D;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import StateManager.LevelState;

public class MachineGun extends Weapon {
	
	public AudioPlayer shot0 = SoundStore.get().getSound("machinegun");
	public AudioPlayer shot1 = SoundStore.get().getSound("machinegun2");

	private long shootStartTime;
	private long shootDelay = 60;

	public MachineGun(LevelState ls, Humanoid owner) {
		super(ls, owner, "machinegun");
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
		kickBack = 10;
		double rotation = this.getRotation() + Math.PI / 2;
		double dx = (Math.cos(rotation) * 7) + Math.random();
		double dy = (Math.sin(rotation) * 7) + Math.random();
		if (owner.isPlayer()) {
			
			if (shootDelay == -1)
				return;
			long elapsed0 = (System.nanoTime() - shootStartTime) / 1000000;
			if (elapsed0 > shootDelay) {
				unloadShell(0);
				ls.sm.game.shake(3);
				owner.ammo.x--;
				shot0.play();
				ls.playerBullets.add(new PlayerBullet(ls, pos.x, pos.y + 5, dx,
						dy, 0, 0, rotation, 50, false));
				shootStartTime = System.nanoTime();
			}

		} else {
			int rand = (int) (Math.random() * 30);
			if (rand == 0) {
				unloadShell(0);
				shot0.play();
				ls.enemyBullets.add(new EnemyBullet(ls, pos.x, pos.y + 5, dx,
						dy, 0, 0, rotation, 50, false));
			}
		}
	}

}
