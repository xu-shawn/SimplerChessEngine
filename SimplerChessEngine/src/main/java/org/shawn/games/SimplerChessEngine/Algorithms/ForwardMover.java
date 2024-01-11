package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class ForwardMover implements Algorithm
{
	static class MoveComparator implements Comparator<Move>
	{
		@Override
		public int compare(Move m1, Move m2)
		{
			return (Integer.parseInt(m1.getTo().getRank().getNotation())
					- Integer.parseInt(m1.getFrom().getRank().getNotation()))
					- (Integer.parseInt(m2.getTo().getRank().getNotation())
							- Integer.parseInt(m2.getFrom().getRank().getNotation()));
		}

	}

	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		legalMoves.sort(new Comparator<Move>() {
			public int compare(Move m1, Move m2)
			{
				return (Integer.parseInt(m1.getTo().getRank().getNotation())
						- Integer.parseInt(m1.getFrom().getRank().getNotation()))
						- (Integer.parseInt(m2.getTo().getRank().getNotation())
								- Integer.parseInt(m2.getFrom().getRank().getNotation()));
			}
		});
		return legalMoves.get(board.getSideToMove() == Side.BLACK ? 0 : (legalMoves.size() - 1));
	}
}
