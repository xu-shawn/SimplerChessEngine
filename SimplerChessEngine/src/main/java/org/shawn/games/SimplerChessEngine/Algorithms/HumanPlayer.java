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
		System.out.println(board + "\nFEN: " + board.getFen());
		while (true)
		{
			System.out.print("Your Move: ");
			try
			{
				Move move = new Move(s.next(), board.getSideToMove());
				if(!board.isMoveLegal(move, true))
				{
					continue;
				}
			}
			catch (Exception e)
			{
			}
		}
	}

}
