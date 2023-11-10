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
	private Snake ocuppyingSnake = null;
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
	public boolean isOcupiedBySnake() {
		return ocuppyingSnake != null;
	}

	//Atribui o GameElement à célula - a ser usado sempre 
	public  void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		if(isOcupiedByGoal() && element instanceof Snake) {
			((Snake)element).setDesiredSize(getGoal().getValue());
			getGoal().captureGoal();
		}
		if(element instanceof Snake)
			ocuppyingSnake = (Snake)element;
		gameElement=element;
	}

	//Se está ocupada
	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
	}

	//Que snake está na célula (PODE DEVOLVER NULL)
	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public  Goal removeGoal() {
		// TODO
		return null;
	}
	public void removeObstacle() {
		//TODO
	}

	//nada
	public void removeSnake() {
		ocuppyingSnake = null;
		gameElement = null;
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}



}
