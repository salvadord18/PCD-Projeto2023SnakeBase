package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends GameElement implements Serializable{
	protected LinkedList<Cell> cells = new LinkedList<Cell>(); //todas as cells onde a snake está
	protected int size = 1;
	protected int desired_size;
	private int id;
	private Board board;
	//private static int MAX_VALUE = 10

	public Snake(int id,Board board) {
		this.id = id;
		this.board = board;
		desired_size = size;
	}

	public int getSize() {
		return size;
	}

	public void incrementSize() {
		size++;
	}

	//New Method
	public void setDesiredSize(int value) {
		//		if(desired_size + value >= MAX_VALUE)
		//			desired_size = 9;
		//		else desired_size += value;
		//	}
		desired_size += value;
	}

	//Comparar snakes ao tentar mover - ver se é a célula onde quero mover está ocupada por mim
	public int getIdentification() {
		return id;
	}

	// usado para While(getSize() < DELTA) continua a existir jogo
	public int getLength() {
		return cells.size();
	}

	public LinkedList<Cell> getCells() {
		return cells;
	}

	//New method
	public void addCell(Cell c) {
		cells.add(c);
	}

	//New Method
	public void removeCell() {
		if(size == desired_size) {
			cells.getFirst().removeSnake(); // dizer á celula q ja nao esta aliu nenhuma snake bro
			cells.removeFirst(); // remover a celula da lista de celulas da snake, dizer á snake q aquele celula ja n lhe pertence
		}
		else size++;
	}

	protected void move(Cell cell) throws InterruptedException {
		// TODO
	}



	//utilidade? É ter as BoardPositions logo, em vez de as inferir a partir das células onde estou
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	

	//copiar isto para o obstaculo :) [MAS vamos ter de garantir a coisinha da concorrencia :(]
	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		while(cells.isEmpty()) {
			int posX = 0;
			int posY = (int) (Math.random() * Board.NUM_ROWS);
			BoardPosition positionIWant = new BoardPosition(posX, posY);
			Cell cellIWant = board.getCell(positionIWant);

			System.out.println("eu, snake nº " + id + " , vou ver se a célula " + cellIWant.getPosition().toString() + " está empty");

			if(!cellIWant.isOccupied()) {
				try {
					cellIWant.request(this);
					addCell(cellIWant);
					cellIWant.release();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			else
				System.out.println("Oh não! Estava ocupada... :( ");
		}	
	}

	public Board getBoard() {
		return board;
	}


}
