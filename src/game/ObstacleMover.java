package game;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;

	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	//    @Override
	//    public void run() {
	//        while(true) { // while !isGameOver
	//            Cell currentCell = obstacle.getCurrentCell();
	//            List<BoardPosition> neighboringPositions = board.getNeighboringPositions(currentCell);
	//
	//            // Filtra as posições que não estão ocupadas
	//            neighboringPositions.removeIf(pos -> board.getCell(pos).isOccupied());
	//
	//            if (!neighboringPositions.isEmpty()) {
	//                // Escolhe uma posição aleatória das posições disponíveis
	//                BoardPosition newPosition = neighboringPositions.get(new Random().nextInt(neighboringPositions.size()));
	//                Cell newCell = board.getCell(newPosition);
	//                
	//                // Move o obstáculo para a nova célula
	//                synchronized (board) { // Garantir a sincronização se estiver usando múltiplas threads
	//                	newCell.setGameElement(obstacle); // Coloca o obstáculo na nova célula
	//                	obstacle.setCurrentCell(newCell); // Atualiza a célula atual do obstáculo
	//                	currentCell.removeObstacle(); // Remove o obstáculo da célula atual
	//                
	//                    
	//                }
	//            }
	//
	//            board.setChanged();
	//            try {
	//                sleep(2000); // Intervalo antes do próximo movimento
	//            } catch (InterruptedException e) {
	//                e.printStackTrace();
	//            }
	//        }
	//    }
	@Override
	public void run() {
		while(obstacle.getRemainingMoves() > 0 && !Snake.isjogoTerminado()) {
			obstacle.move();
			try {
				sleep(Obstacle.MOVE_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}

