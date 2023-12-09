package game;

import java.util.List;
import environment.LocalBoard;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake implements Runnable {
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board); //cada snake tem um ID (podemos ver se é a propria pelo ID) e o Board onde está
	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:" + cells.size());

		while(!super.getBoard().isFinished) { //mudar para !isFinished
			try {
				move();
				Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//TODO: automatic movement
			//getrandomcell
			//request that cell
		}
		//super.getBoard().finish();
	}

	public Thread SnakeThread () {
		return Thread.currentThread();
	}

	public synchronized void move() throws InterruptedException {
		Cell currcell = super.getCells().getLast(); // vai buscar a célula da cabeça atual
		if(currcell == null) return;
		BoardPosition position = currcell.getPosition(); // vai buscar a posição atual dessa célula
		System.out.println("Posição atual " + position + " da Snake " + this.getIdentification());

		Cell ParaOndeMeVouMover = choosingNewCell(currcell);

		currcell.snakeMovesTo(ParaOndeMeVouMover);

		super.getBoard().setChanged();

		System.out.println("Posição nova " + ParaOndeMeVouMover + " da Snake " + this.getIdentification());
	}

	public synchronized void wakeup() throws InterruptedException {
		Thread.currentThread().interrupt();

	}

	public Cell choosingNewCell(Cell currcell) {
		List<BoardPosition> neighboringPositions = super.getBoard().getNeighboringPositions(currcell);

		//Lambda Expression ( :( ) que remove todas as posições que tenham como snake a própria
		neighboringPositions.removeIf(pos -> this.equals(getBoard().getCell(pos).getOccupyingSnake()));
		if(neighboringPositions.isEmpty())
			Thread.currentThread().stop(); 		//porquê stop? porque ela já não terá mesmo para onde se mover.

		/* Escolher a célula mais perto do Goal de entre as vizinhas */
		BoardPosition ParaOndeMeVouMover = null;
		double min_distance = 9999.0; //random value e grande para a condiçao se manter verdadeira

		for(BoardPosition pos : neighboringPositions) {
			if(pos.distanceTo(super.getBoard().getGoalPosition()) < min_distance) {
				min_distance = pos.distanceTo(super.getBoard().getGoalPosition());
				ParaOndeMeVouMover = pos;
			}
		}
		Cell nova = super.getBoard().getCell(ParaOndeMeVouMover);

		return nova;
	}

	//mudar
	public void choosingABetterCell(Cell currcell) {
		List<BoardPosition> neighboringPositions = super.getBoard().getNeighboringPositions(currcell);

		//Lambda Expression ( :( ) que remove todas as posições que tenham como snake a própria
		neighboringPositions.removeIf(pos -> (this.equals(getBoard().getCell(pos).getOccupyingSnake()) || (getBoard().getCell(pos).isOccupied() && !getBoard().getCell(pos).isOccupiedByGoal())));
		if(neighboringPositions.isEmpty())
			return; 		//porquê stop? porque ela já não terá mesmo para onde se mover.

		/* Escolher a célula mais perto do Goal de entre as vizinhas */
		BoardPosition ParaOndeMeVouMover = null;
		double min_distance = 9999.0; //random value e grande para a condiçao se manter verdadeira

		for(BoardPosition pos : neighboringPositions) {
			if(pos.distanceTo(super.getBoard().getGoalPosition()) < min_distance) {
				min_distance = pos.distanceTo(super.getBoard().getGoalPosition());
				ParaOndeMeVouMover = pos;
			}
		}

		Cell nova = super.getBoard().getCell(ParaOndeMeVouMover);
		System.out.println(currcell + ", " + nova);

		currcell.snakeMovesTo(nova);

		super.getBoard().setChanged();

		System.out.println("Posição nova " + ParaOndeMeVouMover + " da Snake " + this.getIdentification());
	}
}
