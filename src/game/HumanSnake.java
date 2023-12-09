package game;

import environment.Board;
import java.awt.event.KeyEvent;
import environment.BoardPosition;
import environment.Cell;
/** Class for a remote snake, controlled by a human 
 * 
 * @author luismota
 *
 */
public class HumanSnake extends Snake {

	private static final long serialVersionUID = 1L;

	public HumanSnake(int id,Board board) {
		super(id,board);
		super.doInitialPositioning();
	}

	public void move(int keyCode) {
		if(!getBoard().isFinished) {
			Cell currcell = super.getCells().getLast(); /* vai buscar a celula atual */
			
			BoardPosition newPos = null;
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				newPos = currcell.getPosition().getCellLeft();
				break;
			case KeyEvent.VK_RIGHT:
				newPos = currcell.getPosition().getCellRight();
				break;
			case KeyEvent.VK_UP:
				newPos = currcell.getPosition().getCellAbove();
				break;
			case KeyEvent.VK_DOWN:
				newPos = currcell.getPosition().getCellBelow();
				break;
			default:
				System.err.println("Key event errado!");
				return;
			}
			
			System.out.println("Posição: " + newPos.toString());
			if(!super.getBoard().isViablePosition(newPos))
				return;

			Cell nova = super.getBoard().getCell(newPos);

			if(nova.isOccupied() && !nova.isOccupiedByGoal())
				return;

			currcell.snakeMovesTo(nova);

			getBoard().setChanged();

			System.out.println("Posição nova " + nova.toString() + " da Snake " + this.getIdentification());
		}
	}

}
