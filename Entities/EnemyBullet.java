package Entities;

import StateManager.LevelState;

public class EnemyBullet extends Bullet {
	public EnemyBullet(LevelState ls, double x, double y, double dx,
			double dy, double ddx, double ddy, double rotation, int life, boolean slows) {
		super(ls, x, y, dx, dy, ddx, ddy, rotation, "enemybullet", life, slows);
		// TODO Auto-generated constructor stub
	}
}
