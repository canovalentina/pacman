package Pacman;

import java.awt.Color;

/**
 * The Energizer class is similar to the Dot class in that it extends the EllipseShape class, implements the Collidable 
 * interface and constructs a white dot that will be set in certain locations of the board and will collide with Pacman. 
 * Nevertheless, it is different in that it is larger and its makeCollision adds extra functionality. 
 * 
 * @author vcano
 *
 */

public class Energizer extends cs015.prj.Shape.EllipseShape implements Collidable {

	private MazeSquare _square; 
	private GamePanel _gamePanel; 
	
	public Energizer(GamePanel gamePanel){ //Constructor. Association with GamePanel. 
		this.setSize(12,12); 
		this.setColor(Color.WHITE);
		_gamePanel = gamePanel; 
	}

	@Override
	public void makeCollision(MazeSquare square) { //Collision method
		_square = square; 
		_square.removeFromList(this); //Removes itself from ArrayList in MazeSquare. 
		_gamePanel.setScore(_gamePanel.getScore()+100); //Updates score by 100. 
		_gamePanel.updateScoreLabel();
		_gamePanel.setEnergizers(_gamePanel.getEnergizers()+1); //Energizer counter (keeps track of how many energizers have been eaten) incremented by 1. 
		_gamePanel.setFright(1); //Sets game to frightened mode. 
	}
	
	@Override
	public void setLocation(double x, double y){ //Override setLocation method for graphical purposes. 
		super.setLocation(x+8,y+7);
	}
	
} //class Energizer