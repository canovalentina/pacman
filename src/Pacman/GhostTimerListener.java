package Pacman;

import java.awt.event.*;

/**
 * The GhostTimerListener is the second timer that the game has. It keeps track of the GhostPen and releases
 * a ghost every 8 seconds. 
 * This class implements the ActionListener interface, to be able to define the method that 
 * the Timer for the GhostPen will perform. 
 * 
 * @author vcano
 *
 */

public class GhostTimerListener implements ActionListener {

	private GhostPen _pen;
	private double _time; 

	public GhostTimerListener(GhostPen pen){ //Constructor. Association with the GhostPen. 
		_pen = pen; 
		_time = 0; 	//Counter initially set to 0
	}

	public void actionPerformed (ActionEvent e){ 

		_time += .33; //Increment the counter (my Timestep is 300). 
		
		if (_time >= 8){ //When counter gets to 8, if the GhostPen contains a ghost.
			_pen.releaseGhost(); //Release a Ghost. 
			_time = 0; //Set counter back to 0, to get out of this if-condition and continue incrementing it, until it reaches 8 again.
		}	
	}

} //class GhostTimerListener
