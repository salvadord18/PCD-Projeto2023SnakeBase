package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import environment.Direction;

/** Class for a remote snake, controlled by a human 
 * 
 * @author luismota
 *
 */
public class HumanSnake extends Snake implements Runnable {

	public Direction currentDirection;
	
	public HumanSnake(int id,Board board) {
		super(id,board);
	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:" + cells.size());

		while(!jogoTerminado) { //mudar para !isFinished
			try {
				move();
				Thread.sleep(Board.PLAYER_PLAY_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public Thread SnakeThread () {
		return Thread.currentThread();
	}
	
	public synchronized void move() throws InterruptedException {
		Cell currcell = super.getCells().getLast(); // vai buscar a célula da cabeça atual
		if(currcell == null) return;
		BoardPosition currentPosition = currcell.getPosition(); // vai buscar a posição atual dessa célula
		System.out.println("Posição atual " + currentPosition + " da Snake " + this.getIdentification());

        BoardPosition newPosition;
		
		switch (currentDirection) {
        case UP:
            newPosition = currentPosition.getCellAbove();
            break;
        case DOWN:
            newPosition = currentPosition.getCellBelow();
            break;
        case LEFT:
            newPosition = currentPosition.getCellLeft();
            break;
        case RIGHT:
            newPosition = currentPosition.getCellRight();
            break;
        default:
            // Trata um caso padrão ou erro
            newPosition = currentPosition;
    }

		Cell ParaOndeMeVouMover = null;
		ParaOndeMeVouMover.setPosition(newPosition);
		currcell.snakeMovesTo(ParaOndeMeVouMover);

		super.getBoard().setChanged();

		System.out.println("Posição nova " + ParaOndeMeVouMover + " da Snake " + this.getIdentification());
	}

	public synchronized void wakeup() throws InterruptedException {
		Thread.currentThread().interrupt();

	}
	
	public void changeDirection(Direction newDirection) {
        this.currentDirection = newDirection;
    }
}
