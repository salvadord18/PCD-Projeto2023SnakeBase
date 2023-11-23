package environment;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.ObstacleMover;
import game.Server;
import game.Snake;
import game.AutomaticSnake;

/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board {

	private static final int NUM_SNAKES = 2;
	private static final int NUM_OBSTACLES = 5;
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
