package fr.fitzche.lgmore.RolesLg.Checkers;

import org.bukkit.event.entity.PlayerDeathEvent;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Infections.Virus;
import fr.fitzche.lgmore.Util.PlayerUtil;
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
			switch (virus.type) {
			case ENDED:
				break;
			case EPIDEMIE:
				break;
			case PARASITE:
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
	public void runDeathAction(PlayerDeathEvent e) {
		// TODO Auto-generated method stub

	}

}
