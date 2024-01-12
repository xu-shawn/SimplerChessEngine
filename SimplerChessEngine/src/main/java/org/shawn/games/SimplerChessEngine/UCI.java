package org.shawn.games.SimplerChessEngine;

import java.util.*;

import org.shawn.games.SimplerChessEngine.Algorithms.*;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.*;

public class UCI
{
	Algorithm ai;
	Board board;

	public UCI(Algorithm ai)
	{
		this.ai = ai;
		board = new Board();
	}

	public static void main(String args[])
	{
		new UCI(new AlphaBeta(5)).UCIMainLoop();
	}

	public void UCIMainLoop()
	{
		Scanner input = new Scanner(System.in);
		while (true)
		{
			String command = input.nextLine();

			switch (command.split(" ")[0])
			{
				case "isready":
					System.out.println("readyok");
					break;
				case "uci":
					System.out.println("id name weakEngine");
					System.out.println("id author me");
					System.out.println("uciok");
					break;
				case "quit":
					input.close();
					return;
				case "go":
					Move aiMove = ai.nextMove(board);
					System.out.println("info pv " + aiMove);
					System.out.println("bestmove " + aiMove);
					break;
				case "position":
					String[] fullCommand = command.split(" ");
					for (int i = 1; i < fullCommand.length; i++)
					{
						if (fullCommand[i].equals("startpos"))
						{
							board = new Board();
						}
						if (fullCommand[i].equals("fen"))
						{
							board = new Board();
							board.loadFromFen(fullCommand[i + 1] + " " + fullCommand[i + 2] + " "
									+ fullCommand[i + 3] + " " + fullCommand[i + 4] + " "
									+ fullCommand[i + 5] + " " + fullCommand[i + 6]);
						}
						if (fullCommand[i].equals("moves"))
						{
							for (int j = i + 1; j < fullCommand.length; j++)
							{
								board.doMove(fullCommand[j]);
							}
							break;
						}
					}
					break;
			}
		}
	}
}
