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
	public synchronized void setGameElement(GameElement element) { //melhorar este método
		// TODO coordination and mutual exclusion
		while(isOccupied() && element instanceof Snake && !isOccupiedByGoal())
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!isOccupied()) {
			notifyAll();
			if(element instanceof Goal)
				gameElement = element;
			else if(element instanceof Snake && isOccupiedByGoal()) {
				((Snake)element).setDesiredSize(getGoal().getValue());
				gameElement = element;
			}
			else if(element instanceof Snake) {
				occupyingSnake = (Snake)element;
				gameElement = element;
			}
			if(element instanceof Obstacle && !isOccupiedByGoal())
				gameElement = element;

		}
	}

	//Se está ocupada
	public boolean isOccupied() {
		return isOccupiedBySnake() || (gameElement != null && gameElement instanceof Obstacle);
	}

	//Que snake está na célula (PODE DEVOLVER NULL)
	public Snake getOccupyingSnake() {
		return occupyingSnake;
	}

	public void removeGameElement() {
		if(gameElement instanceof Snake)
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
