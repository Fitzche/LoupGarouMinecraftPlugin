package fr.fitzche.lgmore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import StarParty.RoleStar;
import StarParty.RolesStar;
import StarParty.StarParty;
import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Lg.GameNote;
import fr.fitzche.lgmore.Lg.PlayerNote;
import fr.fitzche.lgmore.Lg.RegisterType;
import fr.fitzche.lgmore.Lg.SpecialsBlock.SpecialBlock;

import fr.fitzche.lgmore.RolesLg.Aura;
import fr.fitzche.lgmore.RolesLg.RolesLg;
import fr.fitzche.lgmore.RolesLg.THANOS;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import fr.fitzche.lgmore.bedwars.BedTeam;
import fr.fitzche.lgmore.bedwars.Bedwars;
import fr.fitzche.lgmore.clocktower.ClockRole;
import fr.fitzche.lgmore.clocktower.ClockRoleData;
import fr.fitzche.lgmore.clocktower.ClockTower;
import fr.fitzche.lgmore.custom.CustomGame;
import fr.fitzche.lgmore.custom.CustomRole;
import fr.fitzche.lgmore.custom.RoleSet;
import fr.fitzche.lgmore.minecraft.PlayerDataLeft;
import fr.fitzche.lgmore.scoreboard.GameBoard;
import fr.fitzche.lgmore.scoreboard.ScoreboardLg;
import fr.fitzche.lgmore.scoreboard.StarBoard;
import net.md_5.bungee.api.ChatColor;

public class PlayerData implements Serializable{
	
	
	private static final long serialVersionUID = -5021567793654652540L;
	public String Name;
	public boolean isOnline = true;
	public boolean isInLgGame = false;
	public ArrayList<GameNote> notes = new ArrayList<GameNote>();
	public int xp = 0;
	public int feathers = 0;
	public boolean hoster = false;
	public boolean absoluteOp = false;
	public transient Location rejoinLoc;
	
	
	
	public transient BedTeam bedTeam;
	
	
	
	
	public transient StarParty starParty;
	public transient RolesStar roleStar;
	public transient RoleStar roleSta;
	
	
	
	
	
	
	public transient Game game;
	public transient GameBoard board;
	
	public transient RoleInstance roleIn;
	public transient Camp appCamp;
	public transient Inventory leftInv = null;
	
	public transient ArrayList<PlayerData> canVoted;
	public transient int vote;
	public transient PlayerDataLeft left;
	public transient boolean contamined = false;
	public transient boolean isShooted = false;
	public transient boolean pacteAccept = false;
	public transient PlayerData voted;
	public transient Player player;
	public transient RolesLg role;
	public transient Camp camp;
	
	public transient boolean hasRes = false;
	public transient boolean inLove = false;
	public transient boolean infected;
	public transient boolean inLife;
	public transient boolean relive;
	public transient Inventory deathInventory;
	public transient int boostS5;
	public transient int boostR5;
	public transient Aura aura;
	public transient int auraDiscoverEffetDuration = 0;
	public transient boolean instantDeath;
	public transient boolean grimed = false;
	public transient int numberOfKill = 0;
	public transient HashMap<String, Integer> timeWithPlayers = new HashMap<String, Integer>();
	public transient HashMap<Player, Boolean> hasStrenghtAgainst = new HashMap<Player, Boolean>();
	public transient boolean canAccuse = false;
	public transient SpecialBlock lastVoteOpen;
	public transient RegisterType favRegister;
	public transient int bienfaisance = 0;
	public transient boolean toEscape = false;
	public transient boolean visionDeath = false;
	public transient boolean visionRegister = false;
	
	public transient boolean considWolf = false;
	public transient boolean considVill = false;
	
	public transient boolean hasSpace = false;
	public transient boolean hasSoul = false;
	public transient boolean hasPower = false;
	public transient boolean hasTime = false;
	public transient boolean hasReality = false;
	public transient boolean hasMind = false;
	
	
	public boolean hasSpaceUsed = false;
	public boolean hasSoulUsed = false;
	public boolean hasPowerUsed = false;
	public boolean hasTimeUsed = false;
	public boolean hasRealityUsed = false;
	public boolean hasMindUsed = false;
	public RolesLg settedRole = null;
	
	
	public ClockTower clockGame;
	public ClockRole clockRole;
	public ClockRoleData dataClock;
	public boolean isLeure = false;
	public boolean isPoisonned = false;
	public boolean isProtected = false;
	public int gojoIt = 150;
	public double gojoLoop = 5;
	
	
	public PlayerData coupleL;
	public void clearLgGameVar() {
		coupleL = null;
		settedRole = null;
		hasSpace = false;
		hasSoul = false;
		hasPower = false;
		hasTime = false;
		hasReality = false;
		hasMind = false;
		considWolf = false;
		considVill = false;
		
		clockGame = null;
		
		
		hasSpaceUsed = false;
		hasSoulUsed = false;
		hasPowerUsed = false;
		hasTimeUsed = false;
		hasRealityUsed = false;
		hasMindUsed = false;
		leftInv = null;
		
		canVoted = new ArrayList<PlayerData>();
		vote = 0;
		left = null;
		contamined = false;
		isShooted = false;
		pacteAccept = false;
		voted = null;
		
		role = null;
		camp = null;
		
		
		bedTeam = null;
		
		hasRes = false;
		inLove = false;
		infected = false;
		inLife = true;
		relive = false;
		deathInventory = null;
		boostS5 = 0;
		boostR5 = 0;
		aura = null;
		auraDiscoverEffetDuration = 0;
		instantDeath = false;
		grimed = false;
		numberOfKill = 0;
		board = null;
	
		timeWithPlayers = new HashMap<String, Integer>();
		hasStrenghtAgainst = new HashMap<Player, Boolean>();
		canAccuse = false;
		lastVoteOpen = null;
		favRegister = null;
		bienfaisance = 0;
		toEscape = false;
		visionDeath = false;
		visionRegister = false;
		this.appCamp = null;
		this.rejoinLoc = null;
	}
	
	
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
	public void setDisplayName() {
		String setted = "";
		if (this.game != null && role != null && game instanceof GameLg) {
			GameLg game = (GameLg) this.game;
			if (game.statut.equals(GameStatut.IN_GAME) && game.displayedRoles) {
				if (role.getCampOfRole().equals(Camp.Love) ) {
					setted = Camp.Villager.getColor() + setted;
				} else {
					if (game.swapper) {
						setted = camp.getColor() + setted;
					} else {
						setted = role.getCampOfRole().getColor() + setted;
					}
					
				}
				
			}
		}
		
		
		if (game != null && game instanceof Bedwars&& bedTeam != null) {
			setted = bedTeam.getChatColor() + setted;
		}
		
		setted = setted + getName();
		setDisplayName(setted);
	}
	
	public void setDisplayName(String setted) {
		if (isOnline) {
			try {
				player.setDisplayName(setted);
				player.setCustomName(setted);
				player.setCustomNameVisible(true);
				player.setPlayerListName(setted);
				if (game != null) {
					for (PlayerData p: game.getPlayers()) {
						if (p.isOnline) {
							PlayerUtil.setTargetToColor(p.player, player, role.getCampOfRole().getColor());
						}
					
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void setMaxHealth(double h) {
		if (isOnline) {
			this.player.setMaxHealth(h);
		} else {
			if (this.left != null) {
				this.left.life= h;
			}
			
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
		return new Location(this.game.getWorld(), 10000, 10000, 10000);
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
		if (isOnline) {
			this.player.sendMessage(message);
		}
		
	}
	
	public int getXp() {
		return xp;
		
	}
	public void addXp(int i) {
		this.xp += i;
	}
	public void setXp(int i) {
		this.xp = i;
	}
	public void removeXp(int i) {
		if (i > xp ) {
			xp = 0;
		} else {
			xp -= i;
		}
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
		if (this.game == null) {
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
	
	public double getWinRate() {
		int s = 0;
		if (notes.size() == 0) {
			s ++;
		}
		return getNumberOfWin() / (notes.size() + s);
	}
	
	public double getWinRate(int last) {
		int x = last;
		
		if (notes.size() < last) {
			x = notes.size() - 1;
		}
	
		ArrayList<GameNote> recents = new ArrayList<GameNote>();
		for (GameNote note:notes) {
			if (notes.indexOf(note) <= notes.size() - 1 && (notes.indexOf(note) >= notes.size() - x -1)) {
				recents.add(note);
			}
		}
		if (recents.size() <= 0) {
			return 0.0;
		}
		return getNumberOfWin(x) /recents.size();
	}
	public String getWinRateString(int last) {
		int x = last;
		
		if (notes.size() < last) {
			x = notes.size() - 1;
		}
	
		ArrayList<GameNote> recents = new ArrayList<GameNote>();
		for (GameNote note:notes) {
			if (notes.indexOf(note) <= notes.size() - 1 && (notes.indexOf(note) >= notes.size() - x -1)) {
				recents.add(note);
			}
		}
		if (recents.size() <= 0) {
			return "0 parties jouée";
		}
		
		
		return ChatColor.AQUA+ ""+ChatColor.BOLD+ ""+getNumberOfWin(x) +ChatColor.RESET+ChatColor.WHITE+" victoires pour les "+ChatColor.AQUA+ChatColor.BOLD+recents.size()+ChatColor.RESET+ChatColor.WHITE+ " parties les + récentes";
	}
	
	public int getNumberOfWin(int last) {
		int x = last;
		int win = 0;
		if (notes.size() > last) {
			x = notes.size();
		}
		List<GameNote> not = notes;
		ArrayList<GameNote> recents = new ArrayList<GameNote>();
		for (GameNote note:notes) {
			if (notes.indexOf(note) <= notes.size() - 1 && (notes.indexOf(note) >= notes.size() - x -1)) {
				recents.add(note);
			}
		}
		for (GameNote note:recents) {
			if (note.winners.contains(getName())) {
				win ++;
			}
		}
		return win;
	}
	
	public int getNumberOfWin() {
		int x = 0;
		for (GameNote note:notes) {
			if (note.winners.contains(Name)) {
				x ++;
			}
		}
		return x;
	}
	
	public boolean isFree() {
		if (starParty != null) {
			sendMessage("Vous êtes déjà en partie (starParty)");
		} 
		if (game != null) {
			sendMessage("Vous êtes déjà en partie (Game Classique)");
		}
		
		return !(starParty != null || game != null);
	}


	public void setCustomRole(CustomGame game, CustomRole customRole, RoleSet set) {
		set.setRolesOfPlayer(this.Name, customRole);
		
		sendMessage(Main.info+" Vous êtes "+ customRole.name() + " et appartenez au camp "+ customRole.campName());
		customRole.setPlayer(this);
		customRole.attribution();
		set.setVictoryOfPlayer(this.Name, customRole.campName());
		
	}
	
	
	
	
}
