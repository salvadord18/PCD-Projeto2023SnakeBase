package game;

import environment.Board;

public class Goal extends GameElement {
	private int value = 1;
	private Board board;
	public static final int MAX_VALUE = 10;

	public Goal(Board board) {
		this.board = board;
	}

	//Valor do Goal - usado para incrementar o size da cobra
	public int getValue() {
		return value;
	}

	//ou fazemos a incrementar o valor de 1 em 1...
	public void incrementValue() {
		if(value < MAX_VALUE)
			value++;
	}

	//... ou então fazemos o goal "que morre" e um novo é adicionado
	public synchronized void captureGoal() {
		if(value == MAX_VALUE-1) {
			board.finish();
			return;
		}
		board.addGameElement(this);   
		incrementValue();	
		board.setChanged();
	}
}


