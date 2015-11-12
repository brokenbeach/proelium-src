package StateManager;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import Fundamentals.Entity;
import Main.GamePanel;
import ResourceStores.SpriteStore;

public class Screen extends Entity {

	public Screen(LevelState ls, int i) {
		super(ls, (GamePanel.WIDTH - 192) / 2, 154 - (8 * i), 0, 0.2, 0, 0);
		this.image = SpriteStore.get().getImage("screensmall");
	}

	@Override
	public boolean tick() {
		pos.y -= vel.y;
		if (pos.y < 30 - 8) {
			pos.y = 158;
		}
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				0.3f));
		g.drawImage(image, (int) pos.x, (int) pos.y, null);
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}

}
