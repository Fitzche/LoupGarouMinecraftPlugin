package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;


public class NEGOCIATEUR implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Négociateur";
	public Camp camp = Camp.Other;
	public ArrayList<PlayerData> killedByNeg = new ArrayList<PlayerData>();
	public boolean solo = false;
	GameLg game;
	
	public NEGOCIATEUR(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		GameLg game = (GameLg)player.game;
		this.game = game;
		
			
		
		
		
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous êtes négociateur, vous gagnez tout seul. Cependant vous pouvez également gagner avec le village ou avec les loups deux conditions: la première est que vous devez avoir passé 10min avec les joueurs encore en vie à la fin, la deuxième est que vous ne devez pas avoir tué un membre du camp restant avec qui ceux-ci ont passé plus de 15min. Vous pouvez pouvez renoncer à la négociation et gagner obligatoirement tout seul avec la commande /lg solo. Vous gagnerez alors 15% de résistance.");
	}
	public static ItemStack logo = new ItemStack(Material.GOLD_SWORD);

	
	public void giveEffectAllTime() {
		if (camp.equals(Camp.Other) && checkCanWin() && !solo) {
			camp = Camp.Uneffective;
		}
		if (!playerWithRole.infected && !playerWithRole.inLove) {
			playerWithRole.camp = this.camp;
		}
	}
	

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	
	
	
	
	@Override
	public void giveNightEffect() {
		
		
		
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

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("solo") && sender.getName().equals(playerWithRole.getName())) {
			playerWithRole.boostR5 += 3;
			this.solo = true;
			this.camp = Camp.Other;
			if (!playerWithRole.infected && !playerWithRole.inLove) {
				playerWithRole.camp = this.camp;
			}
		}
		
	}
	
	public boolean checkCanWin() {
		for (PlayerData p:game.getPlayerAlive()) {
			if (p.timeWithPlayers.get(playerWithRole.getName()) > 600) {
				for (PlayerData p2:killedByNeg) {
					if (p.timeWithPlayers.get(p2.getName()) > 299) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
}
