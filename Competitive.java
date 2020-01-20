package etf.dotsandboxes.ma170420d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import etf.dotsandboxes.ma170420d.Board.Edge;
import etf.dotsandboxes.ma170420d.GamePlay.Status;
import etf.dotsandboxes.ma170420d.GameState.PlayerType;

public class Competitive extends GameSolver {
	private GameState bestState = null;
	private int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
	private Board board;
	private int maxDepth;
	
	
	public Competitive(Board board, int m, int n, int depth) {
		super(board.getHorizontal(), board.getVertical(), m, n);
		this.board = board;
		maxDepth = depth;
	}

	@Override
	public Edge getNextMove() {
		Edge edge = super.formSquare();
		if (edge != null) {
			int i = edge.getI();
			int j = edge.getJ();
			if (edge.isHorizontal()) return (board.getHorizontal())[i][j];
			else return (board.getVertical())[i][j];
		}
		
		ArrayList<Edge> availableOptions = new ArrayList<>();
		for (int i = 0; i < m + 1; i++)
			for (int j = 0; j < n + 1; j++) {
				if (j < n && !this.horizontal[i][j].isFilled() && !isThirdEdge(this.horizontal[i][j]))
						availableOptions.add(this.horizontal[i][j]);
				if (i < m && !this.vertical[i][j].isFilled() && !isThirdEdge(this.vertical[i][j]))
						availableOptions.add(this.vertical[i][j]);
			}
		
		if (availableOptions.size() > 0) {
			Collections.shuffle(availableOptions);
			Edge e = availableOptions.remove(0);
			int i = e.getI();
			int j = e.getJ();
			if (e.isHorizontal()) return (board.getHorizontal())[i][j];
			else return (board.getVertical())[i][j];
		}
		
		Color color = Color.red;
		
		if (board.getGamePlay().status == Status.PLAYING1) color = Color.blue;
		
		int blueScore = board.getGamePlay().getBlueScore();
		int redScore = board.getGamePlay().getRedScore();
		
		GameState rootState = new GameState(horizontal, vertical, null, PlayerType.MAX, m, n, color, blueScore, redScore, false, 0, true);
		bestState = rootState;
		maxDepth = 3;
		minimaxAlphaBeta(rootState, maxDepth, 0, alpha, beta);

		if (bestState.getEdge() == null)
			return null;
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
	
	private boolean isThirdEdge(Edge e) {
		int num = 0;
		int i = e.getI();
		int j = e.getJ();
		if (e.isHorizontal()) {
			if (i + 1 <= m) {
				if (horizontal[i + 1][j].isFilled()) num++;
				if (vertical[i][j].isFilled()) num++;
				if (vertical[i][j + 1].isFilled()) num++;
				if (num == 2) return true;
			}
			if (i - 1 >= 0) {
				num = 0;
				if (horizontal[i - 1][j].isFilled()) num++;
				if (vertical[i - 1][j].isFilled()) num++;
				if (vertical[i - 1][j + 1].isFilled()) num++;
				if (num == 2) return true;
			}
		}
		else {
			if (j + 1 <= n) {
				if (vertical[i][j + 1].isFilled()) num++;
				if (horizontal[i][j].isFilled()) num++;
				if (horizontal[i + 1][j].isFilled()) num++;
				if (num == 2) return true;
			}
			if (j - 1 >= 0) {
				num = 0;
				if (vertical[i][j - 1].isFilled()) num++;
				if (horizontal[i][j - 1].isFilled()) num++;
				if (horizontal[i + 1][j - 1].isFilled()) num++;
				if (num == 2) return true;
			}
		}
		return false;
	}
}
