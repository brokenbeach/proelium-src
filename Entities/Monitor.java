package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Fundamentals.Entity;
import Main.GamePanel;
import ResourceStores.SpriteStore;
import StateManager.LevelState;

public class Monitor extends Entity {

	int width = 192;
	int height = 132;
	int x = (GamePanel.WIDTH - width) / 2;
	int y = 30;

	// tv stuff
	private int channel = 0;
	private static final int numberOfChannels = 4;
	public static final int LIVE = 0;
	public static final int HUD = 1;
	public static final int WAVE = 2;
	public static final int CREDITS = 3;
	public static final int GAMEOVER = 4;
	public static final int TITLE = 5;

	BufferedImage hud = SpriteStore.get().getImage("hud");
	BufferedImage skull = SpriteStore.get().getImage("bigskull");
	BufferedImage tree = SpriteStore.get().getImage("tree");
	BufferedImage bb = SpriteStore.get().getImage("bb");
	BufferedImage gameover = SpriteStore.get().getImage("gameover");
	BufferedImage title = SpriteStore.get().getImage("title");
	
	private long channelStartTime;
	private long channelDelay = 7000;

	public Monitor(LevelState ls) {
		super(ls, 0, 0, 0, 0, 0, 0);
		channelStartTime = System.nanoTime();
	}

	@Override
	public boolean tick() {
		long elapsed0 = (System.nanoTime() - channelStartTime) / 1000000;
		if (elapsed0 > channelDelay) {
			setChannel(channel + 1);
			if(channel >= numberOfChannels) {
				channel = 0;
			}
			channelStartTime = System.nanoTime();
		}
		return false;
	}

	@Override
	public void render(Graphics2D g) {
		switch (channel) {
		case 0:
			LIVE(g);
			break;
		case 1:
			HUD(g);
			break;
		case 2:
			WAVE(g);
			break;
		case 3:
			CREDITS(g);
			break;
		case 4:
			GAMEOVER(g);
			break;
		case 5:
			TITLE(g);
			break;
		}
	}

	public void LIVE(Graphics2D g) {
		BufferedImage screen = ls.sm.game.getImage().getSubimage(0, 171,
				GamePanel.WIDTH, 389);
		g.drawImage(screen, x, y, width, height, null);
	}

	public void HUD(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);
		g.drawImage(hud, x + 14, y + 13, null);

		// health
		double width = 125 * (ls.player.health.x / ls.player.health.y);
		g.setColor(new Color(255, 204, 204).darker().darker());
		g.fillRect(x + 55, y + 24, 125, 10);
		g.setColor(new Color(255, 204, 204).darker());
		g.fillRect(x + 55, y + 24, (int) width, 10);

		width = 125 * (ls.player.ammo.x / ls.player.ammo.y);
		g.setColor(new Color(204, 255, 204).darker().darker());
		g.fillRect(x + 55, y + 58, 125, 10);
		g.setColor(new Color(204, 255, 204).darker());
		g.fillRect(x + 55, y + 58, (int) width, 10);

		g.setColor(new Color(255, 255, 204).darker().darker());
		g.fillRect(x + 55, y + 94, 125, 10);
		g.setColor(new Color(204, 255, 204).darker());
	}
	
	public void WAVE(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);
		g.drawImage(skull, (GamePanel.WIDTH - skull.getWidth()) / 2, y + 20, null);
		if( ls.getNoEnemies() > 0 ) {
			double ratio = ((double )ls.enemies.size()) / ls.getNoEnemies();
			double width = 125 * ratio;
			g.setColor(new Color(204, 255, 204).darker().darker());
			g.fillRect(x + 35, y + 94, 125, 10);
			g.setColor(new Color(204, 255, 204));
			g.fillRect(x + 35, y + 94, (int) width, 10);
		}
	}
	
	public void CREDITS(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);
		g.drawImage(tree, (GamePanel.WIDTH - skull.getWidth()) / 2, y + 20, null);
		g.drawImage(bb, (GamePanel.WIDTH - bb.getWidth()) / 2, y + 80, null);
	}
	
	public void GAMEOVER(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);
		g.drawImage(gameover, (GamePanel.WIDTH - gameover.getWidth()) / 2, y + 30, null);
	}
	
	public void TITLE(Graphics2D g) {
		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);
		g.drawImage(title, (GamePanel.WIDTH - title.getWidth()) / 2, y + 20, null);
	}

	@Override
	public boolean hit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setChannel(int channel) {
		this.channel = channel;
		channelStartTime = System.nanoTime();	
	}

}
