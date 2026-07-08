package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;
import net.md_5.bungee.api.ChatColor;

public class VirusChecker implements ResCheck {

	
	public Virus virus;
	public VirusChecker(Virus virus) {
		this.virus = virus;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		if (killer.getName().equals(virus.owner.getName())) {
			System.out.println("killer has virus");
			switch (virus.type) {
			case ENDED:
				break;
			case EPIDEMIE:
				break;
			case PARASITE:
				System.out.println("killer has parasite");
				PlayerData newOwner = Main.getData(e.getEntity());
				virus.owner.sendMessage(ChatColor.DARK_GREEN+"Vous avez transmi le parasite");
				
				virus.owner = newOwner;
				newOwner.sendMessage(ChatColor.DARK_GREEN+"Vous réssucitez, cependant un parasite vous a infecté");
				System.out.println("parasit ressut");
				return true;
				
			case POISON:
				break;
			default:
				break;
			
			}
		}
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
		return "";

	}
	@Override
	public boolean hide(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddTragic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddEpic(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAddOrat(int before, int after, Location loc) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "VirusChecker";
	}

}
