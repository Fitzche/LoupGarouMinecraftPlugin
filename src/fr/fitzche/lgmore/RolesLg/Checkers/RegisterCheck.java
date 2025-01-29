package fr.fitzche.lgmore.RolesLg.Checkers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class RegisterCheck implements ResCheck{
	GameLg game;
	
	public RegisterCheck(GameLg game) {
		this.game = game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void runDeathAction(PlayerDeathEvent e, Player k) {
	
		
		PlayerData victim = PlayerUtil.getDataOfPlayer(e.getEntity(), "at register checker");
		if (game.getTragic() > 25) {
			PlayerUtil.getDataOfPlayer(k, "at register checker").changeHealth(1);
		}
		if (MathUtil.pourcentage(game.getEpic()/6)) {
			k.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 18000, 0, false, false));
		}
		if (victim.camp == Camp.Wolf) {
			if (MathUtil.pourcentage(game.getEpic()/3)) {
				game.broadcoast("Le tueur du loup garou "+victim.getName()+ " est "+k.getName());
			}
		}
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
		if (game.getTragic()  < 100 && game.getTragic() + i > 100) {
			for (PlayerData p:game.getPlayerAlive()) {
				GameLgUtil.tpAl(p);
			}
		}
		
	}

	@Override
	public void onAddEpic(int i, Location loc) {
		// TODO Auto-generated method stub
		
	}

	@Deprecated
	@Override
	public void onAddOrat(int i, Location loc) {
		if (game.getTragic()  < 80 && game.getTragic() + i > 80) {
			if ((game.timer.temps - (game.timer.getEpisode() - 1) * 1200)>80 && !game.hasMoreVote) {
				game.broadcoast(ChatColor.AQUA+"Un vote Supplémentaire se déclenche");
				game.hasMoreVote = true;
				game.startVote();
			} else {
				game.broadcoast(ChatColor.AQUA+"Un vote Supplémentaire se déclenchera dans 10min car un vote approche déjà");
				Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

					@Override
					public void run() {
						game.broadcoast(ChatColor.AQUA+"Un vote Supplémentaire se déclenche");
						game.hasMoreVote = true;
						game.startVote();
						
					}
					
				},	12000);
			}
		}
		if (game.getTragic() < 100 && game.getTragic() + i >= 100) {
			ArrayList<PlayerData> choosen = new ArrayList<PlayerData>();
			choosen.add(GameLgUtil.getAlPlayer(game));
			choosen.add(GameLgUtil.getAlPlayer(game));
			choosen.add(GameLgUtil.getAlPlayer(game));

			game.exposedMulti(null, i);
		}
		
	}

	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
