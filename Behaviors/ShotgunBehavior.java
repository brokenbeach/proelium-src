package Behaviors;

import Entities.*;

public class ShotgunBehavior extends Behavior {

	double offset;
	
	public ShotgunBehavior(Enemy npc) {
		super(npc);
		npc.setWeapon(new Shotgun(npc.ls, npc));
		offset = 0.2 + Math.random();
	}

	@Override
	public boolean tick() {
		double a = npc.pos.x - npc.ls.player.pos.x;
		double o = npc.pos.y - npc.ls.player.pos.y;
		double h = Math.sqrt(a * a + o * o);

		double rotation = (npc.getWeapon().getRotation() + Math.PI / 2);
		npc.vel.x = Math.cos(rotation) * 1 * offset;
		npc.vel.y = Math.sin(rotation) * 1 * offset;
		if (h > 80) {
			npc.pos.x += npc.vel.x;
			npc.pos.y += npc.vel.y;
		}
		if( h < 80){
			npc.shoot();
		}

		return false;
	}

}
