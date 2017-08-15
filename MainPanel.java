package Pacman;

import java.awt.Dimension;
import javax.swing.*;

/**
 * In this program, the MainPanel simply adds a GamePanel, and sets a size a bit ;larger than the GamePanel.
 * 
 * Although this class does not seem necessary it is a good idea to have the GamePanel instantiated in this
 * upper-level class, in case I wanted to add any extra functionality or extra panels to the program, and to avoid bugs.
 * 
 * @author vcano
 *
 */

public class MainPanel extends JPanel {
	
	public MainPanel(){ //Constructor. 
		
		super();
		Dimension panelSize = new Dimension(Constants.SQUARE_SIDE*24, Constants.SQUARE_SIDE*24); 
		this.setPreferredSize(panelSize); //set Size 
		
		GamePanel gamePanel = new GamePanel(); //Instantiates GamePanel. 
		this.add(gamePanel);
		this.setVisible(true);	
	}
	
} //class MainPanel
