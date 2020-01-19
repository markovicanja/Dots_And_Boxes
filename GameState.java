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
	private ArrayList<Edge> availableOptions = new ArrayList<>(), forRootState;
	private int index = 0, level;
	private int m, n;
//	private Board board;
	private Color color;
//	private static int posid = 0;
//	public int id;
	private int blueScore = 0, redScore = 0;
	private boolean scored = false, parentScored = false, hasChild = false, isCompetitive = false;
	
	public GameState(Edge[][] horizontal , Edge[][] vertical, Edge played, PlayerType type, int m, int n, Color color, int blueScore, int redScore, boolean parentScored, int level, boolean isCompetitive) {
		this.horizontal = copyMatrix(horizontal, m + 1, n); 
		this.vertical = copyMatrix(vertical, m, n + 1);
		
		this.blueScore = blueScore;
		this.redScore = redScore;
		this.parentScored = parentScored;
		this.level = level;
		this.isCompetitive = isCompetitive;
		
		if (level == 0) {
			forRootState = new ArrayList<>();
		}
		
		if (played != null) {
			if (played.isHorizontal()) {
				(playedEdge = this.horizontal[played.getI()][played.getJ()]).fillEdge();
			}
			else {
				(playedEdge = this.vertical[played.getI()][played.getJ()]).fillEdge();
			}
		}
		
		this.color = color;
		this.m = m; this.n = n;
		this.type = type;
		
		int scoreNum = 0;
		if (playedEdge != null) {
			scoreNum = setScore(playedEdge);
		}
		
		for (int i = 0; i < m + 1; i++)
			for (int j = 0; j < n + 1; j++) {
				if (j < n && !this.horizontal[i][j].isFilled()) {
						availableOptions.add(this.horizontal[i][j]);
						if (level == 0 && !isThirdEdge(this.horizontal[i][j]))
							forRootState.add(this.horizontal[i][j]);
				}
				if (i < m && !this.vertical[i][j].isFilled()) { 
						availableOptions.add(this.vertical[i][j]);
						if (level == 0 && !isThirdEdge(this.vertical[i][j]))
							forRootState.add(this.vertical[i][j]);
				}
			}
		Collections.shuffle(availableOptions);
		
		if (scored && isCompetitive) {
			this.type = this.type == PlayerType.MAX ? PlayerType.MIN : PlayerType.MAX;
			this.color = this.color == Color.blue ? Color.red : Color.blue;
			if(this.color == Color.red) {
				this.blueScore -= scoreNum;
				this.redScore += scoreNum;
			} else{
				this.redScore -= scoreNum;
				this.blueScore += scoreNum;
			}
			
			Edge e = formSquare(availableOptions);
			while (e != null) {
				if (e.isHorizontal()) {
					(this.horizontal[e.getI()][e.getJ()]).fillEdge();
				}
				else {
					(this.vertical[e.getI()][e.getJ()]).fillEdge();
				}
				availableOptions.remove(e);
				setScore(e);
				
				e = formSquare(availableOptions);
			}
		}
	}
	
	private Edge[][] copyMatrix(Edge[][] matrix, int m, int n) { 
		Edge[][] newMatrix = new Edge[m][n];
		for(int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				newMatrix[i][j] = matrix[i][j].clone();
		}
		return newMatrix;
	}
	
	private Edge formSquare(ArrayList<Edge> edges) {
		Edge returnEdge = null;
		for (Edge edge: edges) {
			int i = edge.getI();
			int j = edge.getJ();
			if (edge.isHorizontal()) {
				if (i + 1 <= m && horizontal[i + 1][j].isFilled() && vertical[i][j].isFilled() && vertical[i][j + 1].isFilled()) {
					returnEdge = edge;
					break;
				}
				if (i - 1 >= 0 && horizontal[i - 1][j].isFilled() && vertical[i - 1][j].isFilled() && vertical[i - 1][j + 1].isFilled()) {
					returnEdge = edge;
					break;
				}
			}
			else {
				if (j + 1 <= n && vertical[i][j + 1].isFilled() && horizontal[i][j].isFilled() && horizontal[i + 1][j].isFilled()) {
					returnEdge = edge;
					break;
				}
				if (j - 1 >= 0 && vertical[i][j - 1].isFilled() && horizontal[i][j - 1].isFilled() && horizontal[i + 1][j - 1].isFilled()) {
					returnEdge = edge;
					break;
				}
			}
		}
		return returnEdge;
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
		if (isCompetitive) 
			return scored;
		return (availableOptions.size() == 0);
	}
	
	public boolean isScored() {
		return scored;
	}
	
	public int setScore(Edge playedEdge) {
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
		if (scoreNum > 0) this.scored = true;
		
		return scoreNum;
	}
	
	public int heuristic() {
		if (playedEdge == null) return 0;
		int score = 0;
		
		if (color == Color.blue)
			score = blueScore - redScore; 
		else
			score = redScore - blueScore;
		if (type == PlayerType.MIN) score *= -1;
		return score;	
	}
	
	public GameState createChild() { 
		Edge newEdge = null;
		if (level == 0 && forRootState.size() > 0) {
			newEdge = forRootState.remove(0);
			hasChild = true;
		}
		else if (!hasChild) newEdge = availableOptions.get(index++);
		
		PlayerType newType = PlayerType.MIN;
		if (type == PlayerType.MIN) newType = PlayerType.MAX;
		Color newColor = Color.red;
		if (color == Color.red) newColor = Color.blue;
		if (scored) {
			newColor = color;
			newType = type;
		}
		
		GameState newState = new GameState(horizontal, vertical, newEdge, newType, m, n, newColor, blueScore, redScore, scored, level + 1, isCompetitive);
		return newState;
	}
	
	public Edge getEdge() {
		return playedEdge;
	}
	
	public boolean hasNextMove() {
		if (level == 0 && hasChild) return index < forRootState.size();
		else return index < availableOptions.size();
	}
	
	@Override
	public String toString() {
		if(playedEdge.isHorizontal()) {
			return "H("+playedEdge.getI()+","+playedEdge.getJ()+")";
		}
		return "V("+playedEdge.getI()+","+playedEdge.getJ()+")";
	}
}