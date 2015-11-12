package ResourceStores;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteStore {
	
	//Singleton
	private static SpriteStore store = new SpriteStore();
	//Map from reference to sprite
	private HashMap<String, BufferedImage> reference = new HashMap<String, BufferedImage>();

	SpriteStore() {	
		try {	
			// characters
			reference.put("player0", ImageIO.read( getClass().getResource("/res/img/player0.png")) );
			reference.put("playerhit0", ImageIO.read( getClass().getResource("/res/img/player0hit.png")) );
			reference.put("player1", ImageIO.read( getClass().getResource("/res/img/player1.png")) );
			reference.put("playerhit1", ImageIO.read( getClass().getResource("/res/img/player1hit.png")) );
			reference.put("player2", ImageIO.read( getClass().getResource("/res/img/player2.png")) );
			reference.put("playerhit2", ImageIO.read( getClass().getResource("/res/img/player2hit.png")) );
			reference.put("player3", ImageIO.read( getClass().getResource("/res/img/player3.png")) );
			reference.put("playerhit3", ImageIO.read( getClass().getResource("/res/img/player3hit.png")) );
			reference.put("player4", ImageIO.read( getClass().getResource("/res/img/player4.png")) );
			reference.put("player5", ImageIO.read( getClass().getResource("/res/img/player5.png")) );
			reference.put("player6", ImageIO.read( getClass().getResource("/res/img/player6.png")) );
			reference.put("player7", ImageIO.read( getClass().getResource("/res/img/player7.png")) );
			reference.put("player8", ImageIO.read( getClass().getResource("/res/img/player8.png")) );
			reference.put("player9", ImageIO.read( getClass().getResource("/res/img/player9.png")) );
			reference.put("player10", ImageIO.read( getClass().getResource("/res/img/player10.png")) );
			reference.put("player11", ImageIO.read( getClass().getResource("/res/img/player11.png")) );
			reference.put("player12", ImageIO.read( getClass().getResource("/res/img/player12.png")) );
			reference.put("player13", ImageIO.read( getClass().getResource("/res/img/player13.png")) );
			reference.put("player14", ImageIO.read( getClass().getResource("/res/img/player14.png")) );
			reference.put("player15", ImageIO.read( getClass().getResource("/res/img/player15.png")) );
			
			reference.put("playerhit3", ImageIO.read( getClass().getResource("/res/img/player3hit.png")) );
			reference.put("agent", ImageIO.read( getClass().getResource("/res/img/agent.png")) );
			reference.put("agenthit", ImageIO.read( getClass().getResource("/res/img/agenthit.png")) );
			reference.put("zombie", ImageIO.read( getClass().getResource("/res/img/zombie.png")) );
			reference.put("zombiehit", ImageIO.read( getClass().getResource("/res/img/zombiehit.png")) );
			reference.put("merc", ImageIO.read( getClass().getResource("/res/img/merc.png")) );
			reference.put("merchit", ImageIO.read( getClass().getResource("/res/img/merchit.png")) );
			reference.put("samurai", ImageIO.read( getClass().getResource("/res/img/samurai.png")) );
			reference.put("samuraihit", ImageIO.read( getClass().getResource("/res/img/samuraihit.png")) );
			
			// weapons
			reference.put("pistol", ImageIO.read( getClass().getResource("/res/img/pistol.png")) );
			reference.put("hitpistol", ImageIO.read( getClass().getResource("/res/img/hitpistol.png")) );
			reference.put("shotgun", ImageIO.read( getClass().getResource("/res/img/shotgun.png")) );
			reference.put("shotgunhit", ImageIO.read( getClass().getResource("/res/img/shotgunhit.png")) );
			reference.put("machinegun", ImageIO.read( getClass().getResource("/res/img/machinegun.png")) );
			reference.put("machinegunhit", ImageIO.read( getClass().getResource("/res/img/machinegunhit.png")) );
			reference.put("flamethrower", ImageIO.read( getClass().getResource("/res/img/flamethrower.png")) );
			reference.put("flamethrowerhit", ImageIO.read( getClass().getResource("/res/img/flamethrowerhit.png")) );
			reference.put("sword", ImageIO.read( getClass().getResource("/res/img/sword.png")) );
			reference.put("swordhit", ImageIO.read( getClass().getResource("/res/img/swordhit.png")) );
			reference.put("zombiehand", ImageIO.read( getClass().getResource("/res/img/zombiehands.png")) );
			reference.put("zombiehandhit", ImageIO.read( getClass().getResource("/res/img/zombiehandhit.png")) );
			
			// projectiles
			reference.put("playerbullet", ImageIO.read( getClass().getResource("/res/img/playerbullet.png")) );			
			reference.put("enemybullet", ImageIO.read( getClass().getResource("/res/img/enemybullet.png")) );		
			reference.put("flame", ImageIO.read( getClass().getResource("/res/img/flameball.png")) );		
			
			// bg
			reference.put("blocker", ImageIO.read( getClass().getResource("/res/img/blocker.png")) );		
			reference.put("screensmall", ImageIO.read( getClass().getResource("/res/img/screensmall.png")) );		
			reference.put("bg", ImageIO.read( getClass().getResource("/res/img/bg.png")) );		
			reference.put("ground", ImageIO.read( getClass().getResource("/res/img/ground.png")) );		
			reference.put("dirt", ImageIO.read( getClass().getResource("/res/img/dirt.png")) );		
			
			// items
			reference.put("health", ImageIO.read( getClass().getResource("/res/img/healthkit.png")) );		
			reference.put("weaponbox", ImageIO.read( getClass().getResource("/res/img/weaponbox.png")) );		
			reference.put("coin", ImageIO.read( getClass().getResource("/res/img/coin.png")) );		
			reference.put("shell0", ImageIO.read( getClass().getResource("/res/img/shell0.png")) );		
			reference.put("shell1", ImageIO.read( getClass().getResource("/res/img/shell1.png")) );		
			reference.put("gib0", ImageIO.read( getClass().getResource("/res/img/gib0.png")) );	
			reference.put("gib1", ImageIO.read( getClass().getResource("/res/img/gib1.png")) );	
			reference.put("gib2", ImageIO.read( getClass().getResource("/res/img/gib2.png")) );	

			// misc
			reference.put("button", ImageIO.read( getClass().getResource("/res/img/button.png")) );		
			reference.put("buttonpressed", ImageIO.read( getClass().getResource("/res/img/buttonpushed.png")) );	
			reference.put("hud", ImageIO.read( getClass().getResource("/res/img/hud.png")) );	
			reference.put("bigskull", ImageIO.read( getClass().getResource("/res/img/bigskull.png")) );	
			reference.put("tree", ImageIO.read( getClass().getResource("/res/img/tree.png")) );	
			reference.put("bb", ImageIO.read( getClass().getResource("/res/img/bb.png")) );	
			reference.put("gameover", ImageIO.read( getClass().getResource("/res/img/gameover.png")) );	
			reference.put("title", ImageIO.read( getClass().getResource("/res/img/title.png")) );	
		} 
		catch ( IOException e ) {
			System.err.println(e);
		}	
	}
	
	public static SpriteStore get()	{
		return store;
	}

	public BufferedImage getImage( String ref ) {
		return reference.get( ref );
	}	
}