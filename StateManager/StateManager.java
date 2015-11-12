package StateManager;
import java.awt.*;
import java.awt.event.MouseEvent;
import Fundamentals.Background;
import Main.GamePanel;

public class StateManager  {
	
	public GamePanel game;
	
	private State[] states;
	private int currentState;
	
	public static final int NUMstates = 1;
	public static final int LEVEL_STATE = 0;
	
	public StateManager(GamePanel game) {
		this.game = game;
		states = new State[NUMstates];
		currentState = LEVEL_STATE;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if( state == LEVEL_STATE ) {
			states[state] = new LevelState(this);	
		}
	}
	
	private void unloadState( int state ) {
		states[state] = null;
	}
	
	public void setState(int state){
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		
		states[currentState].init();
	}
	
	// Update the background, as well as the current gamestate.
	public void tick() {
		try {
			states[currentState].tick();
		}catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	// render the background images, and the current gamestate
	public void render(java.awt.Graphics2D g) {
		try {
			states[currentState].render(g);
		}catch( Exception e ) {
			// Draw black while loading
			g.setColor(new Color( 5, 5, 5));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			e.printStackTrace();
		}
	}
	
	public void keyPressed(int k) {
		states[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		states[currentState].keyReleased(k);
	}

	public void mousePressed(MouseEvent m) {
		states[currentState].mousePressed(m);
	}

	public void mouseReleased(MouseEvent m) {
		states[currentState].mouseReleased(m);
	}

	public void mouseDragged(MouseEvent m) {
		states[currentState].mouseDragged(m);
	}

	public void mouseMoved(MouseEvent m) {
		states[currentState].mouseMoved(m);
	}
}