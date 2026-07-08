package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;

public interface InvFunct {

	
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem);
}
