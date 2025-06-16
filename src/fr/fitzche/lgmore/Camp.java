package fr.fitzche.lgmore;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.ChatColor;

public enum Camp implements Serializable{
	Villager(ChatColor.GREEN, "Villageois"),
	Wolf(ChatColor.DARK_RED, "Loups-Garou"),
	Other(ChatColor.GOLD, "Solo et Hybride"),
	TEAM(ChatColor.GOLD, "Team"),
	Love(ChatColor.LIGHT_PURPLE, "Amoureux"),
	Died(ChatColor.ITALIC, "Mort-Vivant"),
	RED(ChatColor.RED, "Rouge"),
	YELLOW(ChatColor.YELLOW, "Jaune"),
	BLUE(ChatColor.DARK_BLUE, "Bleu"),
	GREEN(ChatColor.DARK_GREEN, "Vert"),
	PINK(ChatColor.LIGHT_PURPLE, "Rose"),

	Uneffective(ChatColor.DARK_GRAY, "Invisible");
	
	
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
