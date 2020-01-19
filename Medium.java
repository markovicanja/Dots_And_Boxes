package etf.dotsandboxes.ma170420d;

import java.awt.Color;
import etf.dotsandboxes.ma170420d.Board.Edge;
import etf.dotsandboxes.ma170420d.GamePlay.Status;
import etf.dotsandboxes.ma170420d.GameState.PlayerType;

public class Medium extends GameSolver {
	private GameState bestState = null;
	private int maxDepth;
	private int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
	private Board board;

	public Medium(Board board, int m, int n, int depth) {
		super(board.getHorizontal(), board.getVertical(), m, n);
		maxDepth = depth;	
		this.board = board;
	}

	@Override
	public Edge getNextMove() {
		Edge edge = super.formSquare();
		if (edge != null) return edge;
		
		Color color = Color.red;
		
		if (board.getGamePlay().status == Status.PLAYING1) color = Color.blue;
		
		int blueScore = board.getGamePlay().getBlueScore();
		int redScore = board.getGamePlay().getRedScore();
		
		GameState rootState = new GameState(horizontal, vertical, null, PlayerType.MAX, m, n, color, blueScore, redScore, false, 0, false);
		bestState = rootState;
		minimaxAlphaBeta(rootState, maxDepth, 0, alpha, beta);

		if (bestState.getEdge() == null) return null;
		Edge e = bestState.getEdge();	
		int i = e.getI();
		int j = e.getJ();
		if (e.isHorizontal()) return (board.getHorizontal())[i][j];
		else return (board.getVertical())[i][j];
	}
	
	public int minimaxAlphaBeta(GameState currentState, int maxDepth, int currentDepth, int alpha, int beta) {
		if(currentDepth == maxDepth || currentState.isTerminalState()) {
			return currentState.heuristic();
		}	
		int bestValue;
		if(currentState.isMaxPlayer()) bestValue = Integer.MIN_VALUE;
		else bestValue = Integer.MAX_VALUE;
		
		GameState newState = null;
		while(currentState.hasNextMove()) {
			newState = currentState.createChild(); 
			int currentValue = minimaxAlphaBeta(newState, maxDepth, currentDepth + 1, alpha, beta);
			
			if(currentState.isMaxPlayer() && currentValue > bestValue) {
				bestValue = currentValue;
				if(currentDepth == 0) bestState = newState;
				if(bestValue >= beta) return bestValue;
				alpha = Math.max(alpha, bestValue);
			}
			else if(currentState.isMinPlayer() && currentValue < bestValue) {
				bestValue = currentValue;
				if(currentDepth == 0) bestState = newState;
				if(bestValue <= alpha) return bestValue;
				beta = Math.min(beta, bestValue);
			}
		}
		return bestValue;
	}
}
