package fr.fitzche.lgmore.bedwars;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.metadata.FixedMetadataValue;

import fr.fitzche.lgmore.Main;

public enum BedTeam {

	Blue(Color.BLUE, ChatColor.BLUE, "Bleu"),
	YELLOW(Color.YELLOW, ChatColor.YELLOW, "Jaune"), 
	GREEN(Color.GREEN, ChatColor.GREEN, "Vert"),
	RED(Color.RED, ChatColor.RED, "Rouge");
	
	
	private Color color;
	private ChatColor chatColor;
	private String name;
	
	public final static FixedMetadataValue blueMeta = new FixedMetadataValue(Main.plug, "blue");
	public final static FixedMetadataValue YellowMeta = new FixedMetadataValue(Main.plug, "yellow");
	public final static FixedMetadataValue GreenMeta = new FixedMetadataValue(Main.plug, "green");
	public final static FixedMetadataValue RedMeta = new FixedMetadataValue(Main.plug, "red");
	
	
	
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
	
	public FixedMetadataValue getMetaValue() {
		switch (this.chatColor) {
		
		case BLUE:
			return blueMeta;
		case RED:
			return RedMeta;
		case YELLOW:
			return YellowMeta;
		case GREEN:
			return GreenMeta;
		}
		return null;
	}
}
