package Pacman;

import java.awt.Color;

/**
 * Subclass of Ghost class. Pinky is the pink ghost!
 * 
 * @author vcano
 *
 */

public class Pinky extends Ghost{
	
	private GamePanel _gamePanel;
	private Pacman _pacman;
	
	public Pinky(GamePanel gamePanel, Pacman pacman){
		super(gamePanel, pacman);
		_gamePanel = gamePanel;
		_pacman = pacman;
		this.setColor(Color.PINK);
	}
	
	@Override 
	public void setNormalColor(){ //Defines the abstract method setNormalColor()
		this.setColor(Color.PINK);
	}
	
	@Override
	public void setChaseTarget(){ //Defines the abstract method setChaseTarget()
		//1 right, 4 up from Pacman.
		this.setTarget(_pacman.getX()+Constants.SQUARE_SIDE,_pacman.getY()-(4*Constants.SQUARE_SIDE));
	}
	
	@Override
	public void setScatterTarget(){ //Defines the abstract method setScatterTarget()
		//Upper right corner. 
		this.setTarget(22*Constants.SQUARE_SIDE,0); 
	}

} //class Pinky
