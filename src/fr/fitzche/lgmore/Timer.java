package fr.fitzche.lgmore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.fitzche.lgmore.Lg.GameLg;

public class Timer {
	public int temps = 0;
	GameLg game;
	public Timer(GameLg game) {
		Bukkit.getWorld("world").setFullTime(0);
		this.game = game;
	}
	public int getEpisode() {
		int ep = 0;
		for (long i = temps;i >= 1200 ; i =i- 1200) {
			ep++;
		//	System.out.println("+1");
		}
		
		return ep+1;
	}
	
	public boolean addOne() {
		int x = getEpisode();
		temps ++;
		
		this.game.askRunFuturesActions(); 
		int i = this.temps - ((this.getEpisode()-1) * 1200);
		if (i == 0 || i==600) {
			Main.server.getWorld("world").setTime(1000);
		} else if (i==300||i==900) {
			Main.server.getWorld("world").setTime(13000);
		}
		for (PlayerData p: this.game.getPlayerAlive()) {
			p.board.refresh();
		}
		return x != getEpisode();
		
		
	}
	
	
	public void add(int value) {
		for (int i = 0; i < value; i++) {
			addOne();
		}
	}
	public String getStringTime() {
		int hours = 0;
		int min= 0;
		int sec = 0;
		long ex = this.temps;
		for (long i = ex; i >= 1; i= i - 1) {
			sec++;
		}
		for (long i = ex; i >= 60; i=i-60) {
			min++;
		}
		for (long i = ex; i >= 3600; i=i-3600) {
			hours++;
		}
		if (this.temps < 3600) {
			return (ChatColor.GOLD+"Horloge: " +ChatColor.AQUA+ String.valueOf(min - 60*hours) + " min " + String.valueOf((sec-60*min) + " s"));

		}
		return (ChatColor.GOLD+"Horloge: " +ChatColor.AQUA+ String.valueOf(hours) + "H " + String.valueOf(min - 60*hours) + " min " + String.valueOf((sec-60*min) + " s"));
	}
}
