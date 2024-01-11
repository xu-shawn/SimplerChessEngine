package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class Minimax implements Algorithm
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
	
	public Minimax(int depth)
	{
		this.depth = depth;
	}

	public int evaluate(Board board)
	{
		return Long.bitCount(board.getBitboard(Piece.make(ourSide, PieceType.PAWN))) * PAWN_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(ourSide, PieceType.KNIGHT))) * KNIGHT_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(ourSide, PieceType.BISHOP))) * BISHOP_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(ourSide, PieceType.ROOK))) * ROOK_VALUE
				+ Long.bitCount(board.getBitboard(Piece.make(ourSide, PieceType.QUEEN))) * QUEEN_VALUE;
	}

	private int minimax(Board board, int depth, boolean mini)
	{
		if(board.isMated())
		{
			return MATE_EVAL;
		}
		
		if(depth <= 0)
		{
			return evaluate(board);
		}
		
		int min = MAX_EVAL;
		int max = MIN_EVAL;
		
		final List<Move> legalMoves = board.legalMoves();
		
		for (Move move: legalMoves)
		{
			board.doMove(move);
			
			int thisMoveEval = minimax(board, depth - 1, !mini);
			if(mini && thisMoveEval < min)
			{
				min = thisMoveEval;
			}
			
			else if(!mini && thisMoveEval > max)
			{
				max = thisMoveEval;
			}
			
			board.undoMove();
		}
		
		return mini ? min : max;
	}

	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		ourSide = board.getSideToMove();
		
		Move bestMove = legalMoves.get(0);
		board.doMove(bestMove);
		int eval = minimax(board, depth - 1, true);
		board.undoMove();
		
		for (Move move: legalMoves)
		{
			board.doMove(move);
			int thisMoveEval = minimax(board, depth - 1, true);
			if(thisMoveEval > eval)
			{
				bestMove = move;
				eval = thisMoveEval;
			}
			board.undoMove();
		}
		
		return bestMove;
	}
}
