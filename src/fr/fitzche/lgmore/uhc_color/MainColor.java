package fr.fitzche.lgmore.uhc_color;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.uhc_color.PlayerColorboard.*;

public class MainColor implements Listener{
	
	public static ArrayList<Player> players;
	public static Server serv;
	public static ArrayList<Colorboard> colors = new ArrayList<Colorboard>();
	public static JavaPlugin plug;
	
	
	public void onEnable() {
		MainColor.plug = Main.plug;
		
		
		Bukkit.getPluginManager().registerEvents(this, plug);
		MainColor.serv = Bukkit.getServer();
		MainColor.players = new ArrayList<Player>();
		for (Player ply:Bukkit.getOnlinePlayers()) {
			MainColor.players.add(ply);
		}
		
		colors = new ArrayList<Colorboard>();
		for (Player ply:MainColor.players) {
			colors.add(new Colorboard(ply));
		}
	}
	
	
	
	public static JavaPlugin getPlugin() {
		return Main.plug;
	}
	
	public static Player getPlayer(String name) {
		for (Player ply: MainColor.players) {
			System.out.println(ply.getName() + "/"+ name);
			if (ply.getName().equals(name)) {
				System.out.println(ply.getName() + "/"+ name);
				return ply;
			}
		}
		
		
		System.out.println("joueur non trouvé");
		return null;
	}
	
	public static Colorboard getPlayerBoard(Player p) {
		for (Colorboard b:MainColor.colors) {
			if (b.player==p) {
				
				return b;
			}
		}
		
		Colorboard col = new Colorboard(p);
		MainColor.colors.add(col);
		
		return col;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		System.out.println("added");
		MainColor.players.add(e.getPlayer());
		
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		MainColor.players.remove(MainColor.getPlayer(e.getPlayer().getName()));
	}
	
}
