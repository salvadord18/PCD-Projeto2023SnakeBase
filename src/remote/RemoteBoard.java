package remote;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.Direction;
import game.Goal;
import game.HumanSnake;
import game.Obstacle;
import game.ObstacleMover;
import game.Snake;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Srver.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	
	private static final int NUM_SNAKES = 2;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;	

	public RemoteBoard() {
		addGoal();
		for (int i = 0; i < NUM_SNAKES; i++) {
			HumanSnake snake = new HumanSnake(i, this);
			snakes.add(snake);
		}
		//System.err.println("All elements placed");
	}

	public void init2() {
		for(Snake s : snakes) {
			Thread aux = new Thread((HumanSnake)s);
			aux.start();
		}

		// TODO: launch other threads (OBSTACULOS)
		ExecutorService executor = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
		addObstacles(NUM_OBSTACLES);
		for(Obstacle o : super.getObstacles()) {
			ObstacleMover om = new ObstacleMover(o, this);
			executor.submit(om);
		}
		executor.shutdown();

		setChanged();
	}

	
	@Override
	public void handleKeyPress(int keyCode) {
        switch (keyCode) {
        case KeyEvent.VK_UP:
            //humanSnake.changeDirection(Direction.UP);
            break;
        case KeyEvent.VK_DOWN:
            //humanSnake.changeDirection(Direction.DOWN);
            break;
        case KeyEvent.VK_LEFT:
            //humanSnake.changeDirection(Direction.LEFT);
            break;
        case KeyEvent.VK_RIGHT:
            //humanSnake.changeDirection(Direction.RIGHT);
            break;
    }
    }

	@Override
	public void handleKeyRelease() {
		// TODO
	}

	@Override
	public void init() {
		// TODO 		
	}


	

}
