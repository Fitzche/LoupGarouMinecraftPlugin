package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.InfinityStones.Stone;
import fr.fitzche.lgmore.InfinityStones.StonesType;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;


public class THANOS implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Thanos";
	public Camp camp = Camp.Other;
	

	public boolean hasAll = false;
	
	public boolean hasAllUsed = false;
	
	
	
	public THANOS(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		GameLg game = GameLgUtil.getGameOfPlayer(player, "at thanos creating");
		playerWithRole.team = new Team("Thanos", Camp.Other, game, players, null, "at wolf team creating at == 1200", null, null, true, false, false, false);
		GameLgUtil.getGameOfPlayer(playerWithRole, "at assassin creating").teams.add(playerWithRole.team);	
		
		
		playerWithRole.changeHealth(4);
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.MIND));
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.SOUL));
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.TIME));
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.SPACE));
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.REALITY));
		Main.placeStoneStruct(LocationUtil.getAlLocAroundFarfrom(500, 1, true), new Stone(this, StonesType.POWER));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous êtes Thanos... Oui oui Thanos.... Vous gagner tout seul, pour cela vous pouvez récupérer les pierres d'infinités dans les batiment bonus. Si un joueur en récupère une, vous obtiendrez son pseudo, et vous devrez le tuer pour la récupérer. Chaque pierre vous confère un bonus:"
				+ "\n"+ChatColor.RED+ "Pierre de Réalité:"+ChatColor.DARK_BLUE+" vous pouvez (avec la commande /lg reality) donner un effet de jump boost 2 pendant 3min à tous les autres joueurs dans un rayon de 15 à 30 bloc"
				+ "\n"+ChatColor.YELLOW+"Pierre de L'Esprit: "+ChatColor.DARK_BLUE+"Vous pouvez (avec la commande /lg esprit [nomDuJoueur]) changer l'aura et le camp visible d'un joueur de manière à ce qu'il soit vu comme loup"
				+ "\n"+ChatColor.DARK_GREEN+"Pierre du Temps: "+ChatColor.DARK_BLUE + "Vous pouvez (avec la commande /lg time) avancer le temps de 2min"
				+ "\n"+ChatColor.DARK_PURPLE+"Pierre du Pouvoir: "+ChatColor.DARK_BLUE+"Vous possédez 10% de force supplémentaire"
				+ "\n"+ChatColor.GOLD+"Pierre de L'Ame: "+ ChatColor.DARK_BLUE+"Vous pouvez (avec la commande /lg ame [nomDuJoueur]) afficher l'aura d'un joueur aux yeux de tout le monde (particules autour du joueur)"
				+ "\n"+ChatColor.BLUE+"Pierre de L'espace: "+"Vous pouvez (avec la commande /lg space) obtenir speed 2 pendant 3min"
				+ "\n"+ChatColor.UNDERLINE+"Toutes les Pierres: "+"Vous gagner 20% de résistance, et vous avez la possibilité (avec la commande /lg thanos) d'infliger 5 coeurs à chaque joueurs (50% de chance de se produire pour chaque joueur)");
	}
	public static ItemStack logo = new ItemStack(Material.GOLD_SWORD);

	
	public void giveEffectAllTime() {
		//null
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	
	
	
	
	
	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			System.out.println("nk.1");
			if (playerWithRole == null) {
				System.out.println("effect can't be gived at null player");
			}
			if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
				playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));

			}
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			System.out.println("nk.2");
		}
		
		
	}

	@Override
	public void giveDayEffect() {
		
	}

	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void episodeEffect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEpisodeTrue() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveNightEffectCheck() {
		if (!(playerWithRole.camp.equals(Camp.Wolf)&& playerWithRole.isShooted)) {
			giveNightEffect();
		}
		
	}

	@Override
	public void blind(PlayerData origin) {
		origin.sendMessage(ChatColor.GREEN + "Ce joueur n'est pas un rôle à info");
		
	}

	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return false;
	}
}
