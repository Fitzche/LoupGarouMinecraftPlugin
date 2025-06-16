package fr.fitzche.lgmore.RolesLg;

import java.io.Serializable;

import net.md_5.bungee.api.ChatColor;

public enum Aura implements Serializable {
	OBSCUR(ChatColor.DARK_RED + "Obscur"),
	NEUTRAL(ChatColor.YELLOW + "Neutre "),
	LUMINOUS(ChatColor.GOLD+ "Lumineuse"), 
	DANGEROUS(ChatColor.BLACK + "Dangereuse"),
	UNKNOW(ChatColor.MAGIC + "étrange");

	private String name;
	Aura(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
