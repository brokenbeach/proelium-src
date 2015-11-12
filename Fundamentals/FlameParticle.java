package Fundamentals;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class FlameParticle extends Particle {

	public FlameParticle(double x, double y, double dx, double dy, double ddx,
			double ddy, double life, double maxLife, Color colour, double size) {
		super(x, y, dx, dy, ddx, ddy, life, maxLife, colour, size);
		// TODO Auto-generated constructor stub
	}

	public boolean tick() {
		boolean returnVal = super.tick();
		double diff = (life.y - life.x) * sector;
		alpha = (float) diff;

		return returnVal;
	}

}
