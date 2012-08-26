import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Tailpiece extends Joint {

	protected ESprite tailImage;
	Color color = Color.white;
	
	public Tailpiece() {
		try {
			tailImage = new ESprite("LifeForm.png",24,24);	
			tailImage.addAnimation("idle", 1, new int[] {0}, true);

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

	@Override
	public void render(Graphics g) {
		Image img = tailImage.getImage();
		float d = 1;
		float x = origin.x - (12 * d);
		float y = origin.y - (12 * d);
		g.drawImage(img, x, y, x + (24*d), y + (24*d), 0, 0, 24, 24, color);
		
		super.render(g);
	}
}
