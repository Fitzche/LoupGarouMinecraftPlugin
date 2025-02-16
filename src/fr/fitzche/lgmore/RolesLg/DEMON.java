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
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Checkers.demonChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;


public class DEMON implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Démon";
	public Camp camp = Camp.Other;
	public Team team;
	
	public ArrayList<PlayerData> pactedPlayers = new ArrayList<PlayerData>();
	
	
	public void pacte(PlayerData ply) {
		
	}
	
	public DEMON(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		GameLg game = GameLgUtil.getGameOfPlayer(player, "at demon creating");
		this.team = new Team("Le démon et ses âmes damnées", Camp.Other, game, players, null, "at wolf team creating at == 1200", null, null, true, false, false, false);
		playerWithRole.team = this.team;
		
		game.teams.add(playerWithRole.team);	
		game.resCheckers.add(new demonChecker(this));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"");
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
