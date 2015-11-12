package Behaviors;

import java.awt.Color;

import Entities.Enemy;
import Fundamentals.FlameParticle;
import Fundamentals.Particle;
import Fundamentals.Vector2D;
import Main.GamePanel;

public class FireBehavior extends Behavior{

	private long targetStartTime;
	private long targetDelay = 1000;
	private Vector2D targetLoc;
	
	public FireBehavior(Enemy npc) {
		super(npc);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean tick() {
		npc.getHealth().x -= 0.01;
		findNewTargetLocation();
		double dx = 2 * (-0.5 + Math.random());
		double dy = 2 * (-(Math.random()));
		int size = (int) (Math.random() * 12) + 5;
		npc.ls.particles.add(new FlameParticle(npc.pos.x + 4, npc.pos.y + 6, dx, dy, 0, 0, 25, 40, Color.ORANGE, size));
		npc.ls.particles.add(new FlameParticle(npc.pos.x + 4, npc.pos.y + 6, dx, dy, 0, 0, 25, 40, Color.WHITE, size * 0.4));

		npc.vel.x = (targetLoc.x - npc.pos.x) / 80;
		npc.vel.y = (targetLoc.y - npc.pos.y) / 80;
		npc.pos.x += npc.vel.x;
		npc.pos.y += npc.vel.y;
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
			targetDelay = (int) (1000 + (Math.random() * 2000));
		}
		return true;
	}

}
