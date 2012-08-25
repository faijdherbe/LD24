import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;


public class LifeForm {
	public final static int STATE_INIT = 0;
	public final static int STATE_ALIVE = 1;
	public final static int STATE_DYING = 2;
	
	public final static int STATE_DEAD = 99;
	
	private int state = STATE_INIT;
	
	Vector2f origin = new Vector2f(100,100);
	protected ESprite lfImage;
	protected ESprite lfWalkImage;
	
	public Vector2f inputForce = new Vector2f(0,0);
	public Vector2f velocity = new Vector2f(0,0);
	
	public Rectangle playArea = new Rectangle(40, 70, 720, 510); 
	
	private int timeSinceLastUpdate = 0;
	
	protected Sound dieSound = null;
	
	protected Color color = Color.white;
	
	int tailLevel = 1, tailLevelMax = 10;
	int speedLevel = 3, speedLevelMax = 10;
	int agilityLevel = 1, agilityLevelMax = 10;
	
	public float getRadius() {
		return 8 * (1 + (speedLevel/(float)speedLevelMax));
	}

	public void addSpeedLevel(int a) {
		speedLevel = Math.max(1, Math.min(speedLevelMax, speedLevel+a));
		
	}

	public void addAgilityLevel(int a) {
		agilityLevel = Math.max(1, Math.min(agilityLevelMax, agilityLevel+a));
		
	}

	public void addTailLevel(int a) {
		tailLevel = Math.max(1, Math.min(tailLevelMax, tailLevel+a));
	}
	
	public LifeForm() {
		if(null == lfImage) {
			try {
				lfImage = new ESprite("res/LifeForm.png",24,24);
				lfWalkImage = new ESprite("res/LifeForm.png", 24, 24);
				
				lfImage.addAnimation("idle", 1, new int[] {0}, true);
				lfImage.addAnimation("die", 12, new int[] {1,2,3}, true);
				lfWalkImage.addAnimation("walk", 12, new int[] {4,5,6,7}, true);
				
				lfImage.setCenterOfRotation(12,12);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			dieSound = new Sound("res/playerDie.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		origin = new Vector2f(100, 10);
	}
	
	public boolean hitTest(LifeForm e) {
		Vector2f d = new Vector2f(Math.abs(this.origin.x - e.origin.x), 
				Math.abs(this.origin.y - e.origin.y));
		
		return  Math.sqrt(d.x*d.x + d.y*d.y) < (e.getRadius() + this.getRadius());  
	}
	
	public void update(int delta) {
		lfImage.update(delta);
		lfWalkImage.update(delta);
		
		
		if(state == STATE_DYING) {
			timeSinceLastUpdate += delta;
			if(timeSinceLastUpdate > 1000) {
				state = STATE_DEAD;
			}
			
			return;
		}
		
		if(origin.x < playArea.getX()) {
			inputForce.x += (playArea.getX() - origin.x) * 0.25;
		} else if(origin.x > playArea.getMaxX()) {
			inputForce.x -= (origin.x - playArea.getMaxX()) * 0.25;
		}
		if(origin.y < playArea.getY()) {
			inputForce.y += (playArea.getY() - origin.y) * 0.25;
		} else if(origin.y > playArea.getMaxY()) {
			inputForce.y -= (origin.y - playArea.getMaxY()) * 0.25;
		}
		
		
		velocity.x *= 0.99;
		velocity.y *= 0.99;
		
		velocity.x += inputForce.x * 0.1f;
		velocity.y += inputForce.y * 0.1f;

		
		Vector2f v = velocity.copy();
		v.scale(speedLevel/(float)speedLevelMax);
		
		
		origin.x += v.x;
		origin.y += v.y;
		
		
	}
	
	public void render(Graphics g) {
		Image img = lfImage.getImage();
		Image walkImg = lfWalkImage.getImage();

		float d = 1 + (speedLevel/(float)speedLevelMax);
		
		float x = origin.x - (12 * d);
		float y = origin.y - (12 * d);
		
//		float ang = (float)Math.atan2(velocity.x, velocity.y);
		//walkImg.setRotation(180*ang);
		//img.setRotation(180*ang);
		
		g.drawImage(img, x, y, x + (24*d), y + (24*d), 0, 0, 24, 24, color);
		g.drawImage(walkImg, x, y, x + (24*d), y + (24*d), 0, 0, 24, 24, color);

//		img.drawCentered(x,y);
		
//		walkImg.drawCentered(x, y);
	}
	
	public void die() {
		if(state != STATE_DEAD && state != STATE_DYING) {
			state = STATE_DYING;
			lfImage.startAnimation("die");
			timeSinceLastUpdate = 0;
			if( null != dieSound ) {

				dieSound.play(1.0f, 0.5f);
			}
		}
	}
	
	public int getState() {
		return state;
	}
}
