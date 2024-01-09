package org.shawn.games.SimplerChessEngine;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

import java.util.*;

public class TUI
{
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		Board board = new Board();
		System.out.println(board);
		
		while (!board.isDraw() && !board.isMated() && !board.isStaleMate())
		{
			try
			{
				board.doMove(input.next());
			}
			catch (Exception e)
			{
				System.out.println("Invalid Move, Please Try again");
			}
			System.out.println(board);
		}
	}
}
