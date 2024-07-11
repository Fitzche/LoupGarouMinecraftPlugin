package fr.fitzche.lgmore.RolesLg;

import net.md_5.bungee.api.ChatColor;

public enum Aura {
	OBSCUR(ChatColor.DARK_RED + "Obscur"),
	NEUTRAL(ChatColor.YELLOW + "Neutre "),
	LUMINOUS(ChatColor.GOLD+ "Lumineuse"), 
	DANGEROUS(ChatColor.BLACK + "Dangereuse"),
	UNKNOW(ChatColor.MAGIC + "étrange");

	private String name;
	Aura(String name) {
		this.name = name;
	}
}
