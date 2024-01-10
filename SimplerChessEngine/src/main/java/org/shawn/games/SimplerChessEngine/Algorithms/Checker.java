package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class Checker implements Algorithm
{
	Random rng;
	
	public Checker()
	{
		this.rng = new Random();
	}
	
	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		List<Move> checks = new ArrayList<>();
		
		for(Move move: legalMoves)
		{
			board.doMove(move);
			if(board.isKingAttacked())
			{
				if(board.isMated())
				{
					return move;
				}
				checks.add(move);
			}
			board.undoMove();
		}
		
		return checks.isEmpty()? legalMoves.get(rng.nextInt(legalMoves.size())) : checks.get(rng.nextInt(checks.size()));
	}
}
