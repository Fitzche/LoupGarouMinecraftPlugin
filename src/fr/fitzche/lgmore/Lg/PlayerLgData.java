package fr.fitzche.lgmore.Lg;


import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import net.md_5.bungee.api.ChatColor;

public class PlayerLgData {
	
	public RolesLg roleLg;
	public int vote;
	public Camp campLg;
	public Aura aura;
	public PlayerData player; 
	public boolean inLove;
	public boolean infected;
	public boolean inLife;
	public boolean relive;
	public PlayerData voted;
	public boolean contamined = false;
	public boolean isShooted = false;
	public boolean grimed = false;
	
	public PlayerLgData(PlayerData p) {
		this.player = p;
		this.vote = 0;

		this.infected = false;
		//System.out.println("3");

		this.inLove = false;
		//System.out.println("4");
		
		this.relive = false;

		this.inLife = true;
		
	}
	
	public RolesLg getLgRole() {
		return this.roleLg;
	}
	
	
	public void askVoted() {
		
		this.player.sendMessage(ChatColor.GOLD+"Vous pouvez voter pour le joueur de votre choix"+ "\n"+ " Le joueur le plus voté subira 15s de poison et perdra 1 coeur de façon permanente");
		this.vote = 0;
		this.voted = null;
	}
	
	public void applyLgRole(RolesLg role) { 
		this.campLg = role.getCampOfRole();
		this.roleLg = role;
		this.aura = role.aura;
		
	}
}
