package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

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
	public boolean checkRes(PlayerDeathEvent e) {
		if (e.getEntity().getKiller().getName().equals(virus.owner.Name)) {
			System.out.println("killer has virus");
			switch (virus.type) {
			case ENDED:
				break;
			case EPIDEMIE:
				break;
			case PARASITE:
				System.out.println("killer has parasite");
				PlayerData newOwner = PlayerUtil.getDataOfPlayer(e.getEntity(), "at virus checker");
				virus.owner.sendMessage(ChatColor.DARK_GREEN+"Vous avez transmi le parasite");
				
				virus.owner = newOwner;
				newOwner.sendMessage(ChatColor.DARK_GREEN+"Vous réssucitez, cependant un parasite vous a infecté");
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
	public void runDeathAction(PlayerDeathEvent e, Player k) {
		// TODO Auto-generated method stub

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
	public void onAddTragic(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddEpic(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddOrat(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
