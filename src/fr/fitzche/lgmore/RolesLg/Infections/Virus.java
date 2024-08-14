package fr.fitzche.lgmore.RolesLg.Infections;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.Camp;
import fr.fitzche.lgmore.RolesLg.IDIOT_DU_VILLAGE;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.Servant_des_loups;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;

public class Virus {
	public VirusType type;
	public PlayerData owner;
	public GameLg game;
	public int removed;
	public int typeInt;
	public PlayerData infecter;
	public int time;
	public int parasiteIntensity = 10;
	public boolean stopped = false;
	public boolean poisonGuerison = false;
	HashMap<PlayerData , Integer> virusAdvencements = new HashMap<PlayerData, Integer>();
	
	@Deprecated
	public void end() {
		switch (type) {
		case ENDED:
			break;
		case EPIDEMIE:
			owner.player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 300, 0, false, false));
			owner.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, MathUtil.generateAlInt(2400, 12000), 0, false, false));

			break;
		case PARASITE:
			int result = MathUtil.probasFivePossib(20, 20, 20, 20, 20);
			if (owner.inLove) {
				result++;
			}
			switch (result) {
			case 1:
				
				ArrayList<PlayerData> player = new ArrayList<PlayerData>();
				player.add(owner);
				owner.team.remove(owner);
				owner.team = new Team("solo", Camp.TEAM, game, player, null, "at parasite of virus", null, null, true, false, false, false);
				owner.sendMessage(ChatColor.DARK_PURPLE+ "Le parasite vous a renté fou, vous devez gagner tout seul, il vous rend cependant plus résistant (2 coeur)");
				owner.player.setMaxHealth(owner.player.getMaxHealth() + 4);
				break;
			case 2:
				owner.player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 12000, 0));
				owner.player.setMaxHealth(owner.player.getMaxHealth() - 4);
				owner.sendMessage(ChatColor.DARK_PURPLE+"Le Parasite vous a affaibli");
				break;
			case 3:
				owner.sendMessage(ChatColor.DARK_PURPLE+ "Le parasite a fait de vous le serviteur des loup garou: "+ "\n");
				owner.roleIn = new Servant_des_loups(infecter, owner);
				owner.role = RolesLg.SERVANT_DES_LOUPS;
				break;
			case 4:
				owner.player.setMaxHealth(owner.player.getMaxHealth() - 3);
				owner.sendMessage(ChatColor.DARK_GREEN+"Le parasite vous affaiblie petit à petit, vous perdrez donc 1/2 coeur de manière permanente toutes les 3 minutes tant que le loup garou alchimiste n'est pas mort");
				Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plug, new BukkitRunnable() {

					@Override
					public void run() {
						owner.player.setMaxHealth(owner.player.getMaxHealth() - 1);
						
					}
					
				}, 0, 3600);
				break;
			case 5:
				owner.sendMessage(ChatColor.DARK_GREEN+"Le parasite provoque chez vous une crise de démence, vous perdez donc vos pouvoir, et le village vous considère comme fou et prend pitié de vous. Vous gardez cependant votre camp d'origine");
				owner.roleIn = new IDIOT_DU_VILLAGE(owner);
				owner.role = RolesLg.IDIOT_DU_VILLAGE;
				owner.aura = Aura.DANGEROUS;
				break;
			case 6:
				owner.sendMessage(ChatColor.DARK_GREEN+"Le parasite provoque chez vous une crise de démence, vous perdez donc vos pouvoir, et le village vous considère comme fou et prend pitié de vous. Vous gardez cependant votre camp d'origine");
				owner.roleIn = new IDIOT_DU_VILLAGE(owner);
				owner.role = RolesLg.IDIOT_DU_VILLAGE;
				owner.aura = Aura.DANGEROUS;
				break;
			}
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
			owner.sendMessage(ChatColor.GREEN+"Vous avez été empoisonné par le parasite, vous perdrez donc 1/2 coeur toutes les 5min tant qu'il n'est pas mort, de plus si vous le tuez vous récupererez votre vie perdue, l'alchimiste est "+infecter.Name);
			Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plug, new BukkitRunnable() {

				@Override
				public void run() {
					owner.player.setMaxHealth(owner.player.getMaxHealth() - 1);
					removed ++;
					
				}
				
			}, 4800, 4800);
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
					if (stopped&&poisonGuerison) {
						poisonGuerison = false;
						owner.player.setMaxHealth(owner.player.getMaxHealth() + removed);
					}
					break;
				default:
					break;
				
				}
				
			}
			
		}, 20, 20);
	}
	
	
	
}
