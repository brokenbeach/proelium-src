package Behaviors;

import Entities.Enemy;
import Entities.ZombieHands;

public class ZombieBehavior extends Behavior {

	double offset;

	public ZombieBehavior(Enemy npc) {
		super(npc);
		npc.setWeapon(new ZombieHands(npc.ls, npc));
		offset = 0.2 + Math.random();
	}

	@Override
	public boolean tick() {
		double a = npc.pos.x - npc.ls.player.pos.x;
		double o = npc.pos.y - npc.ls.player.pos.y;
		double h = Math.sqrt(a * a + o * o);

		double rotation = (npc.getWeapon().getRotation() + Math.PI / 2);
		npc.vel.x = Math.cos(rotation) * 0.6 * offset;
		npc.vel.y = Math.sin(rotation) * 0.6 * offset;
		if (h > 10) {
			npc.pos.x += npc.vel.x;
			npc.pos.y += npc.vel.y;
		}
		if (h < 20) {
			npc.shoot();
		}
		return false;
	}

}
