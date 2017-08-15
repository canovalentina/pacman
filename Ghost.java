package Pacman;

import java.awt.*;
import java.util.*;

/**The Ghost class is very important because it has the methods that will provide each individual Ghost with its
 * basic functionality. The Ghost class implements a method that uses artificial intelligence to find the 
 * fastest way to reach a certain target. This algorithm is know as Breadth-First-Search (or BFS). Because it will be 
 * contained in the MazeSquare's ArrayList, it implements the Collidable interface. 
 * 
 * It is important to note that this is an abstract class. Although some methods are identical for all of the ghosts, 
 * the default color, scatter target and chase target is different. Therefore, the methods that set these values are abstract 
 * methods that are defined by each subclass. 
 */

public abstract class Ghost extends cs015.prj.Shape.RectangleShape implements Collidable {

	private Direction _currDirection; 
	private double _targetX, _targetY; 
	private GamePanel _gamePanel;
	private MazeSquare _square; 
	private MazeSquare[][]_map;
	private Pacman _pacman;

	public Ghost(GamePanel gamePanel, Pacman pacman){ //Constructor associated with GamePanel. 
		this.setSize(Constants.SQUARE_SIDE, Constants.SQUARE_SIDE); //Same length and width as MazeSquares and Pacman. 
		_gamePanel = gamePanel; 
		_pacman = pacman; 
		_map = _gamePanel.getTheMap();	
		
	}

	public void setTarget(double x, double y){ //Sets the target of the ghost.
		_targetX = x; 
		_targetY = y; 
	}

	public void setDirection(Direction dir){ //Sets the current direction of the ghost. 
		_currDirection = dir; 
	}
	
	public void setFrightenedTarget(){
		this.setTarget(Math.random()*(22*Constants.SQUARE_SIDE), Math.random()*(22*Constants.SQUARE_SIDE)); 
	}
	
	public void setFrightenedColor(){
		/*Note: Since the board is blue, I preffered to set the frightened mode color to a light purple. */
		this.setColor(new Color(204,204,255));
	}
	
	/*Abstract methods defined in each subclass. */
	
	public abstract void setChaseTarget(); 
	
	public abstract void setScatterTarget();
	
	public abstract void setNormalColor();	
	
	
	/*Helper method that sets new location but also removes the ghost from the ArrayList of the previous position and adds it
	 * to the ArrayList of the new position. It also checks if the new location is a wall.*/

	public void setNewLoc(double x, double y){ 

		int lastIndexX = (int)this.getX()/Constants.SQUARE_SIDE;
		int lastIndexY = (int)this.getY()/Constants.SQUARE_SIDE;
		int newIndexX = (int)x/Constants.SQUARE_SIDE;
		int newIndexY = (int)y/Constants.SQUARE_SIDE;

		//Condition: new location can't be a wall or out of bounds. 

		if (_currDirection==Direction.RIGHT && this.getX()/Constants.SQUARE_SIDE==22 && this.getY()/Constants.SQUARE_SIDE==11){
			_map[22][11].removeFromList(this);
			this.setLocation(0,this.getY());
			_map[0][11].addToList(this);
		}

		if (_currDirection==Direction.LEFT && this.getX()/Constants.SQUARE_SIDE==0 && this.getY()/Constants.SQUARE_SIDE==11){
			_map[0][11].removeFromList(this);
			this.setLocation(22*Constants.SQUARE_SIDE,this.getY());
			_map[22][11].addToList(this);
		}

		else{ 
			if(_gamePanel.isAWall(newIndexX, newIndexY)==false) {
				_map[lastIndexX][lastIndexY].removeFromList(this);
				this.setLocation(x,y); 
				_map[newIndexX][newIndexY].addToList(this);	
			}
		}
	}

	public void move(){ 

		/*Method that moves the ghost depending on current direction and on whether the next position 
		 * is not a wall. It calls on the helper method setNewLoc(). */

		int currentIndexX = (int)this.getX()/Constants.SQUARE_SIDE; 
		int currentIndexY = (int)this.getY()/Constants.SQUARE_SIDE; 

		if (_currDirection == Direction.RIGHT){
			if (currentIndexX==22&&currentIndexY==11){ 
				this.setNewLoc(0,this.getY());	//Accounts for wrapping around in the left side.
			}
			else {
				this.setNewLoc(this.getX()+Constants.SQUARE_SIDE, this.getY());
			}
		}

		if (_currDirection == Direction.LEFT){ 
			if (currentIndexX==0&&currentIndexY==11){ //Accounts for wrapping around in the right side. 
				this.setNewLoc(Constants.SQUARE_SIDE*22,this.getY());	
			}
			else {
				this.setNewLoc(this.getX()-Constants.SQUARE_SIDE, this.getY());
			}
		}

		if (_currDirection == Direction.UP){
			this.setNewLoc(this.getX(), this.getY()-Constants.SQUARE_SIDE);
		}

		if(_currDirection==Direction.DOWN){
			this.setNewLoc(this.getX(), this.getY()+Constants.SQUARE_SIDE);
		}

		_gamePanel.repaint();

	}

	@Override
	public void makeCollision(MazeSquare square) {
		_square = square; 
		
		if (_gamePanel.getFright()==0){ //If not in frightened mode and there are lives left.
			_gamePanel.setLives(_gamePanel.getLives()-1); //Decrement lives by 1. 
			_gamePanel.updateLivesLabel();
			_gamePanel.collisionGhostNormal(); //Will set everything to initial position. 
		}
		
		if (_gamePanel.getFright()==1){//If in frightened mode and there are lives left.
			_gamePanel.setScore(_gamePanel.getScore()+200); //Updates score by 100. 
			_gamePanel.updateScoreLabel();
			_gamePanel.collisionGhostFrightened(this); //Will add ghost to ghostPen. 
		}
	}
	
	/*The BreadthFirstSearch() method implements an artificial intelligence alogrithm that, depending on a certain target, finds the Direction that
	 * the ghost must move in to get there the fastest. */

	public Direction BreadthFirstSearch(){ 
		
		//Important: this method uses local variables.

		LinkedList<Point> pointQueue = new LinkedList<Point>(); //LinkedList of points
		double minDistance = 1000000; //Initial minimum distance set to an absurdly large number. 
		Direction minDirection = null; //Initial minimum direction set to null. 
		int x = (int)this.getX()/Constants.SQUARE_SIDE; //Gets the current x index of ghost. 
		int y = (int)this.getY()/Constants.SQUARE_SIDE; //Gets the current y index of ghost. 
		Direction[][] _dirMap = new Direction[23][23]; //Constructs an array of Directions that will be filled to determine the best direction. 

		Point closestPoint = new Point(1000000,1000000);

		/*Conditions that must be met: not a 180 degrees turn, and not a wall*/ 

		/*Initial neighbors setup: pointQueue will add the points of the neighbors. The directions Array will be populated with the Direction of
		 * its neighbor. Condition: the Ghost cannot turn 180 degrees, and the neighbor cannot be a wall. 
		 * Note: My BFS points are in terms of indexes.*/ 

		if (x==22 && y==11 && _currDirection!=Direction.LEFT){ //Condition when it reaches the edge at wrapping around point on right side, neighbor is the x position at 0.  
			Point rightLoc = new Point(0, 11); 
			pointQueue.addLast(rightLoc); 
			_dirMap[0][y] = Direction.RIGHT;
		}

		if (x==0 && y==11 && _currDirection!=Direction.RIGHT){ //Condition when it reaches the edge at wrapping around point on left side, neighbor is the x position at 22.
			Point leftLoc = new Point(22, 11); 
			pointQueue.addLast(leftLoc); 
			_dirMap[22][y] = Direction.LEFT; 
		}

		if (_gamePanel.isAWall(x+1,y) == false && _currDirection!=Direction.LEFT){ 
			Point rightLoc = new Point((int) x + 1, (int) y);
			pointQueue.addLast(rightLoc); 
			_dirMap[x+1][y] = Direction.RIGHT; 
		}

		if ( _gamePanel.isAWall(x-1,y)==false && _currDirection!=Direction.RIGHT){
			Point leftLoc = new Point((int)x-1,(int)y);
			pointQueue.addLast(leftLoc); 
			_dirMap[x-1][y] = Direction.LEFT; 
		}

		if (_gamePanel.isAWall(x,y-1) == false&&_currDirection!=Direction.DOWN){ 
			Point upLoc = new Point((int)x,(int)y-1);
			pointQueue.addLast(upLoc); 
			_dirMap[x][y-1] = Direction.UP; 
		}

		if (_gamePanel.isAWall(x,y+1) == false && _currDirection!=Direction.UP){
			Point downLoc = new Point((int)x,(int)y+1);
			pointQueue.addLast(downLoc); 
			_dirMap[x][y+1] = Direction.DOWN; 
		}

	
		while(pointQueue.isEmpty()==false) { //While loop that checks neighbors of neighbors." 	

			Point lastPoint = pointQueue.removeFirst(); 
			int lastX = (int) lastPoint.getX();
			int lastY = (int) lastPoint.getY(); 

			Direction currDirection = _dirMap[lastX][lastY]; //CurrentDirection is direction on the Direction array of the last point. 
			
			//target divided by Constants.SQUARE_SIDE because the target is in terms of locations and the points are in terms of indexes.
			double deltaX = Math.pow(((_targetX/Constants.SQUARE_SIDE)-lastX), 2);  
			double deltaY = Math.pow(((_targetY/Constants.SQUARE_SIDE)-lastY), 2);
			double dist = Math.sqrt(deltaX + deltaY); //Calculates the distance between the point and the Target. 

			if (dist<minDistance){ //If the distance calculated is less than the current minimum distance. 
				minDistance = dist; 
				closestPoint = lastPoint; //Set the variable closestPoint to the point that was just dequeued. 
			}
			
			/*Condition that must be met: the neighbor cannot be a wall, and cannot have been visited yet. */
			
			if (lastX==22&&lastY==11 && _dirMap[0][lastY]== null){ //Accounts for wrapping around on the right side.  
				pointQueue.add(new Point(0,lastY)); 
				_dirMap[0][lastY]= currDirection; 
			}

			if (lastX==0 && lastY==11 && _dirMap[22][lastY]== null){ //Accounts for wrapping around on the right side.
				pointQueue.add(new Point(22, lastY)); 
				_dirMap[22][lastY]= currDirection; 
			}
			

			if (_gamePanel.isAWall(lastX+1,lastY) == false&&_dirMap[lastX+1][lastY]== null /*Checks that not visited yet. */){ 
				pointQueue.add(new Point(lastX+1,lastY)); 
				_dirMap[lastX+1][lastY]= currDirection; 	
			}

			if (_gamePanel.isAWall(lastX-1,lastY) == false&&_dirMap[lastX-1][lastY]==null) {
				pointQueue.add(new Point(lastX-1,lastY)); 
				_dirMap[lastX-1][lastY] = currDirection; 
			}

			if (_gamePanel.isAWall(lastX,lastY-1) == false &&_dirMap[lastX][lastY-1]==null) {
				pointQueue.add(new Point(lastX,lastY-1)); 
				_dirMap[lastX][lastY-1] = currDirection;
			}

			if (_gamePanel.isAWall(lastX,lastY+1) == false && _dirMap[lastX][lastY+1]==null) {	
				pointQueue.add(new Point(lastX,lastY+1)); 
				_dirMap[lastX][lastY+1] = currDirection; 
			}
		}
		
		//Get the direction of the X and Y indexes of the closestPoint. 
		
		minDirection = _dirMap[(int) closestPoint.getX()][(int) closestPoint.getY()];

		return minDirection; //returns the Direction in which the ghost must move. 
	}

} //class Ghost 
