import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.Vector2f;

public class HitArea {
	public final static int STATE_INIT = 0;
	public final static int STATE_OPEN = 1;
	public final static int STATE_CLOSING = 9;
	public final static int STATE_CLOSED = 2;
	public final static int STATE_CLOSED_INVALID = 3;
	public final static int STATE_DESTROYING = 11;

	public final static int STATE_DESTROY = 99;

	private ArrayList<Vector2f> points = new ArrayList<Vector2f>();

	private static Image pointImage;

	private int state = STATE_INIT;
	private int state_closing = 0;
	int timeSinceLastUpdate = 0;

	Sound dropPoint = null ,pointFix = null;

	int max = 3;

	Polygon shape = null;

	public boolean highlight;

	public HitArea() {
		setup(3);
	}

	public HitArea(int max) {
		setup(max);
	}

	private void setup(int max) {
		this.max = max;

		shape = new Polygon();
		shape.setClosed(false);

		try {
			pointImage = new Image("res/point.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		state = STATE_OPEN;
		
		try {
			dropPoint = new Sound("res/dropPoint.wav");
			pointFix = new Sound("res/pointFix.wav");
		} catch(SlickException e) {}
	}

	public void addPoint(Vector2f point) {
		if (state != STATE_OPEN) {
			return;
		}

		this.points.add(point);
		this.shape.addPoint(point.x, point.y);
		if(null != dropPoint) {
			dropPoint.play((float)(1 + (points.size() / (float)max ) * 2 ), 1.0f);
		}
	}

	public int state() {
		return state;
	}
	float fillTime = 250;
	
	public void update(int delta) {
		timeSinceLastUpdate += delta;

		if (state == STATE_CLOSED) {
			state = STATE_DESTROYING;
		} else if( state == STATE_DESTROYING) {
			if (timeSinceLastUpdate > 1000) {
				state = STATE_DESTROY;
			}
		} else if (state == STATE_CLOSING) {

			while (timeSinceLastUpdate > fillTime) {
				state_closing++;
				timeSinceLastUpdate -= fillTime;
				fillTime *= 0.95;
				
				if(null != pointFix) {
					pointFix.play((float)(1 + (state_closing / (float)max ) * 3 ), 1.0f);
				}
			}
			if (state_closing > points.size()) {
				state = STATE_CLOSED;
				shape.setClosed(true);
				timeSinceLastUpdate = 0;
			}
		} else {

			if (max == points.size() && state != STATE_DESTROY) {
				state = STATE_CLOSING;
				timeSinceLastUpdate = 0;
				fillTime= 250;
			}
		}
	}

	public boolean hitTest(LifeForm e) {
		//return shape.contains(new Circle(e.origin.x, e.origin.y, e.getRadius()*0.25f));
		return shape.contains(e.origin.x, e.origin.y);
	}

	public void render(Graphics g) {
		if (shape.closed()) {
			if (state == STATE_CLOSED || state == STATE_DESTROYING) {

				g.setColor(new Color(0.0f,1.0f,0.0f,0.2f));
			} else if (state == STATE_CLOSED_INVALID) {
				g.setColor(new Color(255, 0, 0, 128));
			} else {
				g.setColor(new Color(255, 255, 255, 128));

			}

			g.fill(shape);
		}
		//

		if (state == STATE_CLOSING) {
			Polygon p = new Polygon();
			for (int i = 0; i < Math.min(points.size(), state_closing); i++) {
				// slow
				p.addPoint(points.get(i).x, points.get(i).y);
			}

			p.setClosed(state_closing >= points.size());
			g.setColor(Color.white);
			if (state_closing > 0)
				g.draw(p);

		} else if (state == STATE_CLOSED || state == STATE_DESTROYING) {

			g.setColor(new Color(255, 255, 255, 255));
			g.draw(shape);
		}
		for (Vector2f v : points) {
			g.drawImage(pointImage, v.x - 2, v.y - 2);
		}

	}
}
