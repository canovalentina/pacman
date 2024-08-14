package Pacman;

/**
 * The enum Direction class is very important in the design for Pacman, because they are used for many methods such
 * as Pacman's move method, the Ghost's move and BFS method, etc. Enums are a special data type that permits that
 * a variable to be a set of predefined constants. Although they do not have a specific value, they represent the 
 * "idea" of a Direction, and can be used as conditions in if-statements and loops. 
 * 
 * @author vcano
 *
 */

public enum Direction { //Constructor. 

	UP, DOWN,LEFT, RIGHT; //4 possible directions in which a ghost or pacman can move. 
	
}//enum Direction 

