import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Main extends StateBasedGame {
	public final static int GAMESTATE_GAME = 10;
	
	
	public Main(String name) {
		super(name);

	}

	public void initStatesList(GameContainer c) throws SlickException {
		// TODO Auto-generated method stub
		
		addState(new GSGame()); //  Game
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

        try {
            AppGameContainer app = new AppGameContainer(new Main("LD24: Evolution"));
            app.setDisplayMode(800, 600, false);
            app.setTargetFrameRate(60);
            
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
	}

}
