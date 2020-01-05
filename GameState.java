package etf.dotsandboxes.ma170420d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import etf.dotsandboxes.ma170420d.Board.Edge;

public class GameState {
	private Edge[][] horizontal, vertical;
	private Edge playedEdge = null;
	public enum PlayerType { MIN, MAX };
	private PlayerType type;
	private ArrayList<Edge> availableOptions = new ArrayList<>();
	private int index = 0;
	private int m, n;
	private Board board;
	private Color color;
	
	public GameState(Edge[][] horizontal , Edge[][] vertical, Edge played, PlayerType type, int m, int n, Board board, Color color) {
		this.horizontal = copyMatrix(horizontal, m + 1, n); 
		this.vertical = copyMatrix(vertical, m, n + 1);
		
		if (played != null) {
			if (played.isHorizontal()) this.horizontal[played.getI()][played.getJ()].fillEdge();
			else this.vertical[played.getI()][played.getJ()].fillEdge();
		}
		
		playedEdge = played;
		this.board = board;
		this.color = color;
		
		for (int i = 0; i < m + 1; i++) { 
			for (int j = 0; j < n + 1; j++) {
				if (j < n && !horizontal[i][j].isFilled() && numberOfEdgesAroundTile(horizontal[i][j]) != 2) 
					availableOptions.add(horizontal[i][j]);
				if (i < m && !vertical[i][j].isFilled() && numberOfEdgesAroundTile(vertical[i][j]) != 2) 
					availableOptions.add(vertical[i][j]);
			}	
		}		
		Collections.shuffle(availableOptions);
		this.type = type;
		this.m = m; this.n = n;
	}
	
	private Edge[][] copyMatrix(Edge[][] matrix, int m, int n) { 
		Edge[][] newMatrix = new Edge[m][n];
		for(int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				newMatrix[i][j] = matrix[i][j].clone();
		}
		return newMatrix;
	}
	
	private int numberOfEdgesAroundTile(Edge e) {
		int num = 0, maxNum = 0;
		int i = e.getI();
		int j = e.getJ();
		if (e.isHorizontal()) {
			if (i + 1 <= m) {
				if (horizontal[i + 1][j].isFilled()) num++;
				if (vertical[i][j].isFilled()) num++;
				if (vertical[i][j + 1].isFilled()) num++;
				if (maxNum < num) maxNum = num;
				num = 0;
			}
			if (i - 1 >= 0) {
				if (horizontal[i - 1][j].isFilled()) num++;
				if (vertical[i - 1][j].isFilled()) num++;
				if (vertical[i - 1][j + 1].isFilled()) num++;
				if (maxNum < num) maxNum = num;
				num = 0;
			}
		}
		else {
			if (j + 1 <= n) {
				if (vertical[i][j + 1].isFilled()) num++;
				if (horizontal[i][j].isFilled()) num++;
				if (horizontal[i + 1][j].isFilled()) num++;
				if (maxNum < num) maxNum = num;
				num = 0;
			}
			if (j - 1 >= 0) {
				if (vertical[i][j - 1].isFilled()) num++;
				if (horizontal[i][j - 1].isFilled()) num++;
				if (horizontal[i + 1][j - 1].isFilled()) num++;
				if (maxNum < num) maxNum = num;
				num = 0;
			}
		}
		return maxNum;
	}
	
	public boolean isMaxPlayer() {
		return (type == PlayerType.MAX);
	}
	
	public boolean isMinPlayer() {
		return (type == PlayerType.MIN);
	}
	
	public boolean isTerminalState() {
		return (availableOptions.size() == 0);
	}
	
	public int heuristic() {
		int value;
		GamePlay gamePlay = board.getGamePlay();
		int blueScore = gamePlay.getBlueScore();
		int redScore =  gamePlay.getRedScore();
		
        if (color == Color.blue) value = blueScore - redScore;
        else value = redScore - blueScore;
        
        return value;
	}
	
	public GameState createChild() {
		Edge newEdge = availableOptions.get(index++);
		
		PlayerType newType = PlayerType.MIN;
		if (type == PlayerType.MIN) newType = PlayerType.MAX;
		Color newColor = Color.red;
		if (color == Color.red) newColor = Color.blue;
		
		GameState newState = new GameState(horizontal, vertical, newEdge, newType, m, n, board, newColor);
		return newState;
	}
	
	public Edge getEdge() {
		return playedEdge;
	}
	
	public boolean hasNextMove() {
		return index < availableOptions.size(); 
	}
}
