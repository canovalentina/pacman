package Pacman;

import javax.swing.JFrame;

/**
 * This is the main class where your Pacman application will start.
 * Note that this is an application, where the main method calls 
 * the App constructor.  When you fill in the constructor, you
 * will need to instantiate your Pacman game.
 *
 * Welcome to my Pacman!! 
 * 
 * *Talk about design choice* Most of my objects are associated with the GamePanel. 
 * 
 *  In the App class (the upper-level class), I create a JFrame, and a MainPanel 
 *  (and add it to the frame), along with other important methods. 
 *
 * @author <vcano>
 * Did you discuss your design with another student? No. 
 * If so, list their login here:
 *
 */

public class App {

	public App() {	

			//Constructor 
			//Instantiation of MainPanel and JFrame (and set visible). 

			JFrame frame = new JFrame("PACMAN");

			MainPanel panel = new MainPanel();
			frame.add(panel); //panel graphically contained in frame. 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.pack();
			frame.setVisible(true);
		}

	/* You don't need to touch this part. */
	public static void main(String[] argv) {
		new App();
	}
	
} //class App 
