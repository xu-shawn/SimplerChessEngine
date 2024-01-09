package org.shawn.games.SimplerChessEngine.Algorithms;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;
import java.util.*;

public class RandomMover implements Algorithm
{
	@Override
	public Move nextMove(Board board)
	{
		final List<Move> legalMoves = board.legalMoves();
		Random rng = new Random();
		return legalMoves.get(rng.nextInt(legalMoves.size()));
	}
}
