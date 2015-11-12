package Behaviors;

import Entities.Enemy;
import Entities.MachineGun;
import Fundamentals.Vector2D;
import Main.GamePanel;

public class MachineGunBehavior extends Behavior {

	private long targetStartTime;
	private long targetDelay = 50;
	private Vector2D targetLoc;

	public MachineGunBehavior(Enemy npc) {
		super(npc);
		npc.setWeapon(new MachineGun(npc.ls, npc));
	}

	@Override
	public boolean tick() {
		findNewTargetLocation();

		npc.vel.x = (targetLoc.x - npc.pos.x) / 100;
		npc.vel.y = (targetLoc.y - npc.pos.y) / 100;
		npc.pos.x += npc.vel.x;
		npc.pos.y += npc.vel.y;
		npc.shoot();
		return false;
	}

	protected boolean findNewTargetLocation() {
		if (targetDelay == -1)
			return false;

		long elapsed = (System.nanoTime() - targetStartTime) / 1000000;
		if (elapsed > targetDelay) {
			double x = 20 + (Math.random() * (GamePanel.WIDTH - 56));
			double y = 200 + (Math.random() * (GamePanel.HEIGHT - 230));
			targetLoc = new Vector2D(x, y);
			targetStartTime = System.nanoTime();
			targetDelay = (int) (1000 + (Math.random() * 5000));
		}
		return true;
	}

}
