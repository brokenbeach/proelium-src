package Entities;

import java.awt.Graphics2D;

import Fundamentals.Entity;
import Fundamentals.Vector2D;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class CrowdMember extends Entity {
	
	boolean left;
	boolean xUp;
	boolean yUp;
	double xWave;
	double yWave;
	double xOffset;
	double yOffset;
	Vector2D xlimit;
	Vector2D ylimit;

	public CrowdMember(LevelState ls, double x, double y, boolean left) {
		super(ls, x, y, 0, 0, 0, 0);
		int rand = (int) (Math.random() * 15);
		this.image = SpriteStore.get().getImage("player".concat(String.valueOf(rand)));
		this.left = left;
		xWave = (-0.5 + Math.random());
		yWave = (-0.5 + Math.random());
		xOffset = 0.05 * Math.random();
		yOffset = 0.05 * Math.random();
		
		xlimit = new Vector2D( x - 2, x + 2 );
		ylimit = new Vector2D( y - 4, y + 4 );
	}

	@Override
	public boolean tick() {
		xWave();
		yWave();
		pos.x += vel.x;
		pos.y += vel.y;
		
		
		if( pos.x < xlimit.x ) pos.x = xlimit.x;
		if( pos.x > xlimit.y ) pos.x = xlimit.y;
		if( pos.y < ylimit.x ) pos.y = ylimit.x;
		if( pos.y > ylimit.y ) pos.y = ylimit.y;
		
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		int translate = (left) ? 1 : 0;
		int transform = (left) ? -1 : 1;
		g.drawImage(image, (int) pos.x + translate * image.getWidth(),
				(int) pos.y, transform * image.getWidth(),
				image.getHeight(), null);
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void xWave() {
		if(xUp){
			if( xWave < 1 ) {
				xWave += 0.1+xOffset;
			}else {
				xUp = false;
			}
		}else {
			if( xWave > -1) {
				xWave -= 0.01+xOffset;
			}else {
				xUp = true;
			}
		}
		vel.x = Math.sin(xWave) / 4;
	}
	
	protected void yWave() {
		if(yUp) {
			if( yWave < 1 ) {
				yWave += 0.05 + yOffset;
			}else {
				yUp = false;
			}
		}else {
			if( yWave > -1 ) {
				yWave -= 0.05 + yOffset;
			}else {
				yUp = true;
			}
		}
		vel.y = Math.sin(yWave) / 4;
	}

}
