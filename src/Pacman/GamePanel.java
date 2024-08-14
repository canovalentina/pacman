package Pacman;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The GamePanel class is the most important class in the program, because it contains most of the important
 * methods (or calls on methods defined in classes that are instantiated in the GamePanel) that will define the
 * game's functionality, as well as a private inner class that will determine the user input with the keys.
 * 
 * @author vcano
 *
 */

public class GamePanel extends JPanel {

	private Ghost _blinky, _inky, _pinky, _clyde; //Use of polymorphism
	private GhostPen _ghostPen; 
	private int _score, _lives, _fright, _dots, _energizers; 
	private int[][] _supportMap; 
	private JLabel _scoreLabel, _livesLabel, _gameOverLabel, _winLabel;
	private MazeSquare[][] _squareMap; 
	private Pacman _pacman; 
	private PacmanKeyListener _pkl;
	private Timer _timer, _penTimer; 

	public GamePanel(){ //Constructor. 

		super();
		Dimension panelSize = new Dimension(Constants.SQUARE_SIDE*23, Constants.SQUARE_SIDE*23); 
		this.setPreferredSize(panelSize); //set Size 
		this.setBackground(Color.WHITE);

		/*Adding KeyListener and other necessary methods for keys:*/ 
		_pkl = new PacmanKeyListener(); //Declared as instance variable because it must be removed when game is over. 
		this.addKeyListener(_pkl);
		this.setFocusable(true);
		this.grabFocus();
		
		_squareMap = new MazeSquare[23][23];

		/*Setting initial value of the _fright variable to 0. This int will keep track of whether the
		 * game is in normal play (set to 0) or in frightened mode (set to 1).*/
		_fright = 0;

		/*General timer. */
		AnimationTimerListener atl = new AnimationTimerListener(this);
		_timer= new Timer(Constants.TIMESTEP,atl);
		_timer.setInitialDelay(1500); //Game will start after 1.5 seconds have passed. 

		/*SupportMap to initialize map of Squares:*/ 
		_supportMap = cs015.fnl.PacmanSupport.SupportMap.getMap();

		/*Ghost Pen and its timer.*/ 
		_ghostPen = new GhostPen(this); 
		GhostTimerListener gtl = new GhostTimerListener(_ghostPen);
		_penTimer = new Timer (Constants.TIMESTEP, gtl);
		_penTimer.setInitialDelay(1500);
		
		/*Label that appears when all dots and energizers have been eaten (winning the game!)*/
		_winLabel= new JLabel("We have a winner!  "); 
		_winLabel.setForeground(Color.MAGENTA);
		_winLabel.setVisible(false); 
		_dots = 0; //Initial number of dots eaten. 
		_energizers = 0; //Initial number of dots eaten.

		/*Score and Lives labels are instantiated, and their color and text is set.*/ 
		_score=0;  //Initial score. 
		_lives = 3; //Initial number of lives. 
		_scoreLabel= new JLabel("Score:"+ _score + "       "); //The space is for aesthetic purposes.
		_scoreLabel.setForeground(Color.BLACK);
		_livesLabel= new JLabel("Lives:"+ _lives); 
		_livesLabel.setForeground(Color.BLACK);

		/*GameOverLabel (will be setVisible (true) when the GameOver condition is met.) */
		_gameOverLabel= new JLabel("Game Over!!"); 
		_gameOverLabel.setForeground(Color.RED);
		_gameOverLabel.setVisible(false); 
		
		/*4 JLabels are organized in a panel.*/
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		GridLayout gLayout = new GridLayout(1,4); //1 row. 4 column GridLayout. 
		topPanel.setLayout(gLayout);
		topPanel.add(_winLabel);
		topPanel.add(_scoreLabel);
		topPanel.add(_livesLabel);
		topPanel.add(_gameOverLabel);
		this.add(topPanel);

		/* Instantiating Pacman (associated with the GamePanel.)*/
		_pacman = new Pacman(this);

		/*Instantiating ghosts (each one is a subclass of the Ghost class.)*/
		_blinky = new Blinky(this, _pacman);
		_pinky = new Pinky(this, _pacman);
		_inky = new Inky(this, _pacman);
		_clyde = new Clyde(this, _pacman);

		/*Initializing the Map of Squares: */
		for (int col = 0; col < 23; col++) { //For-loop for the columns.
			for (int row = 0; row < 23; row++){ //Nested for-loop for the rows.

				if (_supportMap[col][row]== 0){ //Walls. 
					_squareMap[col][row] = new MazeSquare(); 
					_squareMap[col][row].setColor(Color.BLUE); 
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);
				}

				else if (_supportMap[col][row]==1){ //Empty black squares. 
					_squareMap[col][row]= new MazeSquare(); 
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);
				}

				else if (_supportMap[col][row]==2){ //Black squares with dots. 
					_squareMap[col][row]= new MazeSquare(); 
					_squareMap[col][row].addToList(new Dot(this));
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);

				}
				else if (_supportMap[col][row]==3){ //Black squares with energizers. 
					_squareMap[col][row]= new MazeSquare(); 
					_squareMap[col][row].addToList(new Energizer(this));
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);
				}

				else if (_supportMap[col][row]==4){ //Black square- initial Pacman location. 
					_squareMap[col][row]= new MazeSquare(); 
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);
					_pacman.setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE); 
				}

				else if (_supportMap[col][row]==5){ //Initial ghost location. 
					_squareMap[col][row] = new MazeSquare(); 
					_squareMap[col][row].setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);

					_blinky.setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE-52);
					_inky.setLocation(col*Constants.SQUARE_SIDE-26, row*Constants.SQUARE_SIDE);
					_pinky.setLocation(col*Constants.SQUARE_SIDE-52, row*Constants.SQUARE_SIDE+26);
					_clyde.setLocation(col*Constants.SQUARE_SIDE, row*Constants.SQUARE_SIDE);

					_squareMap[col][row-2].addToList(_blinky);
					_squareMap[col-1][row].addToList(_inky);
					_squareMap[col-2][row+1].addToList(_pinky);
					_squareMap[col][row].addToList(_clyde);
				}

				else{ 
					_squareMap[col][row] = new MazeSquare();
				}
			}
			
			this.setVisible(true);
			
			_penTimer.start();

			_ghostPen.addGhost(_inky); //Add 3 ghosts to GhostPen. 
			_ghostPen.addGhost(_pinky);
			_ghostPen.addGhost(_clyde);

			_timer.start(); //Starting timer after the MazeSquare array has been initialized. 
			this.repaint();
		}
	}


	/*Accesors and mutators for important variables. */

	public MazeSquare[][] getTheMap(){ //SquareMap 
		return _squareMap; 
	}

	public int getFright(){ //Int that determines if game is in normal play(0) or in frightened mode (1). 
		return _fright; 
	}

	public void setFright(int fright){
		_fright = fright; 
	}
	
	public int getDots(){ //Int that determines how many dots have been eaten.  
		return _dots; 
	}

	public void setDots(int dots){
		_dots = dots; 
	}
	
	public int getEnergizers(){ //Int that determines how many dots have been eaten.  
		return _energizers; 
	}

	public void setEnergizers(int energizers){
		_energizers = energizers; 
	}

	public int getScore(){ //Value of the current score. 
		return _score; 
	}

	public void setScore(int score){
		_score = score; 
	}

	public int getLives(){ //Value of the current number of lives. 
		return _lives; 
	}

	public void setLives(int lives){
		_lives =lives; 
	}

	/*Methods that update the Score and Lives labels depending on the current value of _lives and _score.*/

	public void updateLivesLabel(){
		_livesLabel.setText("Lives:" + _lives);
	}

	public void updateScoreLabel(){
		_scoreLabel.setText("Score:" + _score);
	}

	/*Other methods.*/

	public void animatePacman(){ //Moves Pacman and calls on collisions. Is called in timer. 
		_pacman.move();
		this.makeCollision();
	}

	public void makeCollision(){ //Calls make collision in the square where Pacman is currently in. 
		_squareMap[(int)_pacman.getX()/Constants.SQUARE_SIDE][(int)_pacman.getY()/Constants.SQUARE_SIDE].makeCollision();					
	}

	public boolean isAWall(int col, int row){ //Helper method that will return true or false if the index is a wall, by using the support map. 

		if(col<0||col>22||row<0||row>22){ //If out of bounds will be considered as a wall (avoids array out of bounds exceptions). 
			return true;
		}
		else if (_supportMap[col][row] == 0){ 
			return true;
		}
		else{
			return false;
		}
	}
	
	public void winGame(){  
		if (_dots==182 && _energizers==4){ //Checks if all dots and energizers have been eaten with the counter that is incremented with the makeCollision() method. 
			_winLabel.setVisible(true); //Set the winLabel to visible. 
			this.removeKeyListener(_pkl); //Removes keyListener. 
			_penTimer.stop();  //Stops timer. 
			_timer.stop(); //Stops timer. 
			this.collisionGhostNormal();
		}
	}

	public void gameOver(){  
		if (_lives<=0){ //Checks if no lives are left. 
			_gameOverLabel.setVisible(true); //Set the gameOverLabel to visible. 
			this.removeKeyListener(_pkl); //Removes keyListener.
			_penTimer.stop();
			_timer.stop(); //Stop timer. 
			this.collisionGhostNormal();
		}
	}
	
	/*Methods for 2 different types of collisions for the ghosts.*/

	public void collisionGhostFrightened(Ghost ghost){ 
		//For a specific ghost, adds to the pen when colliding in frightened mode. 
		ghost.setNewLoc(11*Constants.SQUARE_SIDE, 10*Constants.SQUARE_SIDE);
		_ghostPen.addGhost(ghost); 
	}

	public void collisionGhostNormal(){ 
		//Method for when pacman collides with ghost in normal mode. 
		//Sets pacman and ghosts back to initial locations.  
		_pacman.setLocation(11*Constants.SQUARE_SIDE,17*Constants.SQUARE_SIDE);
		_blinky.setNewLoc(11*Constants.SQUARE_SIDE, 8*Constants.SQUARE_SIDE);
		_inky.setNewLoc(10*Constants.SQUARE_SIDE, 10*Constants.SQUARE_SIDE);
		_pinky.setNewLoc(9*Constants.SQUARE_SIDE, 11*Constants.SQUARE_SIDE);
		_clyde.setNewLoc(10*Constants.SQUARE_SIDE, 11*Constants.SQUARE_SIDE);
		_ghostPen.addGhost(_inky);
		_ghostPen.addGhost(_pinky);
		_ghostPen.addGhost(_clyde);
	}

	/*Helper methods that set colors of ghosts.*/

	public void setNormalGhostColors(){
		_blinky.setNormalColor();
		_pinky.setNormalColor();
		_inky.setNormalColor();
		_clyde.setNormalColor();
	}

	public void setFrightenedGhostColors(){
		_blinky.setFrightenedColor();
		_pinky.setFrightenedColor();
		_inky.setFrightenedColor();
		_clyde.setFrightenedColor();
	}

	/*Helper methods that update the targets of each mode.*/

	public void updateFrightenedTargets(){
		//Each target is set to a random number inside of the board. 
		_blinky.setFrightenedTarget();
		_pinky.setFrightenedTarget();
		_inky.setFrightenedTarget();
		_clyde.setFrightenedTarget();
	}


	public void updateChaseTargets(){
		//Blinky (red): Pacman 
		//Pinky (pink): 1 right, 4 up from Pacman
		//Inky (blue): 2 down from Pacman
		//clyde (orange): 4 left from Pacman

		_blinky.setChaseTarget();
		_pinky.setChaseTarget();
		_inky.setChaseTarget();
		_clyde.setChaseTarget();
	}

	public void updateScatterTargets(){ 
		//Blinky (red): upper left corner
		//Pinky (pink): upper right corner
		//Inky (blue): lower right corner
		//Clyde (orange): lower left corner

		_blinky.setScatterTarget();
		_pinky.setScatterTarget();
		_inky.setScatterTarget();
		_clyde.setScatterTarget();
	}

	/*Methods for each of the three modes.*/

	public void frightenedMode(){
		this.setFrightenedGhostColors(); 
		this.makeCollision(); 
		this.updateFrightenedTargets(); //Set targets to random places.
		
		//Sets direction of each ghost with the Breadth First search algorithm and then moves the ghost. 
		_blinky.setDirection(_blinky.BreadthFirstSearch()); 
		_blinky.move(); 
		_inky.setDirection(_inky.BreadthFirstSearch()); 
		_inky.move(); 
		_pinky.setDirection(_pinky.BreadthFirstSearch()); 
		_pinky.move(); 
		_clyde.setDirection(_clyde.BreadthFirstSearch()); 
		_clyde.move(); 	
		this.makeCollision();
	}

	public void chaseMode(){ 
		this.setNormalGhostColors(); //Reset colors of ghosts. 
		this.makeCollision(); 
		this.updateChaseTargets();//Set targets related to pacman. 
		
		//Sets direction of each ghost with the Breadth First search algorithm and then moves the ghost. 
		_blinky.setDirection(_blinky.BreadthFirstSearch()); 
		_blinky.move(); 
		_inky.setDirection(_inky.BreadthFirstSearch()); 
		_inky.move(); 
		_pinky.setDirection(_pinky.BreadthFirstSearch()); 
		_pinky.move(); 
		_clyde.setDirection(_clyde.BreadthFirstSearch()); 
		_clyde.move(); 			
		this.makeCollision();
	}

	public void scatterMode(){
		this.setNormalGhostColors(); //Reset colors of ghosts. 
		this.makeCollision(); 
		this.updateScatterTargets(); //Set targets to each corner. 
		
		//Sets direction of each ghost with the Breadth First search algorithm and then moves the ghost. 
		_blinky.setDirection(_blinky.BreadthFirstSearch()); 
		_blinky.move(); 
		_inky.setDirection(_inky.BreadthFirstSearch()); 
		_inky.move(); 
		_pinky.setDirection(_pinky.BreadthFirstSearch()); 
		_pinky.move(); 
		_clyde.setDirection(_clyde.BreadthFirstSearch()); 
		_clyde.move(); 
		this.makeCollision();
	}

	public void paintComponent(Graphics g) { 
		//Method that paints the components graphically. 
		super.paintComponent(g);
		for (int i = 0; i < 23; i++) { //Paint every MazeSquare (its paint() method also paints everything in its ArrayList). 
			for (int j = 0; j < 23; j++){
				_squareMap[i][j].paint(g);
			}
		}
		//Paint all ghosts and Pacman. 
		_blinky.paint(g);
		_pinky.paint(g);
		_inky.paint(g);
		_clyde.paint(g);
		_pacman.paint(g);
	}

	/**Private inner class : PacmanKeyListener. 
	 * 
	 * Implements the interface KeyListener. This class will define the methods that 
	 * will give the user the ability to move Pacman with the keys. It was created inside of the 
	 * GamePanel class so that it can have access to its instance variables, and therefore avoid 
	 * unnecessary and complicated associations.
	 * 
	 * I designed my move() method in Pacman to perform a certain action depending on its current direction
	 * Therefore, what the keyListener does is change the valuee of that current direction, if it is not a wall
	 * (checking the condition in the following way: if the support map is not equal to 0). 
	 * 
	 * Also, the p key will pause and unpause the game. 
	 * 
	 * @author vcano
	 *
	 */

	private class PacmanKeyListener implements java.awt.event.KeyListener{

		public PacmanKeyListener(){ //Constructor. 
		}

		@Override 
		public void keyPressed(KeyEvent e){

			int key = e.getKeyCode();
			/*Important: Pacman is not contained in the ArrayList of the Squares, but it is good to analyze his
			 * position with an index to check the condition of whether the next position is a wall or not.*/

			int currentIndexX = (int)_pacman.getX()/Constants.SQUARE_SIDE; //Get index of current X position of Pacman.
			int currentIndexY = (int)_pacman.getY()/Constants.SQUARE_SIDE; //Get index of current Y position of Pacman.

			if (key == KeyEvent.VK_RIGHT){  //Right arrow: moves Pacman right.
				if (_supportMap[currentIndexX+1][currentIndexY]!=0){
					_pacman.setDirection(Direction.RIGHT);
				} 
			}

			if (key == KeyEvent.VK_LEFT){ //Left arrow: moves Pacman left. 
				if (_supportMap[currentIndexX-1][currentIndexY]!=0){
					_pacman.setDirection(Direction.LEFT); 
				}
			}

			if (key == KeyEvent.VK_DOWN){ //Down arrow: moves Pacman down. 
				if (_supportMap[currentIndexX][currentIndexY+1]!=0)
					_pacman.setDirection(Direction.DOWN); 
			}

			if (key == KeyEvent.VK_UP){ //Up arrow: moves Pacman up.
				if (_supportMap[currentIndexX][currentIndexY-1]!=0)
					_pacman.setDirection(Direction.UP); 
			}

			if (key == KeyEvent.VK_P){ //"p": pauses and unpauses game by stopping and restarting the timers. 
				if (_timer.isRunning()==true){
					_timer.stop();
					_penTimer.stop();
				}
				else{
					_timer.restart();
					_penTimer.restart();
				}
			}
		}

		/* The keyReleased and keyTyped methods do not require any functionality in this program, but since 
		 * the KeyListener interface was implemented they must be stated (even if their body is blank). 
		 */

		@Override 
		public void keyReleased(KeyEvent e){	
			
		}

		@Override 
		public void keyTyped(KeyEvent e){ 
			
		}

	} //class PacmanKeyListener

} //class GamePanel 



