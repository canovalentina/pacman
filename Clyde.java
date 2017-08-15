package Pacman;
import java.awt.*;

/**
 * Subclass of Ghost class. Clyde is the orange ghost!
 * 
 * @author vcano
 *
 */

public class Clyde extends Ghost {
	
	private GamePanel _gamePanel;
	private Pacman _pacman;
	
	public Clyde(GamePanel gamePanel, Pacman pacman){
		super(gamePanel, pacman);
		_gamePanel = gamePanel;
		_pacman = pacman;
		this.setColor(Color.ORANGE);
	}
	
	@Override
	public void setNormalColor(){ //Defines the abstract method setNormalColor()
		this.setColor(Color.ORANGE);
	}
	
	@Override
	public void setChaseTarget(){ //Defines the abstract method setChaseTarget()
		//4 left from Pacman.
		this.setTarget(_pacman.getX()-(4*Constants.SQUARE_SIDE),(int) _pacman.getY());
	}
	
	@Override
	public void setScatterTarget(){ //Defines the abstract method setScatterTarget()
		//Lower left corner. 
		this.setTarget(0,22*Constants.SQUARE_SIDE);
	}
	
} //class Clyde

