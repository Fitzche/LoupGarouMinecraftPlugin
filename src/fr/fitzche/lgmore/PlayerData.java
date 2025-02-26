package fr.fitzche.lgmore;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.RegisterType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import net.md_5.bungee.api.ChatColor;

public class PlayerData {
	public RoleInstance roleIn;
	public String Name;
	public boolean isOnline = true;
	public ArrayList<PlayerData> canVoted;
	public int vote;
	public PlayerDataLeft left;
	public boolean contamined = false;
	public boolean isShooted = false;
	public PlayerData voted;
	public Player player;
	public RolesLg role;
	public Camp camp;
	public Team team;
	public boolean hasRes = false;
	public boolean inLove;
	public boolean infected;
	public boolean inLife;
	public boolean relive;
	public Inventory deathInventory;
	public int boostS5;
	public int boostR5;
	public Aura aura;
	public int auraDiscoverEffetDuration = 0;
	public boolean instantDeath;
	public boolean grimed = false;
	public int numberOfKill = 0;
	public ScoreboardLg board;
	public HashMap<String, Integer> timeWithPlayers = new HashMap<String, Integer>();
	public HashMap<Player, Boolean> hasStrenghtAgainst = new HashMap<Player, Boolean>();
	public boolean canAccuse = false;
	public SpecialBlock lastVoteOpen;
	public RegisterType favRegister;
	public int bienfaisance = 0;
	public boolean toEscape = false;
	public boolean visionDeath = false;
	public boolean visionRegister = false;
	
	
	
	public boolean hasSpace = false;
	public boolean hasSoul = false;
	public boolean hasPower = false;
	public boolean hasTime = false;
	public boolean hasReality = false;
	public boolean hasMind = false;
	
	
	public boolean hasSpaceUsed = false;
	public boolean hasSoulUsed = false;
	public boolean hasPowerUsed = false;
	public boolean hasTimeUsed = false;
	public boolean hasRealityUsed = false;
	public boolean hasMindUsed = false;
	
	
	
	public PlayerData(Player player) {
		
		canVoted = new ArrayList<PlayerData>();
		this.vote = 0;
		boostR5 = 0;
		this.boostS5 = 0;
		//System.out.println("creation joueur");
		this.player = player;
		//System.out.println("1");
		this.Name = player.getName();
		//System.out.println("2");

		deathInventory = Bukkit.createInventory(null, 36, "Inventory");
		
		this.infected = false;
		//System.out.println("3");

		this.inLove = false;
		//System.out.println("4");
		
		this.relive = false;

		this.inLife = true;
		
		//System.out.println("5");

	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setMaxHealth(double h) {
		if (isOnline) {
			this.player.setMaxHealth(h);
		} else {
			this.left.life= h;
		}
	}
	
	public void setHealth(double h) {
		if (isOnline) {
			this.player.setHealth(h);
		}
	}
	
	
	public double getHealth() {
		if (isOnline) {
			return this.player.getHealth();
		} else {
			return 0;
		}
		
	}
	
	public void addStone(StonesType type) {
		switch (type) {
		case MIND:
			this.hasMind = true;
			sendMessage(ChatColor.YELLOW+"Pierre de L'Esprit: "+ChatColor.DARK_BLUE+"Vous pouvez (avec la commande /lg esprit [nomDuJoueur]) changer l'aura et le camp visible d'un joueur de manière à ce qu'il soit vu comme loup");
			break;
		case POWER:
			this.hasPower = true;
			this.boostS5+=3;
			sendMessage(ChatColor.DARK_PURPLE+"Pierre du Pouvoir: "+ChatColor.DARK_BLUE+"Vous possédez 10% de force supplémentaire");
			break;
		case REALITY:
			this.hasReality = true;
			sendMessage(ChatColor.RED+ "Pierre de Réalité:"+ChatColor.DARK_BLUE+" vous pouvez (avec la commande /lg reality) donner un effet de jump boost 2 pendant 3min à tous les autres joueurs dans un rayon de 15 à 30 bloc");
			break;
		case SOUL:
			this.hasSoul = true;
			sendMessage("Pierre de L'Ame: "+ ChatColor.DARK_BLUE+"Vous pouvez (avec la commande /lg ame [nomDuJoueur]) afficher l'aura d'un joueur aux yeux de tout le monde (particules autour du joueur)");
			break;
		case SPACE:
			this.hasSpace = true;
			sendMessage(ChatColor.BLUE+"Pierre de L'espace: "+"Vous pouvez (avec la commande /lg space) obtenir speed 2 pendant 3min");
			break;
		case TIME:
			this.hasTime = true;
			sendMessage(ChatColor.DARK_GREEN+"Pierre du Temps: "+ChatColor.DARK_BLUE + "Vous pouvez (avec la commande /lg time) avancer le temps de 2min");
			break;
		default:
			break;
		
		}
		if (role.equals(RolesLg.THANOS)&& hasMind && hasPower&&hasReality&&hasSoul&&hasSpace&&hasTime) {
			THANOS than = (THANOS) roleIn;
			than.hasAll = true;
			than.playerWithRole.boostS5 +=3;
		}
		sendMessage(ChatColor.DARK_PURPLE+"Vous avez réçu la pierre de "+type.getName());
	}
	
	
	public double getMaxHealth() {
		if (isOnline) {
			return this.player.getMaxHealth();
		} else {
			return 0;
		}
		
	}
	public Location getLocation() {
		
		if (this.isOnline) {
			return this.player.getLocation();
		}
		return new Location(Main.plug.getServer().getWorld("world"), 10000, 10000, 10000);
	}
	
	public void addPotionEffect(PotionEffect effect) {
		if (isOnline) {
			this.player.addPotionEffect(effect);
		}
		
	}
	public void applyLgRole(RolesLg role) { 
		this.camp = role.getCampOfRole();
		this.role = role;
		this.aura = role.aura;
		
	}
	
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}
	
	public RolesLg getLgRole() {
		return this.role;
	}
	
	
	public void askVoted() {
		
		this.player.sendMessage(ChatColor.GOLD+"Vous pouvez voter pour le joueur de votre choix en tapant sur une urne de vote comportant encore des votes"+ "\n"+ " Le joueur le plus voté subira 15s de poison et perdra 1 coeur de façon permanente");
		this.vote = 0;
		this.voted = null;
	}
	
	
	public void changeHealth(double h) {
		if (this.isOnline) {
			this.player.setMaxHealth(this.player.getMaxHealth() + h);
		} else {
			this.left.life = this.left.life + h;
		}
	}
	
	public boolean isInGame(String spec) {
		if (GameLgUtil.getGameOfPlayer(this, ("at 89 of PlayerData "+spec)) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isInThisGame(GameLg gm) {
		for (PlayerData ply:gm.players) {
			if (this.equals(ply)) {
				return true;
			}
		}
		return false;
		
	}
	
}
