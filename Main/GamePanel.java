package Main;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.JPanel;
import StateManager.StateManager;


public class GamePanel extends JPanel implements Runnable {
	
	//Dimension variables
	public static final int WIDTH = 480;
	public static final int HEIGHT = 560;
	public static boolean MOUSE_PRESSED;
	
	public StateManager sm;
	private double shake = 0;
	private double shakeTick = 0;
		
	//Thread related objects
	private int FPS = 60;
	private Thread thread;
	
	private boolean running;
		
	//Graphics objects
	private BufferedImage image;
	private Graphics2D g;
	private Color bgColour = new Color( 35,35,35 );
	
	public BufferedImage imageCopy;
	
	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(true);
		requestFocus();
		
		sm = new StateManager(this);
	}
	
	//Occurs once the panel is finished loading
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}	
		this.addMouseListener( new MyMouseListener() );
		this.addMouseMotionListener( new MyMouseListener() );
		this.addKeyListener(new WASDListener());
	}

	public void run() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) getImage().getGraphics();
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = 1000 / FPS;
		
		//Game Loop
		while(running) {
			startTime = System.nanoTime();
			tick();
			render();
			draw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try
			{
				Thread.sleep(waitTime);
			}catch(Exception e){}
			
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			
			if( frameCount == maxFrameCount ) {
//				averageFPS = 1000.0 / ( ( totalTime / frameCount ) / 1000000 );
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
//.....................................................................................................................................
//                           TICK
//.....................................................................................................................................
	
	//Calculates all the necessary information
	private void tick() {
		sm.tick();
	}
	
//.....................................................................................................................................
//                                 RENDER
//.....................................................................................................................................
	
	//Draws to the buffer
	private void render() {
		g.setColor(bgColour);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		sm.render(g);
		
		// FPS counter
//		g.setColor(Color.WHITE);
//		g.setFont(new Font("Calibiri", Font.PLAIN, 12));
//		g.drawString("FPS : " + (int) (averageFPS), 10, 20);
	}
	
//.....................................................................................................................................
// 									 DRAW
//.....................................................................................................................................
	
	public void shake(double shake) {
		this.shake = shake;
		shakeTick = 2.0;
	}
	
	//Draws the buffered image to the screen
	private void draw() {
		Graphics g2 = this.getGraphics();
		
		// shake
		if( shakeTick > 0 ) {
			shakeTick -= 0.05;
			double randX = (shake * (-0.5 + Math.random())) * shakeTick;
			double randY = (shake * (-0.5 + Math.random())) * shakeTick;
			g2.translate((int) randX, (int) randY);
		}
		
		g2.drawImage(getImage(), 0, 0, null);
		g2.dispose();
	}

//........................................................................................................................................................................................................

	public BufferedImage getImage() {
		return image;
	}

	private class MyMouseListener implements MouseListener, MouseMotionListener {
		//...... Mouse Listener ......
		
		public void mouseClicked(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {
			sm.mousePressed( e );
		}

		public void mouseReleased(MouseEvent e) {
			sm.mouseReleased( e );
		}

		//...... Mouse Motion Listener ......

		public void mouseDragged(MouseEvent e) {
			sm.mouseDragged( e );
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			sm.mouseMoved( e );
		}
	}
	
	private class WASDListener implements KeyListener {
	
		public void keyPressed(KeyEvent k) {
			sm.keyPressed(k.getKeyCode());
		}
	
		public void keyReleased(KeyEvent k) {
			sm.keyReleased(k.getKeyCode());
		}
	
		public void keyTyped(KeyEvent arg0) {}	
	}
}
