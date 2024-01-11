package org.shawn.games.SimplerChessEngine;

import org.shawn.games.SimplerChessEngine.Algorithms.Algorithm;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class MatchMaker
{
	Algorithm a0;
	Algorithm a1;
	
	public MatchMaker(Algorithm a0, Algorithm a1)
	{
		this.a0 = a0;
		this.a1 = a1;
	}
	
	public int[] play(int rounds)
	{
		int win = 0, loss = 0, draw = 0;
		for(int i = 0; i < rounds; i ++)
		{
			int result = playOnce(false);

			if(result == 1)
			{
				win ++;
			}
			if(result == 0)
			{
				draw ++;
			}
			if(result == -1)
			{
				loss ++;
			}
				

			result = -playOnce(true);

			if(result == 1)
			{
				win ++;
			}
			if(result == 0)
			{
				draw ++;
			}
			if(result == -1)
			{
				loss ++;
			}
		}
		
		return new int[] {win, loss, draw};
	}
	
	public int playOnce(boolean reverse)
	{
		Board board = new Board();
		boolean a0Move = !reverse;
		while (!board.isDraw() && !board.isMated())
		{
			if(a0Move)
			{
				board.doMove(a0.nextMove(board.clone()));
			}
			else
			{
				board.doMove(a1.nextMove(board.clone()));
			}
			a0Move = !a0Move;
		}
		
		if(board.isMated())
		{
			return board.getSideToMove() == Side.BLACK ? 1 : -1;
		}
		
		return 0;
	}
	
	public String demonstrationMatch(boolean reverse)
	{
		MoveList moves = new MoveList();
		Board board = new Board();
		boolean a0Move = !reverse;
		while (!board.isDraw() && !board.isMated())
		{
			Move nextMove = null;
			if(a0Move)
			{
				nextMove = a0.nextMove(board.clone());
			}
			else
			{
				nextMove = a1.nextMove(board.clone());
			}
			a0Move = !a0Move;
			System.out.println(nextMove);
			board.doMove(nextMove);
			moves.add(nextMove);
		}
		
		StringBuilder partialPGN = new StringBuilder();
		
		for(Move move: moves)
		{
			partialPGN.append(move.toString());
			partialPGN.append(" ");
		}
		
		return partialPGN.deleteCharAt(partialPGN.length() - 1).toString();
	}
}
