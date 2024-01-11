package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class Gluttonous implements Algorithm
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

	Random rng;

	public Gluttonous()
	{
		this.rng = new Random();
	}

	private int pieceValue(Piece p)
	{
		if (p.getPieceType() == null)
		{
			return 0;
		}

		if (p.getPieceType().equals(PieceType.PAWN))
		{
			return 1;
		}

		if (p.getPieceType().equals(PieceType.BISHOP) || p.getPieceType().equals(PieceType.KNIGHT))
		{
			return 3;
		}

		if (p.getPieceType().equals(PieceType.ROOK))
		{
			return 5;
		}

		if (p.getPieceType().equals(PieceType.QUEEN))
		{
			return 9;
		}

		return 0;
	}

	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();

		for (Move move : legalMoves)
		{
			board.doMove(move);
			if (board.isMated())
			{
				return move;
			}
			board.undoMove();
		}

		legalMoves.sort(new Comparator<Move>() {
			public int compare(Move m1, Move m2)
			{
				return pieceValue(board.getPiece(m1.getTo()))
						- pieceValue(board.getPiece(m2.getTo()));
			}
		});

		return pieceValue(board.getPiece(legalMoves.get(legalMoves.size() - 1).getTo())) == 0
				? legalMoves.get(rng.nextInt(legalMoves.size()))
				: legalMoves.get(legalMoves.size() - 1);
	}
}
