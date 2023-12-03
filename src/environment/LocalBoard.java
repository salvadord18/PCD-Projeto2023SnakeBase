package environment;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.*;

/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board {

	private static final int NUM_SNAKES = 3;
	private static final int NUM_OBSTACLES = 0;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 2;	

	public LocalBoard() {
		addGoal();
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}
		//System.err.println("All elements placed");
	}

	public void init() {
		for(Snake s : snakes) {
			Thread aux = new Thread((AutomaticSnake)s);
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
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}





}
