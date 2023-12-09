package environment;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import game.*;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake occupyingSnake = null;
	private GameElement gameElement = null;
	public Lock lock = new ReentrantLock();
	public Condition isEmpty  = lock.newCondition();
	private Board board;


	public Cell(BoardPosition position, Board board) {
		this.position = position;
		this.board = board;
	}


	//devolve o GameElement que está na Cell - PODE DEVOLVER NULL
	public GameElement getGameElement() {
		return gameElement;
	}


	//devolve a BoardPosition da Cell - MUITO ÚTIL
	public BoardPosition getPosition() {
		return position;
	}
	
	public void setPosition(BoardPosition newPosition) {
		this.position = newPosition;
	}

	//Invocado pela Snake, pede para entrar dentro da célula SE A CÉLULA JA NAO TIVER LÁ ALGUÉM ANTES 
	public void request(Snake snake)
			throws InterruptedException {
		//lock.lock();
		//TODO coordination and mutual exclusion
	}

	//Invocado para libertar (célula.unlock()) a célula
	public void release() {
		//lock.unlock();
		//TODO
	}

	//Boolean que diz se é snake o GameElement da célula
	public boolean isOccupiedBySnake() {
		if((gameElement != null && gameElement instanceof Snake) || occupyingSnake != null)
			return true;
		return false;
	}

	public void snakeMovesTo(Cell nextCell) {
		lock.lock();
		try {			
			//2ª Possibilidade - Proxima celula estar ocupada por snake ou obstacle
			while(nextCell.isOccupied() && !nextCell.isOccupiedByGoal() && occupyingSnake instanceof AutomaticSnake) {
				System.out.println("awaitinggggggggggggggggggggggggggggggg");
				isEmpty.await();
				System.out.println("done awaittttttinggggggg");
				((AutomaticSnake)occupyingSnake).choosingABetterCell(this);
				return;

			}
			//1ª Possibilidade - proxima celula estar livre ou ter um Goal
			//if(!nextCell.isOccupied() || nextCell.isOccupiedByGoal()) {
				nextCell.lock.lock();
				try {
					if(nextCell.isOccupiedByGoal()) {
						getOccupyingSnake().setDesiredSize(nextCell.getGoal().getValue());
						nextCell.getGoal().captureGoal();
						if(nextCell.getGoal().getValue() == 10) {
							getOccupyingSnake();
							Snake.terminarJogo();
							System.out.println("Fim do jogo!");
						}
					}
					System.out.println(getOccupyingSnake().toString());
					nextCell.setGameElement(getOccupyingSnake());
					//nextCell.getOccupyingSnake().addCell(nextCell);
					nextCell.getOccupyingSnake().getCells().getFirst().removeGameElement(); // dizer á celula q ja nao esta aliu nenhuma snake bro
					nextCell.getOccupyingSnake().removeTailCell();
				} finally {
					nextCell.lock.unlock();
				} 
			//}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		board.setChanged();
	}


	//Atribui o GameElement à célula - a ser usado sempre 
	public void setGameElement(GameElement element) { //melhorar este método
		lock.lock();
		try {
			gameElement = element;
			if(element instanceof Snake) {
				occupyingSnake = (Snake)element;
				occupyingSnake.addCell(this);
			}
		} finally {
			lock.unlock();
		} 
		board.setChanged();
	}

	//Se está ocupada
	public boolean isOccupied() {
		return isOccupiedBySnake() || (gameElement != null);
	}

	//Que snake está na célula (PODE DEVOLVER NULL)
	public Snake getOccupyingSnake() {
		if(occupyingSnake == null)
			return null; //desnecessário
		return occupyingSnake;
	}

	public void removeGameElement() throws InterruptedException {
		lock.lock();
		try {
			if(gameElement instanceof Snake) {
				//occupyingSnake.removeTailCell();
				if(occupyingSnake.getSize() != occupyingSnake.getDesiredSize()) {
					return;
				}
				occupyingSnake = null;
			}
			gameElement = null;
			isEmpty.signal();// para todos os q estao a espera de se moverem para esta celula recebrem um alerta de q ficou free
		System.out.println("sai da celula aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		} finally {
			lock.unlock();
		}
		board.setChanged();
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}

	public Obstacle getObstacle() {
		return (Obstacle)gameElement;
	}


	public boolean isOccupiedByGoal() {
		return (gameElement != null && gameElement instanceof Goal);
	}
}
