package Entities;

import StateManager.LevelState;

public class Flame extends Bullet{

	public Flame(LevelState ls, double x, double y, double dx, double dy,
			double ddx, double ddy, double rotation, int life) {
		super(ls, x, y, dx, dy, ddx, ddy, rotation, "flame", life, true);
		// TODO Auto-generated constructor stub
	}

}
