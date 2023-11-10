package game;

import environment.Board;
import environment.LocalBoard;

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
	public void incrementValue() throws InterruptedException {
		//TODO
		if(value < MAX_VALUE)
			value++;
	}

	//... ou então fazemos o goal "que morre" e um novo é adicionado
	public void captureGoal() {
		//TODO
		try {
			incrementValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.addGameElement(this);
		board.setChanged();
	}
}
