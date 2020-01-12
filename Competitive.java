package etf.dotsandboxes.ma170420d;

import etf.dotsandboxes.ma170420d.Board.Edge;

public class Competitive extends GameSolver {
	private int maxDepth;
	private Board board;

	public Competitive(Board board, int m, int n, int depth) {
		super(board.getHorizontal(), board.getVertical(), m, n);
		maxDepth = depth;	
		this.board = board;
	}

	@Override
	public Edge getNextMove() {
		return null;
	}
	
	private int heuristic() {
//		final int scoreCoeff = 20, threeEdgeCoeff = 15, twoEdgeCoeff = 1;
//		int value;
//		GamePlay gamePlay = board.getGamePlay();
//		int blueScore = gamePlay.getBlueScore();
//		int redScore =  gamePlay.getRedScore();
//        if (gamePlay.status == Status.PLAYING1) //igra plavi igrac
//            value = scoreCoeff * redScore - scoreCoeff * blueScore;
//        else
//            value = scoreCoeff * blueScore - scoreCoeff * redScore;
//        if (referenceColor == color)
//            value += cThree * board.getBoxCount(3) - cTwo * board.getBoxCount(2);
//        else
//            value -= cThree * board.getBoxCount(3) - cTwo * board.getBoxCount(2);
//        return value
		
		//bolja heuristika, i pravljenje reusable stabla, gde samo odsecam grane koje sam vec koristila
		return 0;
	}
}
