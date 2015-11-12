package Behaviors;

import Entities.*;

public abstract class Behavior {

	protected Enemy npc;

	public Behavior(Enemy npc) {
		this.npc = npc;
	}

	public abstract boolean tick();

}
