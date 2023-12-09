package gui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import environment.Board;

//import game.ClienteGame;

public class ClientGui implements Observer {

	private JFrame frame = new JFrame("pcd.io");
	public BoardComponent boardGui;
	private Board board;
	//private ClienteGame clientGame;

	public ClientGui(){
		//clientGame = new ClienteGame(null);
		//clientGame.addObserver(this);

		buildGui();
		init();
	}

	private void buildGui() {
		boardGui = new BoardComponent(board);
		frame.add(boardGui);

		frame.setSize(800, 800);
		frame.setLocation(0, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void update(Observable o, Object arg) {
		boardGui.repaint();
	}

	public void init() {
		frame.setVisible(true);
	}

//	public ClienteGame getClientGame() {
//		return clientGame;
//	}

}

