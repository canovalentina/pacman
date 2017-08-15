package Pacman;

/**
 * This interface was designed to be implemented by every object that can be contained in the ArrayList 
 * of the MazeSquares. By implementing a certain interface it can be specified as the generic type  of the 
 * ArrayList, therefore permitting type checking, and also being able to write methods for the type Collidable.
 * 
 * @author vcano
 */

import java.awt.Graphics;

public interface Collidable {
	
	public abstract void makeCollision(MazeSquare square); //Will determine what collision will happen when Pacman enters the square. 
	public abstract void paint(Graphics g); 
	public abstract void setLocation(double x, double y); //Override to change location (Exm: dots 10 more to the right)

} //interface Collidable 
