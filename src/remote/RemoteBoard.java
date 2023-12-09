package remote;

import java.io.IOException;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board {
	//private PrintWriter ...;
	private Client client;
	
	public RemoteBoard() {
		super();
	}
	
	@Override
	public void init() {
		
	}
}
