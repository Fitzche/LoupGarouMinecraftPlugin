package fr.fitzche.lgmore.scoreboard.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.fitzche.lgmore.Game;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameType;
import fr.fitzche.lgmore.Util.CommandUtil;
import fr.fitzche.lgmore.Util.ItemUtil;
import fr.fitzche.lgmore.Util.MathUtil;

public class GameTypeChoose implements InvFunct {

	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public GameTypeChoose() {
		items.add(ItemUtil.getItem(ItemUtil.getCustomHead("TheGuill84"), ChatColor.DARK_RED+ ""+ChatColor.BOLD + "Loup Garou", new ArrayList<String>(Arrays.asList(ChatColor.GRAY +"   ▪Loup garou Uhc Classique", ChatColor.GRAY +"   ▪Plusieurs scénarios et events"))));
		items.add(ItemUtil.getItem(ItemUtil.getCustomHead("Guep"), ChatColor.DARK_BLUE + ""+ChatColor.BOLD+"Team Swapper", new ArrayList<String>(Arrays.asList(ChatColor.GRAY +"   ▪Scénario du loup garou", ChatColor.GRAY +"   ▪Ce n'est pas un loup garou uhc", ChatColor.GRAY +"   ▪5 équipes par défauts, modifiable dans scénarios", ChatColor.GRAY +"   ▪configuration des roles inutile si team swapper activé"))));
		
		
	}
	
	public void open(PlayerData p, Inventory backInv) {
		StringChooseInv inv = new StringChooseInv(p, items, backInv, this);
	}
	@Override
	public void click(PlayerData p, Game game, String clickedName, ArrayList<String> lores, ItemStack returnedItem) {
		if (clickedName.equals(ChatColor.DARK_RED+ ""+ChatColor.BOLD + "Loup Garou")) {
			String gameName = "gameLg-"+ MathUtil.generateAlInt(0, 10000);
			String[] args0 = new String[] {"Game" , "create" , gameName};
			CommandUtil.runCommand("lga", p.player, args0);
			Main.strToGame.get(gameName).gameType = GameType.LoupGarou;
			
			
			
			String[] args1 = new String[] {"Game" , "config" , gameName};
			CommandUtil.runCommand("lga", p.player, args1);
		} else if (clickedName.equals(ChatColor.DARK_BLUE + ""+ChatColor.BOLD+"Team Swapper")) {
			String gameName2 = "gameLg-"+ MathUtil.generateAlInt(0, 10000);
			String[] args2 = new String[] {"Game" , "create" , gameName2};
			CommandUtil.runCommand("lga", p.player, args2);
			Main.strToGame.get(gameName2).gameType = GameType.TeamSwapper;
			
			
			
			String[] args3 = new String[] {"Game" , "config" , gameName2};
			
			
			((GameLg)p.game).swapper = true;
			((GameLg)p.game).swapperPente = true;
			CommandUtil.runCommand("lga", p.player, args3);
		}
		

	}

}
