import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class LD24Evolution extends StateBasedGame {
	public final static int GAMESTATE_GAME = 10;
	public final static int GAMESTATE_MENU = 20;
	
	
	public LD24Evolution(String name) {
		super(name);

	}

	public void initStatesList(GameContainer c) throws SlickException {
		// TODO Auto-generated method stub
		addState(new GSMenu()); //Menu
		
		addState(new GSGame()); //  Game
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        try {
            AppGameContainer app = new AppGameContainer(new LD24Evolution("LD24: Evolution - Jeroen Faijdherbe"));
            app.setDisplayMode(800, 600, false);
            app.setTargetFrameRate(60);
            app.setShowFPS(false);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
	}

}
