package org.shawn.games.SimplerChessEngine.Algorithms;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public interface Algorithm
{
	Move nextMove(Board board);
}
