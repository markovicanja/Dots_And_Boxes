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
//	private Board board;
	private Color color;
	private static int posid = 0;
	private int id;
	private int blueScore = 0, redScore = 0;
	
	public GameState(Edge[][] horizontal , Edge[][] vertical, Edge played, PlayerType type, int m, int n, Color color, int blueScore, int redScore) {
		this.horizontal = copyMatrix(horizontal, m + 1, n); 
		this.vertical = copyMatrix(vertical, m, n + 1);
		
		if (played == null) posid = 0; //obrisi ovo
		id = posid++;
		
		this.blueScore = blueScore;
		this.redScore = redScore;
		
		if (played != null) {
			if (played.isHorizontal()) {
				(playedEdge = this.horizontal[played.getI()][played.getJ()]).fillEdge();
			}
			else {
				(playedEdge = this.vertical[played.getI()][played.getJ()]).fillEdge();
			}
		}
		if (playedEdge != null) setScore();
		
//		this.board = board;
		this.color = color;
		this.m = m; this.n = n;
		this.type = type;

		for (int i = 0; i < m + 1; i++)
			for (int j = 0; j < n + 1; j++) {
				if (j < n && !this.horizontal[i][j].isFilled() && !isThirdEdge(this.horizontal[i][j])) 
					availableOptions.add(this.horizontal[i][j]);
				if (i < m && !this.vertical[i][j].isFilled() && !isThirdEdge(this.vertical[i][j])) 
					availableOptions.add(this.vertical[i][j]);
			}
		
//		System.out.println(this.id);
//		for (Edge e:availableOptions) {
//			System.out.println((e.isHorizontal()?"horizontal ":"vertical ") + e.getI() + " "+ e.getJ());
//		}
//		System.out.println("");
		
		Collections.shuffle(availableOptions);
	}
	
	private Edge[][] copyMatrix(Edge[][] matrix, int m, int n) { 
		Edge[][] newMatrix = new Edge[m][n];
		for(int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				newMatrix[i][j] = matrix[i][j].clone();
		}
		return newMatrix;
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
	
	public boolean isMaxPlayer() {
		return (type == PlayerType.MAX);
	}
	
	public boolean isMinPlayer() {
		return (type == PlayerType.MIN);
	}
	
	public boolean isTerminalState() {
		return (availableOptions.size() == 0);
	}
	
	public void setScore() {
		int scoreNum = 0;
		int i = playedEdge.getI();
		int j = playedEdge.getJ();
		if (playedEdge.isHorizontal()) {
			if (i + 1 <= m && horizontal[i + 1][j].isFilled() && vertical[i][j].isFilled() && vertical[i][j + 1].isFilled())
				scoreNum++;
			if (i - 1 >= 0 && horizontal[i - 1][j].isFilled() && vertical[i - 1][j].isFilled() && vertical[i - 1][j + 1].isFilled())
				scoreNum++;
		}
		else {
			if (j + 1 <= n && vertical[i][j + 1].isFilled() && horizontal[i][j].isFilled() && horizontal[i + 1][j].isFilled())
				scoreNum++;
			if (j - 1 >= 0 && vertical[i][j - 1].isFilled() && horizontal[i][j - 1].isFilled() && horizontal[i + 1][j - 1].isFilled())
				scoreNum++;
		}
		if (this.color == Color.blue) blueScore += scoreNum;
		else redScore += scoreNum;
		System.out.println("scoreNum "+scoreNum);
	}
	
	public int heuristic() {
		int value;
//		GamePlay gamePlay = board.getGamePlay();
//		int blueScore = gamePlay.getBlueScore();
//		int redScore =  gamePlay.getRedScore();
		
        if (color == Color.blue) value = blueScore - redScore;
        else value = redScore - blueScore;
        
        System.out.println(blueScore+ " : "+ redScore + " "+ this.id);
        return value;
	}
	
	public GameState createChild() { 
		Edge newEdge = availableOptions.get(index++);
		
		PlayerType newType = PlayerType.MIN;
		if (type == PlayerType.MIN) newType = PlayerType.MAX;
		Color newColor = Color.red;
		if (color == Color.red) newColor = Color.blue;
		
		GameState newState = new GameState(horizontal, vertical, newEdge, newType, m, n, newColor, blueScore, redScore);
		return newState;
	}
	
	public Edge getEdge() {
		return playedEdge;
	}
	
	public boolean hasNextMove() {
		return (index < availableOptions.size()); 
	}
}
