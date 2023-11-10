package game;

import java.util.Iterator;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
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

		while(super.size < Board.MAXPOINTS) {
			try {
				move();
				Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO: automatic movement
			//getrandomcell
			//request that cell
		}
	}

	public Thread SnakeThread () {
		return Thread.currentThread();
	}

	public synchronized void move() throws InterruptedException {
		Cell currcell = super.getCells().getLast(); // vai buscar a célula atual
		if(currcell == null) return;
		BoardPosition position = currcell.getPosition(); // vai buscar a posição atual da célula
		System.out.println("Posição atual " + position + " do " + this.toString());

		List<BoardPosition> neighbors = super.getBoard().getNeighboringPositions(currcell);

		//		Iterator it = neighbors.iterator();
		//		for (BoardPosition p : neighbors) {
		//			if(this.equals(getBoard().getCell(p).getOcuppyingSnake())) {  // verifica se onde a snake quer ir está ocuapdo por ela mesma
		//				it.remove(); //remove todas as células das "CELULAS POSSIVEIS DE SE MOVER PARA" nas quais eu ja esteja.
		//			}
		//			it.next();
		//		}

		//Lambda Expression ( :( ) que remove todas as posições que tenham como snake a própria
		neighbors.removeIf(pos -> this.equals(getBoard().getCell(pos).getOcuppyingSnake()));

		if(neighbors.isEmpty())
			Thread.currentThread().stop(); 		//porquê stop? porque ela já não terá mesmo para onde se mover.


		/* linha que diferencia a AutomaticSnake da Human */
		BoardPosition ParaOndeMeVouMover = null;
		double min_distance = 9999.0; //random value

		for(BoardPosition pos : neighbors) {
			if(pos.distanceTo(super.getBoard().getGoalPosition()) < min_distance) {
				min_distance = pos.distanceTo(super.getBoard().getGoalPosition());
				ParaOndeMeVouMover = pos;
			}
		}

		//BoardPosition ParaOndeMeVouMover = neighbors.get((int)(Math.random()*(neighbors.size())));

		Cell nova = super.getBoard().getCell(ParaOndeMeVouMover);

		if(nova.isOcupied() && !nova.isOcupiedByGoal())
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("Saí da espera ----------------------------------------");
			}
		currcell.request(this);
		nova.request(this);
		nova.setGameElement(this);
		addCell(nova);
		super.removeCell();
		currcell.release();
		nova.release();

		super.getBoard().setChanged();

		System.out.println("Posição nova " + position + " do " + this.toString());
	}

	public synchronized void wakeup() throws InterruptedException {
		notifyAll();
	}

}
