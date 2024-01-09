package org.shawn.games.SimplerChessEngine;

import org.shawn.games.SimplerChessEngine.Algorithms.*;

public class TUI
{
	public static void main(String args[])
	{
		MatchMaker match = new MatchMaker(new RandomMover(), new RandomMover());
		System.out.println(match.demonstrationMatch(false));
	}
}
