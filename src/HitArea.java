import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;




public class HitArea {
	private ArrayList<Vector2f> points = new ArrayList<Vector2f>();
	
	private static Image pointImage;
	
	int max = 3;
	
	Polygon shape = null;
	
	public HitArea() {
		setup(5);
	}
	
	public HitArea( int max ) {
		setup(max);
	}
	
	private void setup(int max) {
		this.max = max;
		
		shape = new Polygon();
		shape.setClosed(false);
		
		try {
			pointImage = new Image("res/point.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addPoint(Vector2f point) {
		if( shape.closed() ) { return; }
		
		this.points.add(point);
		this.shape.addPoint(point.x, point.y);
	}
	
	public void update(int delta) {
		if(max == points.size()) {
			shape.setClosed(true);
		}
	}
	
	public void render(Graphics g) {
		
		if(shape.closed()) {
			g.setColor(new Color(255, 255, 255, 128));
			g.fill(shape);
		}

		g.setColor(new Color(255,255,255,255));
		g.draw(shape);
		
		for(Vector2f v : points) {
			g.drawImage(pointImage, v.x -2, v.y - 2);
		}
		
	}
}
