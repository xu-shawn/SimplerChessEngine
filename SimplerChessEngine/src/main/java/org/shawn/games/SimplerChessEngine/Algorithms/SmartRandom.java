package org.shawn.games.SimplerChessEngine.Algorithms;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;
import java.util.*;

public class SmartRandom implements Algorithm
{
	Random rng;
	
	public SmartRandom()
	{
		this.rng = new Random();
	}
	
	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		
		for(Move move: legalMoves)
		{
			board.doMove(move);
			if(board.isMated())
			{
				return move;
			}
			board.undoMove();
		}
		
		return legalMoves.get(rng.nextInt(legalMoves.size()));
	}
}
