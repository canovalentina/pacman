package Pacman;

import java.awt.Color;

/**
 * Pacman!
 * I decided to make Pacman a subclass of EllipseShape, so that it could have all of its parents' methods without 
 * needing to re-write them. This is a simple class, but it was a very important design decision to make Pacman a
 * separate class because this way it can define the methods  that will take care of his movement and keep track of
 * its location, without needing to overcomplicate the GamePanel. 
 * 
 * @author vcano
 *
 */

public class Pacman extends cs015.prj.Shape.EllipseShape{

	private GamePanel _gamePanel; 
	private Direction _currDir; 

	public Pacman(GamePanel gamePanel){ //Constructor. Association with the GamePanel. 
		super();
		_gamePanel = gamePanel; 
		this.setColor(Color.YELLOW);//Color. 
		this.setSize(Constants.SQUARE_SIDE, Constants.SQUARE_SIDE); //Same length and width as ghosts and MazeSquares. 
		_currDir = Direction.RIGHT; //Initial direction set to right. 
	}

	public void setDirection(Direction dir){ //Sets the current direction to a specific value. 
		_currDir = dir;  //The KeyListener will use this method to change the Direction in which Pacman is going. 
	}

	public void move(){
		
		/*Although Pacman is not contained in the ArrayList of the MazeSquare, it is important to consider the
		X and Y indexes to be able to determine if the new position is not a wall.*/
		
		int currentIndexX = (int)this.getX()/Constants.SQUARE_SIDE; 
		int currentIndexY = (int)this.getY()/Constants.SQUARE_SIDE; 
		
		if (_currDir==Direction.RIGHT){ //If moving right. 
			if (currentIndexX==22&&currentIndexY==11){ //If entering right tunnel. 
				this.setLocation(0,this.getY()); //Set location to the left side. 
			}
			else if (_gamePanel.isAWall(currentIndexX+1, currentIndexY)==false){ //If in any other location and if new location is not a wall.
				this.setLocation(this.getX()+Constants.SQUARE_SIDE, this.getY()); //Move one square to the right. 
			}
		}

		if (_currDir==Direction.LEFT){  //If moving left. 
			if (currentIndexX==0&&currentIndexY==11){ //If entering left tunnel. 
				this.setLocation(Constants.SQUARE_SIDE*22,this.getY());	//Set location to right side.  
			}
			else if (_gamePanel.isAWall(currentIndexX-1, currentIndexY)==false){ //If in any other location, and if new location is not a wall. 
				this.setLocation(this.getX()-Constants.SQUARE_SIDE, this.getY()); //Move one square to the left. 
			}
		}

		if (_currDir==Direction.UP){ //If moving up. 
			if (_gamePanel.isAWall(currentIndexX, currentIndexY-1)==false){  //If new location is not a wall.
				this.setLocation(this.getX(), this.getY()-Constants.SQUARE_SIDE); //Move one square up. 
			}
		}

		if(_currDir==Direction.DOWN){ //If moving down. 
			if (_gamePanel.isAWall(currentIndexX, currentIndexY+1)==false){ //If new location is not a wall. 
				this.setLocation(this.getX(), this.getY()+Constants.SQUARE_SIDE); //Move one square down. 
			}
		}
		_gamePanel.repaint();
	}

} //class Pacman 
