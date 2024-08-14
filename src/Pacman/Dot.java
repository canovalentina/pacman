package Pacman;
import java.awt.Color;

/**
 * The Dot class constructs the small dots that will be laid out in certain points of the board and that
 * boost the score of the Game 10 points when eaten. I decided to make it a subclass of the EllipseShape class
 * so that it would automatically have all of the methods of this class. Also, because it will be contained in the MazeSquare's ArrayList, 
 * it implements the Collidable interface. 
 * 
 * @author vcano
 *
 */

public class Dot extends cs015.prj.Shape.EllipseShape implements Collidable {
	
	private MazeSquare _square; 
	private GamePanel _gamePanel; 
	
	public Dot(GamePanel gamePanel){ //Constructor. Association with GamePanel. 
		this.setSize(6,6); 
		this.setColor(Color.WHITE);
		this.setVisible(true);
		_gamePanel = gamePanel;
		 
	}

	@Override
	public void makeCollision(MazeSquare square) { //When colliding, it removes itself from the ArrayList and updates the score by 10. 
		_square = square;
		_square.removeFromList(this); 
		_gamePanel.setScore(_gamePanel.getScore()+10); 
		_gamePanel.updateScoreLabel(); 
		_gamePanel.setDots(_gamePanel.getDots()+1); //Dot counter (keeps track of how many dots have been eaten) incremented by 1. 
	}
	
	@Override
	public void setLocation(double x, double y){ //So that it looked better graphically, I did override to the setLocation method to be off by 10 pixels. 
		super.setLocation(x+10,  y+10);
	}

} //class Dot
