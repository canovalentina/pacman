package Pacman;
import java.awt.*;

/**
 * The MazeSquare class is a very important class for this program, because it permits the creation of an
 * object-oriented array of squares, which each contain certain methods and data structures. One of the
 * most important aspects of this class is that it contains an ArrayList that can contain any object that 
 * implemented the Collidable interface. I decided it should extend the RectangleShape class, because this
 * way it can have all of its parent's methods. 
 * 
 * @author vcano
 *
 */

public class MazeSquare extends cs015.prj.Shape.RectangleShape {

	private java.util.ArrayList<Collidable> _list; 	//List that will contain Ghosts, Dots and/or Energizers.

	public MazeSquare(){ //Constructor.
		super();
		this.setSize(Constants.SQUARE_SIDE, Constants.SQUARE_SIDE); //Same length and width as Pacman and Ghosts. 
		this.setFillColor(Color.BLACK); //Default color is black. If it is a wall, the color is set in the GamePanel class. 
		_list = new java.util.ArrayList<Collidable>();  
	}

	public void addToList(Collidable c){
		_list.add(c); //Adds object passed in as a parameter to the ArrayList. 
	}

	public void removeFromList(Collidable c){ 
		if (_list.isEmpty()==false){ //If list isn't empty. 
			_list.remove(c); //Removes the object passed in as a parameter. 
		}
	}

	public boolean emptyList(){ //Method to check if list is empty, returns a boolean depending on whether the condiiton is true or false.
		if (_list.isEmpty()==false){
			return false;
		}
		else{
			return true; 
		}
	}

	public void makeCollision(){ //Calls on the makeCollision() method of each Collidable contained in the ArrayList. 
		for(int i = 0; i<_list.size(); i++){
			_list.get(i).makeCollision(this);
		}
	}

	@Override
	public void setLocation(double x, double y){ //Sets its location, and the location of everything in its ArrayList with a for-loop.
		super.setLocation(x,y); 
		if (_list.isEmpty() == false){
			for(int i = 0; i<_list.size(); i++){ //Keeps incrementing until it reaches the length of the ArrayList. 
				_list.get(i).setLocation(x,y);
			}
		}
	}

	@Override
	public void paint(Graphics g){ //Paints itself and every component in its ArrayList with a for-loop. 
		super.paint(g);
		if (_list.isEmpty() == false){
			for(int i = 0; i<_list.size(); i++){
				_list.get(i).paint(g);
			}
		}
	}

} //class MazeSquare
