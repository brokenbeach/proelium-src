package StateManager;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entities.*;
import Fundamentals.Entity;
import Fundamentals.Particle;
import Main.GamePanel;
import ResourceStores.AudioPlayer;
import ResourceStores.SoundStore;
import ResourceStores.SpriteStore;

public class LevelState extends State {

	boolean started = false;
	private int waveNumber = 0;

	public static Point CURSOR = new Point(0, 0);

	public Player player;

	float damageAlpha = 0;
	float darkAlpha = 1.0f;

	public List<Particle> particles = new ArrayList<Particle>();
	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Screen> screen = new ArrayList<Screen>();
	public List<Bullet> playerBullets = new ArrayList<Bullet>();
	public List<Bullet> enemyBullets = new ArrayList<Bullet>();
	public List<Flame> playerFlame = new ArrayList<Flame>();
	public List<Entity> pickups = new ArrayList<Entity>();
	public List<Shell> shells = new ArrayList<Shell>();
	public List<Gib> gibs = new ArrayList<Gib>();
	public List<CrowdMember> crowd = new ArrayList<CrowdMember>();

	public Button button;

	BufferedImage bg = SpriteStore.get().getImage("bg");
	BufferedImage ground = SpriteStore.get().getImage("ground");
	BufferedImage dirt = SpriteStore.get().getImage("dirt");
	BufferedImage blocker = SpriteStore.get().getImage("blocker");

	Monitor monitor;

	private long waveStartTime;
	private long waveDelay = 3000;

	boolean ready = true;
	private long startTime;
	private long delay = 5000;

	private int noEnemies = 0;
	private boolean waveBreak = false;

	public AudioPlayer coin = SoundStore.get().getSound("coin");
	public AudioPlayer gameover = SoundStore.get().getSound("gameover");

	public LevelState(StateManager sm) {
		super(sm);
		monitor = new Monitor(this);
		monitor.setChannel(Monitor.TITLE);
		for (int i = 0; i < 17; i++) {
			screen.add(new Screen(this, i));
		}
		spawnPlayer();
		startTime = System.nanoTime();
		darkFlash(1.0f);
		
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 10; j++) {
				crowd.add(new CrowdMember(this, -4 + i * 17, -8 + j * 18, false));
			}
		}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 10; j++) {
				crowd.add(new CrowdMember(this, 370 + i * 17, -8 + j * 18, true));
			}
		}
	}

	public void spawnEnemy(int type) {
		double y = 210;
		double x = GamePanel.WIDTH / 2;
		int rand = (int) (Math.random() * 4);
		switch (rand) {
		case 0:
			x = 40;
			break;
		case 1:
			x = 124;
			break;
		case 2:
			x = 332;
			break;
		case 3:
			x = 422;
			break;
		}
		switch (type) {
		case 0:
			enemies.add(new AgentEnemy(this, x, y));
			break;
		case 1:
			enemies.add(new ZombieEnemy(this, x, y));
			break;
		case 2:
			enemies.add(new MercEnemy(this, x, y));
			break;
		case 3:
			enemies.add(new SamuraiEnemy(this, x, y));
			break;
		}
	}

	public void spawnWave(int agents, int zombies, int mercenaries, int samurai) {
		this.noEnemies = agents + zombies + mercenaries + samurai;
		for (int i = 0; i < agents; i++) {
			spawnEnemy(0);
		}

		for (int i = 0; i < zombies; i++) {
			spawnEnemy(1);
		}

		for (int i = 0; i < mercenaries; i++) {
			spawnEnemy(2);
		}

		for (int i = 0; i < samurai; i++) {
			spawnEnemy(3);
		}
	}

	public void waveSystem() {
		if (waveNumber < 12) {
			switch (waveNumber) {
			case 0:
				spawnWave(1, 0, 0, 0);
				break;
			case 1:
				spawnWave(2, 0, 0, 0);
				break;
			case 2:
				spawnWave(4, 0, 0, 0);
				break;
			case 3:
				spawnWave(0, 1, 0, 0);
				break;
			case 4:
				spawnWave(0, 4, 0, 0);
				break;
			case 5:
				spawnWave(0, 8, 0, 0);
				break;
			case 6:
				spawnWave(0, 0, 1, 0);
				break;
			case 7:
				spawnWave(0, 0, 2, 0);
				break;
			case 8:
				spawnWave(0, 0, 4, 0);
				break;
			case 9:
				spawnWave(0, 0, 0, 1);
				break;
			case 10:
				spawnWave(0, 0, 0, 2);
				break;
			case 11:
				spawnWave(0, 0, 0, 4);
				break;
			}
		} else {
			int agents = 0;
			int zombies = 0;
			int mercs = 0;
			int samurai = 0;
			for (int i = 0; i < waveNumber - 11; i++) {
				int rand = (int) (Math.random() * 4);
				switch (rand) {
				case 0:
					agents++;
					break;
				case 1:
					zombies++;
					break;
				case 2:
					mercs++;
					break;
				case 3:
					samurai++;
					break;
				}
			}
			spawnWave(agents, zombies, mercs, samurai);
		}
		waveNumber++;
	}

	public void start() {
		this.started = true;
	}

	public void spawnPlayer() {
		button = new Button(this, GamePanel.WIDTH / 2 - 10, 370);
		player = new Player(this);
		ready = true;
	}

	@Override
	public boolean tick() {
		if (started) {
			if (enemies.size() == 0 && waveBreak) {
				long elapsed = (System.nanoTime() - waveStartTime) / 1000000;
				if (elapsed > getWaveDelay()) {
					waveSystem();
					waveBreak = false;
				}
			}
		} else {
			if (!ready) {
				long elapsed = (System.nanoTime() - startTime) / 1000000;
				if (elapsed > delay) {
					spawnPlayer();
				}
			}
		}
		checkButtonPressed();
		button.tick();
		monitor.tick();
		tickList(screen);
		player.tick();
		tickList(crowd);
		tickList(gibs);
		tickList(shells);
		tickList(enemies);
		tickList(playerFlame);
		tickList(playerBullets);
		tickList(enemyBullets);
		tickList(pickups);
		tickParticles();
		checkPlayerBulletCollisions();
		checkPlayerFlameCollisions();
		if( ready ) {
			checkEnemyBulletCollisions();
		}
		checkDeadEnemies();
		checkPickupCollision();
		Collections.sort(enemies);
		if (!waveBreak && enemies.isEmpty()) {
			waveBreak = true;
			waveStartTime = System.nanoTime();
		}
		darkAlpha += (0 - darkAlpha) * 0.04;
		damageAlpha += (0 - damageAlpha) * 0.04;
		return false;
	}

	protected void tickList(List<? extends Entity> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).tick()) {
				list.remove(i);
				if (i > 0)
					i--;
			}
		}
	}

	public void tickParticles() {
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).tick()) {
				particles.remove(i);
				if (i > 0)
					i--;
			}
		}
	}

	public void checkDeadEnemies() {
		for (int j = 0; j < enemies.size(); j++) {
			Enemy enemy = enemies.get(j);
			if (enemy.getHealth().x <= 0) {
				enemies.remove(j);
				if (j > 0)
					j--;
			}
		}
	}

	private void checkPlayerBulletCollisions() {
		for (int i = 0; i < playerBullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if (playerBullets.isEmpty())
					return;
				if (enemies.isEmpty())
					return;
				Bullet bullet = playerBullets.get(i);
				Enemy enemy = enemies.get(j);

				if (bullet.hitbox.intersects(enemy.hitbox)) {
					bullet.hit();
					playerBullets.remove(i);
					if (i > 0) {
						i--;
					}
					if (enemy.hit(bullet.vel)) {
						enemies.remove(j);
						if (j > 0) {
							j--;
						}
					}
				}
			}
		}
	}

	private void checkPlayerFlameCollisions() {
		for (int i = 0; i < playerFlame.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				if (playerFlame.isEmpty())
					return;
				if (enemies.isEmpty())
					return;
				Flame flame = playerFlame.get(i);
				Enemy enemy = enemies.get(j);

				if (flame.hitbox.intersects(enemy.hitbox)) {
					flame.hit();
					if (enemy.ignite(flame.vel)) {
						enemies.remove(j);
						if (j > 0) {
							j--;
						}
					}
				}
			}
		}
	}

	private void checkEnemyBulletCollisions() {
		for (int i = 0; i < enemyBullets.size(); i++) {
			Bullet bullet = enemyBullets.get(i);
			if (bullet.hitbox.intersects(player.hitbox)) {
				bullet.hit();
				enemyBullets.remove(i);
				if (i > 0)
					i--;
				if (player.hit(bullet.vel)) {
					gameOver();
				}
			}
		}
	}

	private void checkPickupCollision() {
		for (int i = 0; i < pickups.size(); i++) {
			if (pickups.get(i).hitbox.intersects(player.hitbox)) {
				pickups.get(i).hit();
				pickups.remove(i);
				if (i > 0)
					i--;
			}
		}
	}

	public void gameOver() {
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).exit();
		}
		monitor.setChannel(Monitor.GAMEOVER);
		sm.game.shake(20);
		button.release();
		started = false;
		ready = false;
		if (waveNumber > 12) {
			waveNumber = 12;
		}
		startTime = System.nanoTime();
	}

	public void checkButtonPressed() {
		if (!button.isPressed()) {
			if (player.hitbox.intersects(button.hitbox)) {
				button.press();
				start();
			}
		}
	}

	public void coin() {
		coin.play();
	}

	// .............................................

	@Override
	public void render(Graphics2D g) {
		g.drawImage(dirt, 40, 250, null);
		g.drawImage(ground, 15, 224, null);
		renderList(crowd, g);
		g.drawImage(bg, 0, 0, null);
		button.render(g);
		renderList(shells, g);
		renderList(gibs, g);
		renderList(playerBullets, g);
		renderList(enemyBullets, g);;
		renderList(playerFlame, g);
		renderList(pickups, g);
		renderList(enemies, g);

		if (ready) {
			player.render(g);
		}

		renderParticles(g);

		drawBigMonitor(g);
		renderList(screen, g);
		g.drawImage(blocker, (GamePanel.WIDTH - blocker.getWidth()) / 2, 22,
				null);
		g.drawImage(blocker, (GamePanel.WIDTH - blocker.getWidth()) / 2, 154,
				null);

		g.setColor(new Color(40, 40, 40));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				darkAlpha));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		g.setColor(new Color(255, 100, 100));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				damageAlpha));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setComposite(AlphaComposite
				.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	protected void drawBigMonitor(Graphics2D g) {
		monitor.render(g);
	}

	protected void renderList(List<? extends Entity> list, Graphics2D g) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).render(g);
		}
	}

	protected void renderParticles(Graphics2D g) {
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
	}

	// ....................................................................................................................................
	// ....................................................................................................................................
	// ....................................................................................................................................

	@Override
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_W) {
			player.setUp(true);
		}
		if (k == KeyEvent.VK_S) {
			player.setDown(true);
		}
		if (k == KeyEvent.VK_A) {
			player.setLeft(true);
		}
		if (k == KeyEvent.VK_D) {
			player.setRight(true);
		}

		if (k == KeyEvent.VK_1) {
			monitor.setChannel(0);
		}
		if (k == KeyEvent.VK_2) {
			monitor.setChannel(1);
		}
		if (k == KeyEvent.VK_3) {
			monitor.setChannel(2);
		}
		if (k == KeyEvent.VK_4) {
			monitor.setChannel(3);
		}
		// FOR DEV TESTING ONLY
//		if (k == KeyEvent.VK_Q) {
//			spawnEnemy(0);
//		}
//		if (k == KeyEvent.VK_E) {
//			spawnEnemy(1);
//		}
//		if (k == KeyEvent.VK_R) {
//			spawnEnemy(2);
//		}
//		if (k == KeyEvent.VK_F) {
//			spawnEnemy(3);
//		}
	}

	@Override
	public void keyReleased(int k) {
		if (k == KeyEvent.VK_W) {
			player.setUp(false);
		}
		if (k == KeyEvent.VK_S) {
			player.setDown(false);
		}
		if (k == KeyEvent.VK_A) {
			player.setLeft(false);
		}
		if (k == KeyEvent.VK_D) {
			player.setRight(false);
		}
	}

	@Override
	public void mousePressed(MouseEvent m) {
		if (ready) {
			player.shoot();
			player.setFiring(true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		player.setFiring(false);
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		if (ready) {
			CURSOR = m.getPoint();

			if (m.getPoint().x < player.pos.x) {
				player.setLeftImage(true);
			} else {
				player.setLeftImage(false);
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent m) {
		if (ready) {
			CURSOR = m.getPoint();
			if (m.getPoint().x < player.pos.x) {
				player.setLeftImage(true);
			} else {
				player.setLeftImage(false);
			}
		}
	}

	public void damageFlash(float f) {
		this.damageAlpha = f;
	}

	public void darkFlash(float f) {
		this.darkAlpha = f;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	public long getWaveDelay() {
		return waveDelay;
	}

	public void setWaveDelay(long waveDelay) {
		this.waveDelay = waveDelay;
	}

	public int getNoEnemies() {
		return noEnemies;
	}

	public void setNoEnemies(int noEnemies) {
		this.noEnemies = noEnemies;
	}

}
