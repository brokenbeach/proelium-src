package Entities;

import Behaviors.MachineGunBehavior;
import Behaviors.PistolBehavior;
import Behaviors.ShotgunBehavior;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class MercEnemy extends Enemy{

	public MercEnemy(LevelState ls, double x, double y) {
		super(ls, x, y);
		image = SpriteStore.get().getImage("merc");
		hitImage = SpriteStore.get().getImage("merchit");

		int rand = (int) (Math.random() * 4);
		switch (rand) {
		case 0:
		case 1:
			behavior = new PistolBehavior(this);
			break;
		case 2:
			behavior = new ShotgunBehavior(this);
			break;
		case 3:
			behavior = new MachineGunBehavior(this);
			break;
		}

		setHealth(new Vector2D(5, 5));
	}

}
