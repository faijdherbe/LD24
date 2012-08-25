import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class LifeForm {
	// eigenschappen
	
	Vector2f origin;
	static Image lfImage;
	
	public Vector2f inputForce = new Vector2f(0,0);
	public Vector2f velocity = new Vector2f(0,0);
	
	
	public LifeForm() {
		if(null == lfImage) {
			try {
				lfImage = new Image("res/LifeForm.png");
				lfImage.setCenterOfRotation(lfImage.getWidth()*0.5f, lfImage.getHeight()*0.5f);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		origin = new Vector2f(100, 10);
	}
	
	public void update(int delta) {
		velocity.x *= 0.99;
		velocity.y *= 0.99;
		
		velocity.x += inputForce.x * 0.1f;
		velocity.y += inputForce.y * 0.1f;
		
		origin.x += velocity.x;
		origin.y += velocity.y;
	}
	
	public void render(Graphics g) {
		g.drawImage(lfImage, origin.x-8, origin.y-8);
	}
	
}
