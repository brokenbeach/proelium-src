package Entities;

import Behaviors.SamuraiBehavior;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class SamuraiEnemy extends Enemy {
	public SamuraiEnemy(LevelState ls, double x, double y) {
		super(ls, x, y);
		image = SpriteStore.get().getImage("samurai");
		hitImage = SpriteStore.get().getImage("samuraihit");

		behavior = new SamuraiBehavior(this);

		setHealth(new Vector2D(6, 6));
	}
}
