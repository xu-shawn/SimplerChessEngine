package org.shawn.games.SimplerChessEngine;

import org.shawn.games.SimplerChessEngine.Algorithms.*;

public class TUI
{
	public static void main(String args[])
	{
		MatchMaker match = new MatchMaker(new RandomMover(), new ForwardMover());
		int[] results = match.play(1000);
		System.out.print("WDL: ");
		System.out.print(results[0]);
		System.out.print("-");
		System.out.print(results[2]);
		System.out.print("-");
		System.out.println(results[1]);
		System.out.println(match.demonstrationMatch(false));
	}
}
