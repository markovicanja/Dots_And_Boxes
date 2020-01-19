package etf.dotsandboxes.ma170420d;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class Player extends Thread {
	private Board board;
	private boolean isBot;
	private GameSolver player;
	private boolean working = false;
	private int seconds;
	
	public Player(String ply, String diff, Board board, int m, int n, int maxDepth, int seconds) {
		this.board = board;
		this.seconds = seconds;
		if (ply.equals("Computer")) isBot = true;
		else isBot = false;
		if (diff.equals("Easy")) player = new Beginner(board.getHorizontal(), board.getVertical(), m, n);
		else if (diff.equals("Medium")) player = new Medium(board, m, n, maxDepth);
		else if (diff.equals("Hard")) player = new Competitive(board, m, n, maxDepth);
		
		if (isBot) this.start();
	}
	
	public void run() {
		Edge edge = null;
		try {
			while(!interrupted()) {
				synchronized(this) { if (!working) wait(); }
				Thread.sleep(seconds);
				edge = player.getNextMove();
				if (edge == null) break;
				board.makeMove(edge, edge.getI(), edge.getJ());
			}
		} catch (InterruptedException e) {
		}
	}
	
	public synchronized void continueThread() {
		working = true;
		notify();
	}
	
	public void pauseThread() {
		working = false;
	}
	
	public void stopThread() {
		interrupt();
	}
	
	public boolean isBot() {
		return isBot;
	}
}
