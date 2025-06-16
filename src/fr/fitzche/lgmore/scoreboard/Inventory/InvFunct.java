package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

public interface InvFunct {

	
	public void click(PlayerData p, GameLg game, String clickedName, ArrayList<String> lores);
}
