import java.util.HashMap;

import org.lwjgl.Sys;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class ESprite extends SpriteSheet {


	public ESprite(String ref, int tw, int th) throws SlickException {
		super(ref, tw, th);
	}

	private class Animation {
		private int fps = 16;
		private int[] frames = null;
		private boolean loop = true;
		int frameNumber = 0;
		int timeSinceLastFrame = 0;
		
		public Animation(int fps, int[] frames, boolean loop) {
			this.fps = fps;
			this.frames = frames;
			this.loop = loop;
		}
		
		public void start() {
			frameNumber = 0;
			timeSinceLastFrame = 0;
		}
		
		public void update(int delta) {
			timeSinceLastFrame += delta;
			
			while(timeSinceLastFrame > 1000/fps) {
				if (loop) {
					frameNumber = (frameNumber + 1) % frames.length;
				} else {
					frameNumber = Math.min(frames.length - 1, frameNumber + 1);
				}
				timeSinceLastFrame -= (1000/fps);
			}
		}
		
		public int frameNumber() {
			return frames[frameNumber];
		}
	}
	
	private HashMap<String, ESprite.Animation> animations = new HashMap<String, ESprite.Animation>();
	private Animation currentAnimation = null;
	
	public void addAnimation(String name, int fps, int[] frames, boolean loop) {
		animations.put(name, new Animation(fps, frames, loop));
		if(null == currentAnimation) {
			startAnimation(name);
		}
	}
	
	public void startAnimation(String name) {
		if(animations.containsKey(name)) {
			currentAnimation = animations.get(name);
			currentAnimation.start();
		}
	}
	
	public void update(int delta) {
		if(null != currentAnimation) {
			currentAnimation.update(delta);
		}
	}
	
	public Image getImage() {
		if(null == currentAnimation) {
			return this;
		}
		
		int x = currentAnimation.frameNumber() % getHorizontalCount() ;
		int y = (int)Math.ceil(currentAnimation.frameNumber() / getHorizontalCount());

		return this.getSprite(x, y);
	}
}
