import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;


public class Joint {
	
	public Joint parent = null, child = null;
	
	public Vector2f origin = new Vector2f(100,100);
	public float radius = 8;
	
	public float getRadius() {
		return radius;
	}
	
	public void update(int delta) {
		if(null != parent) {
			float dist = parent.origin.distance(this.origin);
			float d = dist / (radius+parent.radius);
			
			Vector2f a = new Vector2f(parent.origin.x - origin.x, parent.origin.y-origin.y);
			a.scale(0.15f);
			Vector2f b = new Vector2f(origin.x + a.x, origin.y+a.y);
			
			if(parent.origin.distance(b) > (parent.radius  + radius)) {
				origin = b;
			}
			
			//origin = new Vector2f(parent.origin.x - (a.x * d), parent.origin.y - (a.y *d));

		}
		if(null != child) {
			child.update(delta);
		}
	}
	
	public boolean hitTest(Joint e) {
		Vector2f d = new Vector2f(Math.abs(this.origin.x - e.origin.x), 
				Math.abs(this.origin.y - e.origin.y));
		
		return  Math.sqrt(d.x*d.x + d.y*d.y) < (e.getRadius() + this.getRadius());  
	}
	
	public void render(Graphics g) {
		if(null != child) {
			child.render(g);
		}
	}
}
