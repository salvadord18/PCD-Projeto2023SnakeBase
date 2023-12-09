package gui;

import java.io.Console;
import java.io.IOException;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import game.HumanSnake;
import game.Server;

public class Main {
	public static void main(String[] args) {
		LocalBoard board = new LocalBoard();
		HumanSnake human = new HumanSnake(10,board);
		board.addSnake(human);
		//RemoteBoard board = new RemoteBoard();
		SnakeGui game = new SnakeGui(board,600,0); //remoteboard
		game.init();
		// Launch server
		// TODO
	}
	
	//cliente so visualiza o jogo, nao gere nada.
	//cliente também faz eventos de teclado. 
	//quando o cliente carrega na tecla, 
	//no remote board é só criar o HandleKeyPressed, TODO = enviar a tecla para o servidor
}
