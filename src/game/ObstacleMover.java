package game;

import environment.Cell;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
    private static final Object lock = new Object(); // Objeto para sincronização
    private static int movingCount = 0; // Contador de obstáculos em movimento
    private static final int MAX_CONCURRENT_MOVES = 3; // Número máximo de obstáculos movidos simultaneamente

    private Obstacle obstacle;
    private LocalBoard board;

    public ObstacleMover(Obstacle obstacle, LocalBoard board) { // the one and only ThreadPool :)
        super();
        this.obstacle = obstacle;
        this.board = board;
    }

    @Override
    public void run() {
        while (true) { // while !isGameOver
            synchronized (lock) {
                while (movingCount >= MAX_CONCURRENT_MOVES) {
                    try {
                        // Aguarda se já existirem MAX_CONCURRENT_MOVES obstáculos em movimento
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                movingCount++; // Incrementa o contador de obstáculos em movimento
            }

            // Lógica de movimento do obstáculo
            moveObstacle();

            synchronized (lock) {
                movingCount--; // Decrementa o contador de obstáculos em movimento
                lock.notifyAll(); // Notifica todas as threads
            }
        }
    }

    private void moveObstacle() {
        Cell c = obstacle.getCurrentCell();
        board.addGameElement(obstacle);
        c.removeObstacle();
        board.setChanged();

        try {
            sleep(2000); // Nova Constante no Board OU playerplayinterval?
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
