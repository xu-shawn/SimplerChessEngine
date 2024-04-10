package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.List;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class SimpleAlphaBeta
{
	private static Move bestMove;
	private static long nodes;

	private static int qsearch(int alpha, int beta, Board board, int ply)
	{
		nodes++;

		if (board.isMated())
		{
			return -100000;
		}

		if (board.isDraw())
		{
			return 0;
		}

		if (!board.isKingAttacked())
		{
			int standPat = PeSTO.evaluate(board);
			alpha = Math.max(alpha, standPat);

			if (alpha >= beta)
			{
				return beta;
			}
		}

		final List<Move> moves = board.isKingAttacked() ? board.pseudoLegalMoves() : board.pseudoLegalCaptures();

		for (Move move : moves)
		{
			if (!board.isMoveLegal(move, true))
			{
				continue;
			}

			board.doMove(move);

			nodes++;
			int score = -qsearch(-beta, -alpha, board, ply + 1);

			board.undoMove();

			alpha = Math.max(alpha, score);

			if (alpha >= beta)
			{
				return beta;
			}
		}

		return alpha;
	}

	public static int search(int depth, int alpha, int beta, Board board, int ply)
	{
		if (board.isMated())
		{
			return -100000;
		}

		if (board.isDraw())
		{
			return 0;
		}

		if (depth <= 0)
		{
			return qsearch(alpha, beta, board, ply);
		}

		int bestValue = Integer.MIN_VALUE;

		for (Move move : board.legalMoves())
		{
			board.doMove(move);

			nodes++;
			int thisMoveValue = -search(depth - 1, -beta, -alpha, board, ply + 1);

			board.undoMove();

			bestValue = Math.max(thisMoveValue, bestValue);

			if (bestValue > alpha)
			{
				if (ply == 0)
				{
					bestMove = move;
				}

				if (bestValue > beta)
				{
					return bestValue;
				}

				alpha = bestValue;
			}
		}

		return bestValue;
	}

	public static void main(String args[])
	{
		Board board = new Board();
		nodes = 0;
		System.out.println(search(5, -Integer.MAX_VALUE, Integer.MAX_VALUE, board, 0));
		System.out.println(nodes + " nodes");
		System.out.println("best move: " + bestMove);
	}
}
