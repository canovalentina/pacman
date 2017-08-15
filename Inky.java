package Pacman;
import java.awt.*;

/**
 * Subclass of Ghost class. Inky is the blue ghost!
 * 
 * @author vcano
 *
 */

public class Inky extends Ghost {
	
	private GamePanel _gamePanel;
	private Pacman _pacman;
	
	public Inky(GamePanel gamePanel, Pacman pacman){
		super(gamePanel, pacman);
		_gamePanel = gamePanel;
		_pacman = pacman;
		this.setColor(Color.CYAN);
	}
	
	@Override
	public void setNormalColor(){ //Defines the abstract method setNormalColor()
		this.setColor(Color.CYAN);
	}
	
	@Override
	public void setChaseTarget(){ //Defines the abstract method setChaseTarget()
		//2 down from Pacman. 
		this.setTarget(_pacman.getX(), _pacman.getY()+(2*Constants.SQUARE_SIDE));
	}
	
	@Override
	public void setScatterTarget(){ //Defines the abstract method setScatterTarget()
		//Lower right corner. 
		this.setTarget(22*Constants.SQUARE_SIDE,22*Constants.SQUARE_SIDE);
	}

} // class Inky

