package etf.dotsandboxes.ma170420d;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class Player extends Thread {
	private Board board;
private int m, n;
	
	public Player(String diff1, String diff2, Board board, int m, int n) {
		this.board = board;
		this.m = m;
		this.n = n;
		this.start();
	}
	//stani, kreni, synchornized to sve dodaj
	
	public void run() {
		//sad ovde postavim tezine za odredjenje igrace
		Beginner beginner1 = new Beginner(board.getHorizontal(), board.getVertical(), m, n);
		Beginner beginner2 = new Beginner(board.getHorizontal(), board.getVertical(), m, n);
		Edge edge = null;
		while(true) {
			edge = beginner1.getNextMove();
			if (edge == null) break;
			board.makeMove(edge, edge.getI(), edge.getJ());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			edge = beginner2.getNextMove();
			if (edge == null) break;
			board.makeMove(edge, edge.getI(), edge.getJ());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}
}
