package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class Negamax implements Algorithm
{
	final int PAWN_VALUE = 100;
	final int KNIGHT_VALUE = 300;
	final int BISHOP_VALUE = 300;
	final int ROOK_VALUE = 500;
	final int QUEEN_VALUE = 900;
	final int MAX_EVAL = 1000000;
	final int MIN_EVAL = -1000000;
	final int MATE_EVAL = 500000;
	final int DRAW_EVAL = 0;

	int depth;
	Side ourSide;

	public Negamax(int depth)
	{
		this.depth = depth;
	}

	public int evaluate(Board board)
	{
		Side side = board.getSideToMove();
		Side opposite = board.getSideToMove().flip();
		return Long.bitCount(board.getBitboard(Piece.make(side, PieceType.PAWN))) * PAWN_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(side, PieceType.KNIGHT))) * KNIGHT_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(side, PieceType.BISHOP))) * BISHOP_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(side, PieceType.ROOK))) * ROOK_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(side, PieceType.QUEEN))) * QUEEN_VALUE
				- 		(Long.bitCount(board.getBitboard(Piece.make(opposite, PieceType.PAWN))) * PAWN_VALUE
						+ Long.bitCount(board.getBitboard(Piece.make(opposite, PieceType.KNIGHT))) * KNIGHT_VALUE
						+ Long.bitCount(board.getBitboard(Piece.make(opposite, PieceType.BISHOP))) * BISHOP_VALUE
						+ Long.bitCount(board.getBitboard(Piece.make(opposite, PieceType.ROOK))) * ROOK_VALUE
						+ Long.bitCount(board.getBitboard(Piece.make(opposite, PieceType.QUEEN))) * QUEEN_VALUE);
	}

	private int minimax(Board board, int depth)
	{
		if (board.isMated())
		{
			return -MATE_EVAL;
		}

		if (board.isDraw())
		{
			return -DRAW_EVAL;
		}

		if (depth <= 0)
		{
			return evaluate(board);
		}

		int max = MIN_EVAL;

		final List<Move> legalMoves = board.legalMoves();

		for (Move move : legalMoves)
		{
			board.doMove(move);

			int thisMoveEval = -minimax(board, depth - 1);

			max = Math.max(thisMoveEval, max);

			board.undoMove();
		}

		return max;
	}

	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		ourSide = board.getSideToMove();

		Move bestMove = legalMoves.get(0);
		board.doMove(bestMove);
		int eval = -minimax(board, depth - 1);
		board.undoMove();

		for (Move move : legalMoves)
		{
			board.doMove(move);
			int thisMoveEval = -minimax(board, depth - 1);
			if (thisMoveEval > eval)
			{
				bestMove = move;
				eval = thisMoveEval;
			}
			board.undoMove();
		}

		return bestMove;
	}
}
