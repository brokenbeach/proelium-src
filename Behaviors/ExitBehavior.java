package Behaviors;

import Entities.Enemy;
import Fundamentals.Vector2D;
import Main.GamePanel;

public class ExitBehavior extends Behavior {

	Vector2D target = new Vector2D((GamePanel.WIDTH - 5) / 2, 200);
	double offset;

	public ExitBehavior(Enemy npc) {
		super(npc);
		offset = 0.6 + Math.random();

		double xDiff = (target.x - npc.pos.x);
		double yDiff = (target.y - npc.pos.y);
		npc.vel.x = (xDiff / 80) * offset;
		npc.vel.y = (yDiff / 80) * offset;
	}

	@Override
	public boolean tick() {
		double a = npc.pos.x - target.x;
		double o = npc.pos.y - target.y;
		double h = Math.sqrt(a * a + o * o);

		if (npc.pos.x < (GamePanel.WIDTH - 5) / 2) {
			npc.setLeftImage(false);
		} else {
			npc.setLeftImage(true);
		}
		if (h > 5) {
			npc.pos.x += npc.vel.x;
			npc.pos.y += npc.vel.y;
		} else {
			return true;
		}
		return false;
	}

}
