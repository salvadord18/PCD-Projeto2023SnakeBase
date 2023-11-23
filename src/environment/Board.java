package environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;

public abstract class Board extends Observable {
	public Cell[][] cells; //todas as células existentes no Board, por ordem.
	private BoardPosition goalPosition; //posição atual do único Goal no Board.
	public static final long PLAYER_PLAY_INTERVAL = 900; //?
	public static final long OBSTACLE_MOVE_INTERVAL = 1000;
	public static final long REMOTE_REFRESH_INTERVAL = 100; //not necessary
	public static final int NUM_COLUMNS = 10; //Largura do Board
	public static final int NUM_ROWS = 10; //Comprimento do Board
	public static final int MAXPOINTS = 3; //Condicao de término do jogo
	protected LinkedList<Snake> snakes = new LinkedList<Snake>(); //todas as Snakes no Board
	private LinkedList<Obstacle> obstacles= new LinkedList<Obstacle>(); //todos os Obstáculos no Board
	public boolean isFinished; //Boolean para definir se nenhuma cobra ganhou (ainda)
	private ExecutorService ex = Executors.newFixedThreadPool(3); //onde implementar?


	/*
	 * Cells - mover cobras, mover obstaculos, ...
	 * goalPosition - movimentação das cobras automáticas 
	 * NUM_Columns & NUM_Rows para saber se as movimentações são para dentro do Board - delimitar
	 * Snakes - 
	 * Obstacles -
	 * */

	public Board() {
		cells = new Cell[NUM_COLUMNS][NUM_ROWS];
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				cells[x][y] = new Cell(new BoardPosition(x, y));
			}
		}
		//		Thread gameOver = new Thread() {
		//			@Override 
		//			public void run() {
		//				while(!isFinished) {
		//					
		//				}
		//				// interrompe as threads que correm os jogadores
		//				for(Obstacle o : obstacles){
		//					o.getObstacleMover().stop();
		//				}
		//				//isFinished = true;
		//				System.err.println("GAME FINISHED!");
		//			};
		//		};
		//		gameOver.start();
	}

	//Devolve a célula que se encontra na Board Position cellCoord
	public Cell getCell(BoardPosition cellCoord) {
		return cells[cellCoord.x][cellCoord.y];
	}
	
	public LinkedList<Cell> allCells() {
		LinkedList<Cell> Allcells = new LinkedList<>();
		for (int x = 0; x < NUM_COLUMNS; x++) {
			for (int y = 0; y < NUM_ROWS; y++) {
				Allcells.add(cells[x][y]);
			}
		}
		return Allcells;
	}

	//Devolve uma BoardPosition aleatória (para os Obstáculos e para o Goal) - protected?
	protected BoardPosition getRandomPosition() {
		return new BoardPosition((int) (Math.random() * NUM_ROWS),(int) (Math.random() * NUM_ROWS));
	}

	//Devolve a BoardPosition onde consta o Goal (para as cobras automáticas)
	public BoardPosition getGoalPosition() {
		return goalPosition;
	}

	//Recebe uma BoardPosition que há de ser gerada pelo getRandomPosition() e atualiza o atributo GoalPosition
	public void setGoalPosition(BoardPosition goalPosition) {
		this.goalPosition = goalPosition;
	}

	//Colocação inicial dos players & novos Goals
	public synchronized void addGameElement(GameElement gameElement) {
		boolean placed=false;
		while(!placed) {
			BoardPosition pos=getRandomPosition();
			if(!getCell(pos).isOccupied()) {
				if(gameElement instanceof Goal) {
					setGoalPosition(pos);
					System.out.println("Goal placed at: " + pos + "! :D");
				}
				else if(gameElement instanceof Obstacle) {
					((Obstacle)gameElement).setCurrentCell(getCell(pos));
				}
				getCell(pos).setGameElement(gameElement);
				placed=true;
			}
		}
		setChanged();
	}

	// Devolve uma lista das BoardPositions vizinhas - verifica APENAS se estão dentro do board
	// NOTA: esta lista nem sempre vai ter tamanho 4!
	public List<BoardPosition> getNeighboringPositions(Cell cell) {
		ArrayList<BoardPosition> possibleCells=new ArrayList<BoardPosition>();
		BoardPosition pos=cell.getPosition();
		if(pos.x>0)
			possibleCells.add(pos.getCellLeft());
		if(pos.x<NUM_COLUMNS-1)
			possibleCells.add(pos.getCellRight());
		if(pos.y>0)
			possibleCells.add(pos.getCellAbove());
		if(pos.y<NUM_ROWS-1)
			possibleCells.add(pos.getCellBelow());
		return possibleCells;
	}


	// função que cria o novo Goal sozinha! -> onde está o notifyChange()?
	protected Goal addGoal() {
		Goal goal = new Goal(this);
		addGameElement(goal);
		return goal;
	}

	// função que cria no início do jogo os obstáculos (que nunca morrem) -> onde está o notifyChange()?
	protected void addObstacles(int numberObstacles) {
		// clear obstacle list , necessary when resetting obstacles.
		getObstacles().clear(); //obstacles.clear
		while(numberObstacles>0) {
			Obstacle obs=new Obstacle(this);
			addGameElement(obs);
			getObstacles().add(obs);
			numberObstacles--;
		}
		setChanged();
	}

	// todas as snakes que existem no Board
	public LinkedList<Snake> getSnakes() {
		return snakes;
	}

	//notifyChange
	@Override
	public void setChanged() {
		super.setChanged();
		notifyObservers();
	}

	// todos os obstaculos
	public LinkedList<Obstacle> getObstacles() {
		return obstacles;
	}


	public abstract void init(); 

	public abstract void handleKeyPress(int keyCode);

	public abstract void handleKeyRelease();



	// Adiciona a snake ao vetor de snakes do Board
	public void addSnake(Snake snake) {
		snakes.add(snake);
	}

	public void finish() {
		isFinished = true;
	}
	//	  public void endGame() {
	//	        isFinished = true; // Sinaliza que o jogo terminou
	//	        Thread endGameThread = new Thread(() -> {
	//	            for (Snake snake : snakes) {
	//	                snake.stopMoving(); // Método para interromper a movimentação da cobra
	//	            }
	//	            for (Obstacle obstacle : obstacles) {
	//	                obstacle.getObstacleMover().interrupt(); // Sinaliza para interromper a movimentação dos obstáculos
	//	            }
	//	        });
	//	        endGameThread.start();;
	//	  }
}