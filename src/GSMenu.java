
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class GSMenu extends BasicGameState {

	Image backgroundImage;
	
	
	boolean spacePressed = false;
	double highscore = 0;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backgroundImage = new Image("MenuBG.png");

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub
		super.enter(container, game);
		
		SavedState savedState = new SavedState("evolutionSavedState");
		highscore = savedState.getNumber("highscore");
		
	}
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawImage(backgroundImage, 0, 0);
		
		g.drawString(String.format("%2.0f", highscore), 397, 333);
	
	}

	public void update(GameContainer c, StateBasedGame game, int arg2)
			throws SlickException {
		if(spacePressed) {
		
				game.enterState(LD24Evolution.GAMESTATE_GAME);
	
		}
		
		spacePressed = false;		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return LD24Evolution.GAMESTATE_MENU;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		switch(key) {
			case Input.KEY_SPACE:
				spacePressed = true;
				break;
		}
	}
}
