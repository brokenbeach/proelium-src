package Entities;

import Behaviors.ZombieBehavior;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class ZombieEnemy extends Enemy {

	public ZombieEnemy(LevelState ls, double x, double y) {
		super(ls, x, y);
		image = SpriteStore.get().getImage("zombie");
		hitImage = SpriteStore.get().getImage("zombiehit");
		behavior = new ZombieBehavior(this);
		setHealth(new Vector2D(2, 2));
	}

}
