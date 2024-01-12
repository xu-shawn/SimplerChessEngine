package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class AlphaBeta implements Algorithm
{
	private final int PAWN_VALUE = 100;
	private final int KNIGHT_VALUE = 300;
	private final int BISHOP_VALUE = 300;
	private final int ROOK_VALUE = 500;
	private final int QUEEN_VALUE = 900;
	private final int MAX_EVAL = 1000000000;
	private final int MIN_EVAL = -1000000000;
	private final int MATE_EVAL = 500000000;
	private final int DRAW_EVAL = 0;

	private int depth;

	public AlphaBeta(int depth)
	{
		this.depth = depth;
	}
	
	private int pieceValue(Piece p)
	{
		if (p.getPieceType() == null)
		{
			return 0;
		}

		if (p.getPieceType().equals(PieceType.PAWN))
		{
			return PAWN_VALUE;
		}

		if (p.getPieceType().equals(PieceType.KNIGHT))
		{
			return KNIGHT_VALUE;
		}

		if (p.getPieceType().equals(PieceType.BISHOP))
		{
			return BISHOP_VALUE;
		}

		if (p.getPieceType().equals(PieceType.ROOK))
		{
			return ROOK_VALUE;
		}

		if (p.getPieceType().equals(PieceType.QUEEN))
		{
			return QUEEN_VALUE;
		}

		return 0;
	}

	public int evaluate(Board board)
	{
		return PeSTO.evaluate(board);
	}
	
	private List<Move> sortMoves(List<Move> moves, Board board)
	{
		moves.sort(new Comparator<Move>() {

			@Override
			public int compare(Move m1, Move m2)
			{
				return pieceValue(board.getPiece(m2.getTo())) - pieceValue(board.getPiece(m2.getFrom()))
						- (pieceValue(board.getPiece(m1.getTo())) - pieceValue(board.getPiece(m1.getFrom())));
			}
			
		});
		return moves;
	}
	
	private int quiesce(Board board, int alpha, int beta, int ply)
	{
		
		if (board.isMated())
		{
			return -MATE_EVAL + ply;
		}

		if (board.isDraw())
		{
			return -DRAW_EVAL;
		}
		
		int standPat = evaluate(board);
		
		alpha = Math.max(alpha, standPat);
		
		if(alpha >= beta)
		{
			return beta;
		}
		
		final List<Move> pseudoLegalCaptures = board.pseudoLegalCaptures();
		
		sortMoves(pseudoLegalCaptures, board);
		
		for(Move move: pseudoLegalCaptures)
		{
			if(!board.isMoveLegal(move, true))
			{
				continue;
			}
			
			board.doMove(move);
			
			int score = -quiesce(board, -beta, -alpha, ply + 1);
			
			board.undoMove();
			
			alpha = Math.max(alpha, score);
			
			if(alpha >= beta)
			{
				return beta;
			}
		}
		
		return alpha;
	}

	private int mainSearch(Board board, int depth, int alpha, int beta, int ply)
	{
		if (board.isMated())
		{
			return -MATE_EVAL + ply;
		}

		if (board.isDraw())
		{
			return -DRAW_EVAL;
		}

		if (depth <= 0)
		{
			return quiesce(board, alpha, beta, ply + 1);
		}

		final List<Move> legalMoves = board.legalMoves();
		
		sortMoves(legalMoves, board);

		for (Move move : legalMoves)
		{
			board.doMove(move);
			
			int newdepth = depth - 1;

			int thisMoveEval = -mainSearch(board, newdepth, -beta, -alpha, ply + 1);

			alpha = Math.max(alpha, thisMoveEval);
			
			if (alpha >= beta)
			{
				board.undoMove();
				return beta;
			}

			board.undoMove();
		}

		return alpha;
	}

	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();

		Move bestMove = legalMoves.get(0);
		board.doMove(bestMove);

		int rootAlpha = MIN_EVAL;
		int rootBeta = MAX_EVAL;
		
		board.undoMove();

		for (Move move : legalMoves)
		{
			board.doMove(move);
			int thisMoveEval = -mainSearch(board, depth - 1, -rootBeta, -rootAlpha, 1);
			board.undoMove();
			
			if (thisMoveEval > rootAlpha)
			{
				bestMove = move;
				rootAlpha = thisMoveEval;
			}
		}

		return bestMove;
	}
}
