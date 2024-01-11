package org.shawn.games.SimplerChessEngine.Algorithms;

import java.util.Scanner;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class HumanPlayer implements Algorithm
{
	@Override
	public Move nextMove(Board board)
	{
		Scanner s = new Scanner(System.in);
		System.out.print(board + "\nYour Move: ");
		String move = s.next();
		return new Move(move, board.getSideToMove());
	}

}
