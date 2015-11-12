package Entities;

import Behaviors.*;
import Fundamentals.Vector2D;
import StateManager.LevelState;

public class Enemy extends Humanoid implements Comparable<Enemy>{

	Behavior behavior;
	Behavior defaultBehavior;

	public Enemy(LevelState ls, double x, double y) {
		super(ls, x, y, 0, 0, 0, 0, false);
	}

	public boolean ignite(Vector2D vel) {
		getHealth().x -= 0.01;
		behavior = new FireBehavior(this);
		return false;
	}
	
	public boolean tick() {
		super.tick();
		return behavior.tick();
	}

	@Override
	public int compareTo(Enemy other) {
		if( other.pos.y > pos.y) {
			return -1;
		}else if( other.pos.y < pos.y){
			return 1;
		}
		return 0;
	}
	
	public void exit() {
		this.behavior = new ExitBehavior(this);
	}

}
