package remote;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import gui.ClientGui;

//import game.GameStatus;

public class Client {
	private Socket connection;
	private Scanner in;
	private PrintWriter out;

	private InetAddress serverAddress;
	private int port;

	public Client(InetAddress serverAddress, int port) {
		this.serverAddress = serverAddress;
		this.port = port;
	}

	public void runClient() {
		try {
			System.out.println("inicio");
			connection = new Socket(serverAddress, port);
			// 2. Get streams
			getStreams();
			// 3. Process connection
			processConnection();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 4. Close connection
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
//		String msg = "Hello ";
//		for(int i = 0; i < 5; i++) {
//			// Write message
//			out.println(msg + i);
//			System.out.println("[sent message] " + msg + i);
//			// Receive eco (read message)
//			System.out.println(in.nextLine()); // WAIT!!
//			
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		out.println("END");
		while (true) {
//			//GameStatus message = (GameStatus) in.readObject();
//			//clientGame.setMessage(message);
//			//clientGame.notifyChange();
//
//			//if (message.isGameOver)
//			//	break;
//
			ClientGui c = new ClientGui();
			c.init();
			if (c.boardGui.getLastPressedDirection() != 0) {
				int str = c.boardGui.getLastPressedDirection();
				out.println(str);
				System.out.println("Direction sent to server: " + str);
			}
			c.boardGui.clearLastPressedDirection();
		}
	}

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

	public static void main(String[] args) throws UnknownHostException {
		Client clientApp = new Client(InetAddress.getByName("localhost"), 12341);
		clientApp.runClient();
	}

}
