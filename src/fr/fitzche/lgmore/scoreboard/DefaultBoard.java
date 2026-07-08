package fr.fitzche.lgmore.scoreboard;

import fr.fitzche.lgmore.PlayerData;

public class DefaultBoard implements GameBoard{

	PlayerData p;
	public DefaultBoard(PlayerData p) {
		this.p = p;
	}
	@Override
	public void refresh() {
		
		if (p.starParty != null) {
			p.board = new StarBoard(p);
		}
	}

}
