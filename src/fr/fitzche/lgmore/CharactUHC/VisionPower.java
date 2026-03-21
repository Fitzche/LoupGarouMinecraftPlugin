package fr.fitzche.lgmore.CharactUHC;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.primitives.Chars;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import net.md_5.bungee.api.ChatColor;

public class VisionPower {

	
	public CharactRole role;
	public String type;
	public int distance;
	public boolean all;
	public String command;
	public boolean aleaTime;
	public String caractSeen;
	public int probaTrue;
	public boolean knowWho;
	public int use;
	
	public VisionPower(CharactRole role, String type, boolean all, int distance, String command, boolean timeAlea, String caractSeen, int probaTrue, boolean knowWho, int use ) {
		this.role = role;
		this.type = type;
		this.all = all;
		this.distance = distance;
		this.aleaTime = timeAlea;
		this.command = command;
		this.caractSeen = caractSeen;
		this.probaTrue = probaTrue;
		this.knowWho = knowWho;
		this.use = use;
	}
	
	public void run() {
		switch (this.type) {
		case "seeCaract":
			
			ArrayList<String> players = new ArrayList<String>();
			ArrayList<String> caracts = new ArrayList<String>();
			for (PlayerData player:role.game.getPlayers()) {
				if (LocationUtil.getDistanceBetween(player, role.playerData) < distance) {
					players.add(player.getName());
					if (((CharactRole) role.set.rolesOfPlayer().get(player.getName())).objson.get(caractSeen) != null) {
						
					}
					caracts.add(((CharactRole) role.set.rolesOfPlayer().get(player.getName())).objson.get(caractSeen).getAsString());
				}
				
			}
			
			if (!all) {
				players = new ArrayList<String>(Arrays.asList(players.get(0)));
			}
			if (MathUtil.pourcentage(probaTrue)) {
				
				
				ArrayList<String> ps = new ArrayList<String>();
				for (String p:players) {
					char[] crs = p.toCharArray();
					String result = "";
					while (crs.length > result.length()) {
						int x = MathUtil.generateAlInt(0, crs.length - 1);
						result = result + crs[x];
						
					}
					ps.add(result);
				}
				
				players = ps;
			}
			if (knowWho) {
				role.playerData.sendMessage("Un ou plusieurs joueur possède les valeurs suivantes pour l'attribut "+ caractSeen+ ": ");
				for (String s:caracts) {
					role.playerData.sendMessage("-"+s);
				}
			} else {
				role.playerData.sendMessage("Le ou les joueur(s) suivant(s): ");
				for (String s1:players) {
					role.playerData.sendMessage("-"+s1);
				}
				role.playerData.sendMessage("possède(nt) les valeurs suivantes pour l'attribut "+ caractSeen+ ": ");
				for (String s:caracts) {
					role.playerData.sendMessage("-"+s);
				}
			}
			break;
		}
	}
}
