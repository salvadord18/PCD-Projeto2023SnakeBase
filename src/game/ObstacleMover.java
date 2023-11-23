package game;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) { // the one and only ThreadPool :)
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		while(true){// while !isGameOver
			Cell c = obstacle.getCurrentCell();
			board.addGameElement(obstacle);
			c.removeObstacle();
			board.setChanged();
			try {
				sleep(2000); //nova Constante no Board OU playerplayinterval?
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
