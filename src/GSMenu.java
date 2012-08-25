
import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.TransitionTest;


public class GSMenu extends BasicGameState {

	Image backgroundImage;
	SpriteSheet cursor;
	
	UnicodeFont menuFont;
	
	int pointer = 0;
	boolean spacePressed = false;
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backgroundImage = new Image("res/background.png");
		cursor = new SpriteSheet("res/LifeForm.png", 16, 16);
		menuFont= new UnicodeFont(new Font("Helvetica", Font.PLAIN, 20));
		
		menuFont.addAsciiGlyphs();
		menuFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		menuFont.loadGlyphs();
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawImage(backgroundImage, 0, 0);
		
		
		g.setFont(menuFont);
		
		g.drawString("Start Game", 350, 200);		 
		g.drawString("Highscores", 350, 250);
		
		cursor.getSprite(0, 0).draw(320, 200 + (pointer*50));

	}

	public void update(GameContainer c, StateBasedGame game, int arg2)
			throws SlickException {
		if(spacePressed) {
			if(pointer == 0) {
				game.enterState(Main.GAMESTATE_GAME);
			}
		}
		
		spacePressed = false;		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Main.GAMESTATE_MENU;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		super.keyPressed(key, c);
		switch(key) {
			case Input.KEY_UP:
				pointer -= 1;
			break;
			case Input.KEY_DOWN:
				pointer+=1;
				break;
			case Input.KEY_SPACE:
				spacePressed = true;
				break;
		}
		pointer = (pointer + 2) % 2;
	}
}
