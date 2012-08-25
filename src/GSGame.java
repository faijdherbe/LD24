import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GSGame extends BasicGameState {

	LifeForm player;
	ArrayList<LifeForm> enemies = new ArrayList<LifeForm>();

	Image backgroundImage;
	Image lifeForm;

	boolean upPressed, downPressed, leftPressed, rightPressed;
	boolean actionPressed;

	int maxActionPoints = 993;

	HitArea hitArea = null;
	
	public void init(GameContainer c, StateBasedGame game)
			throws SlickException {
		backgroundImage = new Image("res/background.png");
		lifeForm = new Image("res/LifeForm.png");
		player = new LifeForm();

		for(int i = 0; i < 3; i++) {
			enemies.add(new Enemy(null));
		}
	}

	public void render(GameContainer c, StateBasedGame game, Graphics g)
			throws SlickException {
		// background
		g.drawImage(backgroundImage, 0, 0);

		// render enemies
		for (LifeForm lf : enemies) {
			lf.render(g);
		}
		
		// render player
		if(null != player) {
			player.render(g);
		} else {
			g.setColor(Color.white);
			g.drawString("Game Over!", 200, 290);
		}

		if (null != hitArea) {
			hitArea.render(g);
		}

	}

	public void update(GameContainer c, StateBasedGame game, int delta)
			throws SlickException {

		// update enemies
		for (LifeForm lf : enemies) {
			lf.update(delta);
		}
		
		if(null != player) {
			if(player.getState() == LifeForm.STATE_DEAD) {
				hitArea = null;
				player = null;
			} else { 
			
				player.inputForce.x = (leftPressed ? -1 : 0)
						+ (rightPressed ? 1 : 0);
				player.inputForce.y = (upPressed ? -1 : 0)
						+ (downPressed ? 1 : 0);
				player.update(delta);

				boolean hit = false;
				for (LifeForm e : enemies) {
					if (player.hitTest(e)) {
						hit = true;
					}
				}
				if (hit) {
					player.die();
				}
			}
		}

		if (actionPressed) {
			if (null == player) {
				player = new LifeForm();
				player.origin = new Vector2f(100, 100);

			} else {
				if(player.getState() != LifeForm.STATE_DEAD && player.getState() != LifeForm.STATE_DYING) { 

					if (null == hitArea) {
						hitArea = new HitArea();
					}
					
					hitArea.addPoint(new Vector2f(player.origin.x, player.origin.y));
				}
			}
			actionPressed = false;
		}

		if (null != hitArea) {
			hitArea.update(delta);
			if (hitArea.state() == HitArea.STATE_DESTROY) {
				hitArea = null;
			} else if(hitArea.state() == HitArea.STATE_CLOSED) {

				hitArea.highlight = false;
				
				for (LifeForm e : enemies) {
					if(hitArea.hitTest(e)) {
						e.die();
					}
				}
				
			} else {
			
				
			}
		}

		int enemiesDied = 0;
		for(LifeForm e : (ArrayList<LifeForm>)enemies.clone()) {
			if(e.getState() == LifeForm.STATE_DEAD) {
				enemies.remove(e);
				enemiesDied ++;
			}
		}

		
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);

		switch (key) {
		case Input.KEY_UP:
			upPressed = true;
			break;
		case Input.KEY_DOWN:
			downPressed = true;
			break;
		case Input.KEY_LEFT:
			leftPressed = true;
			break;
		case Input.KEY_RIGHT:
			rightPressed = true;
			break;
		case Input.KEY_SPACE:
			actionPressed = true;
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		super.keyReleased(key, c);

		switch (key) {
		case Input.KEY_UP:
			upPressed = false;
			break;
		case Input.KEY_DOWN:
			downPressed = false;
			break;
		case Input.KEY_LEFT:
			leftPressed = false;
			break;
		case Input.KEY_RIGHT:
			rightPressed = false;
			break;
		case Input.KEY_A:
			enemies.add(new Enemy(null));
			break;
		}

	}

	public int getID() {
		// TODO Auto-generated method stub
		return Main.GAMESTATE_GAME;
	}

}
