package Behaviors;

import Entities.Enemy;
import Entities.Sword;
import Fundamentals.Vector2D;
import Main.GamePanel;

public class SamuraiBehavior extends Behavior {
	private long targetStartTime;
	private long targetDelay = 50;
	private Vector2D targetLoc;
	
	private long attackStartTime;
	private long attackDelay = 50;

	public SamuraiBehavior(Enemy npc) {
		super(npc);
		npc.setWeapon(new Sword(npc.ls, npc));
	}

	@Override
	public boolean tick() {
		findNewTargetLocation();

		npc.vel.x = (targetLoc.x - npc.pos.x) / 30;
		npc.vel.y = (targetLoc.y - npc.pos.y) / 30;
		npc.pos.x += npc.vel.x;
		npc.pos.y += npc.vel.y;
		double a = npc.pos.x - npc.ls.player.pos.x;
		double o = npc.pos.y - npc.ls.player.pos.y;
		double h = Math.sqrt(a * a + o * o);
		if (h < 60) {
			attack();
		}
		if( h < 20 ) {
			npc.shoot();
		}
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
			targetDelay = (int) (2000 + (Math.random() * 1000));
		}
		return true;
	}
	
	protected void attack() {
		long elapsed = (System.nanoTime() - attackStartTime) / 1000000;
		if (elapsed > attackDelay) {
			double a = npc.pos.x - npc.ls.player.pos.x;
			double o = npc.pos.y - npc.ls.player.pos.y;
			double x = npc.ls.player.pos.x - 5*a;
			double y = npc.ls.player.pos.y - 5*o;
			if( y < 205 ) {
				y = 205;
			}
			if( y > GamePanel.HEIGHT - 40 ) {
				y = GamePanel.HEIGHT - 40;
			}
			if( x < 20 ) {
				x = 20;
			}
			if( x > GamePanel.WIDTH - 36 ) {
				x = GamePanel.WIDTH - 36;
			}
			targetLoc = new Vector2D(x, y);
			attackStartTime = System.nanoTime();
			attackDelay = (int) (3000 + (Math.random() * 1000));
			attackDelay = (int) (2000 + (Math.random() * 1000));
		}
	}
}
