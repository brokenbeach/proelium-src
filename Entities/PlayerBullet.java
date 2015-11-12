package Entities;

import StateManager.LevelState;

public class PlayerBullet extends Bullet{

	public PlayerBullet(LevelState ls, double x, double y, double dx,
			double dy, double ddx, double ddy, double rotation, int life, boolean slows) {
		super(ls, x, y, dx, dy, ddx, ddy, rotation, "playerbullet", life, slows);
		// TODO Auto-generated constructor stub
	}

}
