import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;


public class Enemy extends LifeForm {
	LifeForm player;
	
	public Enemy(LifeForm player) {
		// TODO Auto-generated constructor stub
		origin = new Vector2f(400,300);
		this.player = player;


		try {
			dieSound = new Sound("res/enemyDie.wav");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		color = Color.red;
		
		addSpeedLevel((int)Math.round(Math.random() * 10));
	}
	
	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		super.update(delta);

		if(getState() == STATE_DEAD) {
			
		} else {
			Vector2f v = new Vector2f(0,0);
			if(null != player) {
					v = new Vector2f(player.origin.x-origin.x, player.origin.y-origin.y);
					v.normalise();
			} 
	
			v.x += ((float) Math.random() * 2) - 1;
			v.y += ((float) Math.random() * 2) - 1;
			
			v.normalise();
			
			inputForce = v;
		}
		
	}
}
