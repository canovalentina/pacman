package Pacman;

import java.util.*;

/**
 * This class is designed to control the ghosts that are "eaten" when the game is in frightened mode. 
 * In general, it consists of a LinkedList of Ghosts that acts as a queue, and is associated with the GamePanel to be able 
 * to set locations, and be related with the ghosts from the GamePanel. This list works in FIFO (First In First Out): by 
 * calling addLast(Ghost g), it enqueues a ghost to the end of the list. By calling removeFirst() it dequeues the first
 * ghost to enter. 
 * 
 * @author vcano
 *
 */

public class GhostPen { 
	
	LinkedList<Ghost> _ghostList; 
	GamePanel _gamePanel;
	
	public GhostPen(GamePanel gamePanel){ //Constructor. 
		_ghostList = new LinkedList<Ghost>();	
		_gamePanel = gamePanel; 
	}
	
	public void releaseGhost(){ 
		if (_ghostList.isEmpty()==false){
		Ghost gh = _ghostList.removeFirst(); //Removes the ghost that first entered the pen and sets it to the initial location outside of the pen. 
		gh.setNewLoc(11*Constants.SQUARE_SIDE,8*Constants.SQUARE_SIDE);
		_gamePanel.repaint();
		}
	}
	
	public void addGhost(Ghost g){ 
		_ghostList.addLast(g); //Adds ghost to ArrayList and sets its location to a location inside the pen. 
		_gamePanel.repaint(); 
	}
	
	public boolean isEmpty(){ //Method that returns true if the list is empty and false if it contains any ghosts.  
		if (_ghostList.isEmpty()==true){
			return true;
		}
		else{
			return false;
		}
	}
	
}//class GhostPen
