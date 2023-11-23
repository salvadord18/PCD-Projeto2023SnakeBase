package game;

import environment.Board;
import environment.Cell;
import environment.LocalBoard;

public class Obstacle extends GameElement {
	private static final int NUM_MOVES=3;
	private int remainingMoves=NUM_MOVES;
	public static int MOVE_INTERVAL = 500;
	private Board board;
	private Cell currentCell; //podemos colocar este atributo aqui?
	private ObstacleMover om;

	public Obstacle(Board board) {
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

	public void move() {
		currentCell.removeGameElement();	
		board.addGameElement(this);
		remainingMoves--;
		board.setChanged();
	}
}
