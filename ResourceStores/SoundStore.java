package ResourceStores;
import java.util.HashMap;

public class SoundStore {

	//Singleton
	private static SoundStore store = new SoundStore();
	//Map from reference to sprite
	private HashMap<String, AudioPlayer> reference = new HashMap<String, AudioPlayer>();

	SoundStore() {	
		// tilesets
		reference.put("pistol", new AudioPlayer("/res/sfx/pistol.mp3"));	
		reference.put("shotgun", new AudioPlayer("/res/sfx/shotgun.mp3"));		
		reference.put("machinegun", new AudioPlayer("/res/sfx/machinegun.mp3"));	
		reference.put("machinegun", new AudioPlayer("/res/sfx/machinegun.mp3"));	
		reference.put("flamethrower", new AudioPlayer("/res/sfx/flame.mp3"));

		reference.put("coin", new AudioPlayer("/res/sfx/coin.mp3"));
		reference.put("gameover", new AudioPlayer("/res/sfx/gameover.mp3"));
		reference.put("hurt", new AudioPlayer("/res/sfx/hurt.mp3"));
		reference.put("hit", new AudioPlayer("/res/sfx/hitwall.mp3"));
	}
	
	public static SoundStore get()	{
		return store;
	}

	public AudioPlayer getSound( String ref ) {
		return reference.get( ref );
	}	
}
