package fr.fitzche.lgmore.RolesLg;

import org.bukkit.ChatColor;

public enum Camp {
	Villager(ChatColor.GREEN),
	Wolf(ChatColor.RED),
	Other(ChatColor.YELLOW),
	TEAM(ChatColor.GOLD),
	Love(ChatColor.LIGHT_PURPLE);
	
	
	private ChatColor color;
	
	Camp(ChatColor color)  {
		this.color = color;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
}
