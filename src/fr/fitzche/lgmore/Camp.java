package fr.fitzche.lgmore;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public enum Camp {
	Villager(ChatColor.GREEN, "Villageois"),
	Wolf(ChatColor.RED, "Loup"),
	Other(ChatColor.YELLOW, "Solo ou Hybride"),
	TEAM(ChatColor.GOLD, "Team"),
	Love(ChatColor.LIGHT_PURPLE, "Amoureux");
	
	
	private ChatColor color;
	private String name;
	public static Camp[] allCamps = {Camp.Villager, Camp.Wolf, Camp.Other, Camp.TEAM, Camp.Love};
	
	Camp(ChatColor color, String name)  {
		this.name = color + name;
		this.color = color;
	}
	public String getName() {
		
		return this.name;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
}
