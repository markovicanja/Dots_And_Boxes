package etf.dotsandboxes.ma170420d;

import java.awt.Color;
import etf.dotsandboxes.ma170420d.Board.Edge;
import etf.dotsandboxes.ma170420d.GamePlay.Status;
import etf.dotsandboxes.ma170420d.GameState.PlayerType;

public class Competitive extends GameSolver {
	private GameState bestState = null;
	private int maxDepth;
	private int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
	private Board board;

	//moguce optimizacije: praveljenje reusable stabla gde samo odsecam cvorove koje sam
	//vec odigrala, bolja heuristika, brojanje lanaca 
	//samo da ne pravi odma kockicu ako postoji
	
	public Competitive(Board board, int m, int n, int depth) {
		super(board.getHorizontal(), board.getVertical(), m, n);
		maxDepth = depth;	
		this.board = board;
	}

	@Override
	public Edge getNextMove() {
		return null;
	}
	
}
