package Pacman;
import java.awt.*;

/**
 * Although in my initial deign I had decided to not make subclasses for the Ghosts, I decided that for encapsulation purposes it was better 
 * to have separate classes for each ghosts that are able to set their default color, and set their chase and scatter targets (which are different for each ghost). 
 * Blinky is the red ghost! 
 * 
 * @author vcano
 *
 */

public class Blinky extends Ghost {
	
	private GamePanel _gamePanel;
	private Pacman _pacman;
	
	public Blinky(GamePanel gamePanel, Pacman pacman){
		super(gamePanel, pacman);
		_gamePanel = gamePanel;
		_pacman = pacman;
		this.setColor(Color.RED);
	}
	
	@Override
	public void setNormalColor(){ //Defines the abstract method setNormalColor()
		this.setColor(Color.RED);
	}
	
	@Override
	public void setChaseTarget(){ //Defines the abstract method setChaseTarget()
		//Pacman. 
		this.setTarget(_pacman.getX(), _pacman.getY());
	}
	
	@Override
	public void setScatterTarget(){ //Defines the abstract method setScatterTarget()
		//Upper left corner. 
		this.setTarget(0,0); 
	}
	
} //class Blinky
