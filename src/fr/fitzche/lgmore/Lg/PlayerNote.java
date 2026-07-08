package fr.fitzche.lgmore.Lg;

import java.io.Serializable;

import fr.fitzche.lgmore.Role;
import fr.fitzche.lgmore.RolesLg.RolesLg;

public class PlayerNote implements Serializable {
	
	
	public String name;
	public RolesLg role;
	public int kill;
	public boolean inLove;
	public boolean infected;
	public boolean hasWin;
	
	public PlayerNote(String name, RolesLg role, int kill, boolean inLove, boolean infected, boolean hasWin) {
		this.name = name;
		this.role = role;
		this.kill = kill;
		this.inLove = inLove;
		this.infected = infected;
		this.hasWin = hasWin;
			
				
	}
}
