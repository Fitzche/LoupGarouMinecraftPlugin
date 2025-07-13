package fr.fitzche.lgmore.bedwars;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public enum BedTeam {

	Blue(Color.BLUE, ChatColor.BLUE, "Bleu"),
	YELLOW(Color.YELLOW, ChatColor.YELLOW, "Jaune"), 
	GREEN(Color.GREEN, ChatColor.GREEN, "Vert"),
	RED(Color.RED, ChatColor.RED, "Rouge");
	
	
	private Color color;
	private ChatColor chatColor;
	private String name;
	
	private BedTeam(Color color, ChatColor chatColor, String name) {
		this.color = color;
		this.chatColor = chatColor;
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public ChatColor getChatColor() {
		return chatColor;
	}
	
	public String getName() {
		return name;
	}
}
