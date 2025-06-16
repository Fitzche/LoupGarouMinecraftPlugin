package fr.fitzche.lgmore.Lg;

import java.io.Serializable;
import java.util.ArrayList;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class GameNote implements Listener, Serializable {

	
	public ArrayList<PlayerNote> plysNote = new ArrayList<PlayerNote>();
	public Camp winningCamp;
	public ArrayList<String> winners = new ArrayList<String>();
	public String name;
	
	public GameNote(GameLg game, String name) {
		
		this.name = name;
		for (PlayerData p:game.getPlayerAlive()) {
			winners.add(p.getName());
			winningCamp = p.camp;
		}
		for (PlayerData p:game.players) {
			plysNote.add(new PlayerNote(p.getName(), p.role, p.numberOfKill, p.inLove, p.infected, p.inLife));
		}
	}
	
	public GameNote(String forceNote) {
		winningCamp = Camp.Uneffective;
		name = "example";
		winners.add("exa1");
		winners.add("exa2");
		winners.add("exa3");
	}
	
	public String getStrRole(String named) {
		for (PlayerNote n:plysNote) {
			if (n.name.equals(named)) {
				return n.role.getName();
			}
		}
		return "erreur";
	}

	public void open(Player whoClicked) {
		whoClicked.sendMessage("Game: "+name + " gagné par le camp "+winningCamp.getName() + "\n"+"Vainqueurs: " );
		for (PlayerNote p:this.plysNote) {
			ChatColor color = ChatColor.GREEN;
			if (p.hasWin) {
				color = ChatColor.GOLD;
			}
			
			String str = color +"-"+p.name + ": "+ p.role.getName() + ". "+p.kill + " kills";
			if (p.infected) {
				str = str + ChatColor.RED + "infecté";
			}
			if (p.inLove) {
				str = str + ChatColor.LIGHT_PURPLE + "en couple.";
			}
			if (p.hasWin) {
				str = str + color + "(vainqueur)";
			}
			
			whoClicked.sendMessage(str);
		}
		
	}
}
