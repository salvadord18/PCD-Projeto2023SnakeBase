package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell {
	private BoardPosition position;
	private Snake occupyingSnake = null;
	private GameElement gameElement = null;
	private Lock lock;

	//devolve o GameElement que está na Cell - PODE DEVOLVER NULL
	public GameElement getGameElement() {
		return gameElement;
	}

	public Cell(BoardPosition position) {
		super();
		this.position = position;
		lock = new ReentrantLock();
	}

	//devolve a BoardPosition da Cell - MUITO ÚTIL
	public BoardPosition getPosition() {
		return position;
	}

	//Invocado pela Snake, pede para entrar dentro da célula SE A CÉLULA JA NAO TIVER LÁ ALGUÉM ANTES 
	public void request(Snake snake)
			throws InterruptedException {
		lock.lock();
		//TODO coordination and mutual exclusion
		//TODO NAO ESQUECER QUE QUANDO AS SNAKES SE MOVEREM, TÊM DE SER RETIRADAS DA CELL!
	}

	//Invocado para libertar (célula.unlock()) a célula
	public void release() {
		lock.unlock();
		//TODO
	}

	//Boolean que diz se é snake o GameElement da célula
	public boolean isOccupiedBySnake() {
		return occupyingSnake != null;
	}

	//Atribui o GameElement à célula - a ser usado sempre 
	public void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		if(isOccupiedByGoal() && element instanceof Snake) {
			((Snake)element).setDesiredSize(getGoal().getValue());
			getGoal().captureGoal();
			gameElement = element;
		}
		if(element instanceof Snake) {
			occupyingSnake = (Snake)element;
			gameElement = element;
		}
		gameElement = element;
	}

	//Se está ocupada
	public boolean isOccupied() {
		return isOccupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
	}

	//Que snake está na célula (PODE DEVOLVER NULL)
	public Snake getOccupyingSnake() {
		return occupyingSnake;
	}


	public Goal removeGoal() {
		// TODO
		return null;
	}
	public void removeObstacle() {
		if(gameElement instanceof Obstacle)
			gameElement = null;
	}

	//nada
	public void removeSnake() {
		occupyingSnake = null;
		gameElement = null;
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOccupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}
}
