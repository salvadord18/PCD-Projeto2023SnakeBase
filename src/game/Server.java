package game;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import environment.Board;
import remote.RemoteBoard;

public class Server {
	private ServerSocket server;
	protected Board board;

	public Server(Board board) {
		this.board = board;
	}

	private void runServer() {
		try {
			// 1. Create socket
			server = new ServerSocket(12341, 51);
			while(true) {
				// 2. Wait for a new connection
				waitForConnection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private void waitForConnection() throws IOException {
		System.out.println("Waiting for a new connection...");
		Socket connection = server.accept(); // WAIT!!

		// Create a connection manager (ConnectionHandler)
		ConnectionHandler handler = new ConnectionHandler(connection);
		handler.start();
		System.out.println("[new connection] " + connection.getInetAddress().getHostName());
	}

	private class ConnectionHandler extends Thread {
		private Socket connection;
		private Scanner in;
		private PrintWriter out;

		public ConnectionHandler(Socket connection) {
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				getStreams();
				processConnection();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}


		private void getStreams() throws IOException {
			// Writer
			out = new PrintWriter(connection.getOutputStream(), true);
			// Reader
			in = new Scanner(connection.getInputStream());
			System.out.println("[ready to proceed connection]");
		}

		private void processConnection() {
			HumanSnake human = new HumanSnake(28, board);
			while(!board.isFinished) {
				if(in.hasNextInt()) {
					int s = in.nextInt();
					human.move(s);
				}
			}
		}
		//			String msg;
		//			do {
		//				// Read message
		//				msg = in.nextLine(); // WAIT!!
		//				System.out.println("[received] " + msg);
		//				// Write echo
		//				out.println("[server echo] " + msg);			
		//
		//			} while(!"END".equals(msg));
		//
		//		}

		private void closeConnection() {
			try {
				if(in != null)
					in.close();
				if(out != null)
					out.close();
				if(connection != null)
					connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}

	public static void main(String[] args) {
		Server serverApp = new Server(new RemoteBoard());
		serverApp.runServer();
	}
}

// TODO

//faz a conexao, cria a humansnake com posicao no jogo e move a cobra. seguir o esboço disponibilizado
//while fica bloqueado a espera de conexao e while à espera de direcoes
//depois outro while para enviar o estado.


//enviar a cobra humana a mover, sem ver os graficos do lado do cliente
//nao mover a cobra mas visualizar o grafico.

//deixar o botao do reset so no servidor. é no servidor que se cria o botao e que se ativa.
//cobra humana nao é para ser abstrata
// private transient Linkedilist obstacle
//board implement serializable!!! tem de ser
//Boardposition tem de ser serializable

//ou entao so criar uma mensagem

