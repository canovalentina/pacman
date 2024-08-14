package Pacman;
import java.awt.event.*;

/**
 * Pacman has two timers: one that controls the general functionality of the game, and one that controls
 * the ghost pen class. 
 * 
 * This class implements the ActionListener interface, to be able to define the method that 
 * the general Timer will perform. 
 * 
 * @author vcano
 */

public class AnimationTimerListener implements ActionListener{

	private GamePanel _gamePanel;
	private double _counter, _fearCounter; 

	public AnimationTimerListener(GamePanel gamePanel) { //Constructor. Has an association with the GamePanel.
		_gamePanel = gamePanel; 

		//This timer uses two counters to keep track of how much time has passed before it has to switch modes.
		_counter = 0;
		_fearCounter = 0; 	
	}

	@Override
	public void actionPerformed (ActionEvent e){  //Calls on methods defined in the associated GamePanel. 
		
		_gamePanel.repaint(); //Will repaint gamePanel on each tick. 
		_counter += .333; //Increment the counter (my Timestep is 300). 
		_gamePanel.gameOver(); //Constantly checks if the condition for gameOver is met. When it is, this method will be called. 
		_gamePanel.winGame(); //Constantly checks if the condition for winGame is met. When it is, this method will be called. 

		if (_gamePanel.getFright() == 0){ //Int set by other methods that determines whether the game is in frightened mode (1) or not (0). 
		
			if (_counter >= 0 && _counter < 20){ //from 0 to 20 seconds run in chase mode. 
				_gamePanel.animatePacman(); //Moves Pacman. 
				_gamePanel.chaseMode();	//Moves Ghosts. 			
			}
			
			else if (_counter >= 20 && _counter < 28){ //from 20 to 28 seconds run in scatter mode. 
				_gamePanel.scatterMode(); //Moves Pacman. 
				_gamePanel.animatePacman(); //Moves Ghosts. 
			}
			
			else if (_counter >= 28){ //When it reaches the end of scatter mode reset to 0. 
				_gamePanel.makeCollision(); 
				_counter = 0; 
			}
		}
		
		if (_gamePanel.getFright() == 1){ //If the game is in frightened mode (1). 
			_counter = 0; //set the other counter to 0. 
			_fearCounter+= .333; //Increment the second counter. 

			if (_fearCounter >= 0 && _fearCounter < 8){ //For 8 seconds run in frightened mode. 
				_gamePanel.animatePacman(); //Moves Pacman. 
				_gamePanel.frightenedMode(); //Moves Ghosts. 
			}
			
			else if (_fearCounter>=8){ //when the counter reaches 8 
				_gamePanel.makeCollision();
				_fearCounter = 0; //reset fearCounter to 0
				_gamePanel.setFright(0); //setFright to 0 so that it can enter the other loop again. 
			}
		}
	}
	
}//class AnimationTimerListener