package game;

import environment.Board;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement implements Runnable  {
	private static final int NUM_MOVES=3;
	private static final int OBSTACLE_MOVE_INTERVAL = 400;
	private int remainingMoves=NUM_MOVES;
	private Board board;
	private Cell currentCell; //podemos colocar este atributo aqui?
	private ObstacleMover om;

	public Obstacle(Board board) {
		super();
		this.board = board;
	}

	public ObstacleMover getObstacleMover() {
		return om;
	}

	public Cell getCurrentCell() {
		return currentCell;
	}

	public void setCurrentCell(Cell c) {
		currentCell = c;
	}

	//remaning moves - tem de ser atualizado também (remainingMoves--)
	public int getRemainingMoves() {
		return remainingMoves;
	}

	public void run() {
		ObstacleMover om = new ObstacleMover(this,(LocalBoard) board);
		this.om = om;
		om.start();
	}
}
