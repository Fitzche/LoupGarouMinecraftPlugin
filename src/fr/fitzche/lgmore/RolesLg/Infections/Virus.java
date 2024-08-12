package fr.fitzche.lgmore.RolesLg.Infections;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;

public class Virus {
	public VirusType type;
	public PlayerData owner;
	public GameLg game;
	public int typeInt;
	public PlayerData infecter;
	public int time;
	public int parasiteIntensity = 10;
	HashMap<PlayerData , Integer> virusAdvencements = new HashMap<PlayerData, Integer>();
	
	
	public void end() {
		switch (type) {
		case ENDED:
			break;
		case EPIDEMIE:
			owner.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0, false, false));
			owner.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, MathUtil.generateAlInt(2400, 12000), 0, false, false));

			break;
		case PARASITE:
			
			break;
		case POISON:
			break;
		default:
			break;
		
		}
		this.type = VirusType.ENDED;
	}
	@Deprecated
	public Virus(VirusType type, PlayerData owner, PlayerData infecter, int time) {
		this.game = GameLgUtil.getGameOfPlayer(owner, "at virus creation");
		this.owner = owner;
		this.type = type;
		this.infecter = infecter;
		this.time = time;
		
		
		
		for (PlayerData p:game.getPlayerAlive()) {
			virusAdvencements.put(p, 0);
			
		}
		virusAdvencements.put(owner, -100000);
		
		switch (type) {
		case EPIDEMIE:
			typeInt = 0;
			for (PlayerData p:game.getPlayerAlive()) {
				if (LocationUtil.getDistanceBetween(owner, p) < 50 && p.camp.equals(Camp.Wolf)) {
					p.sendMessage("Attention, le joueur "+ owner.Name + " est infecté, éloignez vous en");
				}
			}
			Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					owner.sendMessage("Vous avez été infecté, vous serez contagieux pendant 5 minutes (moins de 15 blocs)");
					
				}
				
			}, 1200);
			Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					end();
				}
				
			}, 6000);
			break;
		case PARASITE:
			typeInt = 1;
			owner.sendMessage("Vous avez été parasité, au bout de 10 min le parasite vous transformera en lg servant, ce qui vous fera passer loup garou sans vous octroiyer d'effet, de plus si le lg alchimiste meurt, vous mourrez à sa place. Vous pouvez transmettre le parasite à un autre joueur en le tuant, celui-ci sera ressucité avec votre parasite.");
			Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					end();
				}
				
			}, 12000);
			break;
		case POISON:
			typeInt = 2;
			break;
		default:
			break;
		
		}
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				switch (type) {
				case EPIDEMIE:
					for (PlayerData p:game.getPlayerAlive()) {
						if (LocationUtil.getDistanceBetween(owner, p) < 15) {
							virusAdvencements.put(p, virusAdvencements.get(p) + 1);
							if (virusAdvencements.get(p)> 30) {
								if (MathUtil.pourcentage(virusAdvencements.get(p) /2)) {
									Virus virus = new Virus(VirusType.EPIDEMIE, p, infecter, time);
								}
							}
						}
					}
					break;
				case PARASITE:
					break;
				case POISON:
					break;
				default:
					break;
				
				}
				
			}
			
		}, 20, 20);
	}
	
	
	
}
