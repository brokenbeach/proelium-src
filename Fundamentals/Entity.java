package Fundamentals;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import StateManager.LevelState;

public abstract class Entity  {
	
	public LevelState ls;
	public BufferedImage image;
	 
	public Vector2D pos;
	public Vector2D vel;
	public Vector2D acc;
	public Vector2D dec;
	public Vector2D max; // max speed for moving & falling
	public Vector2D center;
	private double rotation; // angle
	protected double theta;	 // change in angle per tick
	public Double hitbox;
	
	public Entity(LevelState ls, double x, double y, double dx, double dy, double ddx, double ddy) {
		this.pos = new Vector2D(x, y);
		this.vel = new Vector2D(dx, dy);
		this.acc = new Vector2D(ddx, ddy);
		this.dec = new Vector2D(0, 0);
		hitbox = new Rectangle2D.Double( pos.x, pos.y, 16, 16);
		this.ls = ls;
	}
	
	 protected void updateHitbox() {
		hitbox.setFrame( pos.x, pos.y, image.getWidth(), image.getHeight());
	}

	public abstract boolean tick();
	public abstract void render( Graphics2D g );
	public abstract boolean hit();
	
	
	public BufferedImage getImage() { return this.image; }

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public boolean hit(Vector2D bulletvel) {
		// TODO Auto-generated method stub
		return false;
	}
}