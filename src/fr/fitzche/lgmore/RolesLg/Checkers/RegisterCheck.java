package fr.fitzche.lgmore.RolesLg.Checkers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.VoteEvent;
import fr.fitzche.lgmore.Util.WorldUtil;
import fr.fitzche.lgmore.commands.FutureAction;
import fr.fitzche.lgmore.minecraft.ResCheck;

public class RegisterCheck implements ResCheck{
	GameLg game;
	
	public RegisterCheck(GameLg game) {
		this.game = game;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e, PlayerData killer) {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.visionDeath) {
				p.sendMessage(ChatColor.GRAY+ "Le joueur "+ChatColor.RED+ e.getEntity().getName()+ChatColor.GRAY+ " est mort");
			}
		}
		return false;
	}

	@Override
	public String runDeathAction(PlayerDeathEvent e, Player k) {
	
		if (!game.isRegistresActivated) {
			return "";
		}
		String str = "";
		PlayerData victim = Main.strToPlayer.getOrDefault(e.getEntity().getName(), null);
		if (victim == null) {
			return "";
		}
		PlayerData killer = Main.strToPlayer.getOrDefault(k.getName(), null);
		
		if (killer != null && game.getTragic() > 25) {
			
			killer.changeHealth(1);
			str = str + "tragicBonusDemiCoeurTueur -- ";
			
		}
		if (MathUtil.pourcentage(game.getEpic()/6)) {
			k.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6000, 0, false, false));
			
			str = str + "EpicBonusSpeedTueur -- ";
		}
		
		if (victim.camp == Camp.Wolf || (victim.camp != null && victim.considWolf)) {
			if (MathUtil.pourcentage(game.getEpic()/3)) {
				game.broadcoast("Le tueur du loup garou "+victim.getName()+ " est "+k.getName());
				str = str + "EpicBonusIdentLoupTueur -- ";
			}
		}
		return str;
	}

	@Override
	public boolean hide(PlayerDeathEvent e) {
		if (game.roleBrumed) {
			System.out.println("register hide deaths");
		}
		
		
		return game.roleBrumed;
		
	}

	@Override
	public void beforeDie(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int onPlayerDamage(PlayerData attacker, PlayerData attacked) {
		if (attacker.infected && WorldUtil.getTime(Main.server.getWorld("world")).equals("night")) {
			return 20;
		}
		return 0;
	}
	

	@Override
	public void onVoteEvent(VoteEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddTragic(int before, int after, Location loc) {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.getLocation().distance(loc) < 20 && p.visionRegister) {
				p.sendMessage(ChatColor.GRAY+ "Changement de registre proche");
			}
		}
		if (before  < 100 && after >= 100) {
			for (PlayerData p:game.getPlayerAlive()) {
				GameLgUtil.tpAl(p);
			}
		}
		
	}

	@Override
	public void onAddEpic(int before, int after, Location loc) {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.getLocation().distance(loc) < 20 && p.visionRegister) {
				p.sendMessage(ChatColor.GRAY+ "Changement de registre proche");
			}
		}
		if (before  < 100 && after >= 100) {
			game.broadcoast(ChatColor.DARK_RED+"La Nuit tombe, C'est la Pleine Lune. Tous les loups hurlent à la lune");
			game.broadcoast(ChatColor.RED+"Les joueurs mal entourés entendront les hurlements des loups, les rôles seront brouillés pendant 5min, et la nuit tombera précipitament, mais les auras lumineuses resteront éclairer le village");
			Main.world.setTime(14000);
			game.roleBrumed = true;
			game.futuresActions.add(new FutureAction(new BukkitRunnable() {
				
				@Override
				public void run() {
					game.roleBrumed = false;
					
				}
			}, 300));
			for (PlayerData p:game.getPlayerAlive()) {
				if (p.role.getCampOfRole().equals(Camp.Wolf)) {
					game.playSoundWolf(p);
				}
				if (p.aura.equals(Aura.LUMINOUS)) {
					p.auraDiscoverEffetDuration += 60;
				}
			}

		
		}
		
	}

	@Deprecated
	@Override
	public void onAddOrat(int before, int after, Location loc) {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.getLocation().distance(loc) < 20 && p.visionRegister) {
				p.sendMessage(ChatColor.GRAY+ "Changement de registre proche");
			}
		}
		if (before  < 80 && after >= 80) {
			if ((game.timer.temps - (game.timer.getEpisode() - 1) * 1200)>80 && (game.timer.temps - (game.timer.getEpisode() - 1) * 1200) < 1120 && !game.hasMoreVote) {
				game.broadcoast(ChatColor.AQUA+"Un vote Supplémentaire se déclenche");
				game.hasMoreVote = true;
				game.startVote();
			} else if (!game.hasMoreVote){
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
		if (before < 100 && after >= 100) {
			ArrayList<PlayerData> choosen = new ArrayList<PlayerData>();
			choosen.add(GameLgUtil.getAlPlayer(game));
			choosen.add(GameLgUtil.getAlPlayer(game));
			choosen.add(GameLgUtil.getAlPlayer(game));

			game.exposedMulti(choosen, 0);
		}
		
	}

	@Override
	public boolean brume(PlayerDeathEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean checkRes(PlayerDeathEvent e) {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.visionDeath) {
				p.sendMessage(ChatColor.GRAY+ "Le joueur "+ChatColor.RED+ e.getEntity().getName()+ChatColor.GRAY+ " est mort");
			}
		}
		return false;
	}
	@Override
	public String getTypeName() {
		// TODO Auto-generated method stub
		return "Registre Checker";
	}

}
