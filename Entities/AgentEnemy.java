package Entities;

import Behaviors.MachineGunBehavior;
import Behaviors.PistolBehavior;
import Behaviors.ShotgunBehavior;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class AgentEnemy extends Enemy {

	public AgentEnemy(LevelState ls, double x, double y) {
		super(ls, x, y);
		image = SpriteStore.get().getImage("agent");
		hitImage = SpriteStore.get().getImage("agenthit");

		int rand = (int) (Math.random() * 5);
		switch (rand) {
		case 0:
		case 1:
		case 2:
			behavior = new PistolBehavior(this);
			break;
		case 3:
			behavior = new ShotgunBehavior(this);
			break;
		case 4:
			behavior = new MachineGunBehavior(this);
			break;
		}

		setHealth(new Vector2D(3, 3));
	}

}
