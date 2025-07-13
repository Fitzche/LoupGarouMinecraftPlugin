package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class SORCIER implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Sorcier";
	public Camp camp = Camp.Other;
	public GameLg game;
	public Location labLoc;
	
	public SORCIER(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
		if (MathUtil.pourcentage(50)) {
			
			player.sendMessage(ChatColor.GOLD+"Vous êtes solitaire, vous pouvez donc craft une épée sharpness 4 et vous obtenez un grappin");
			ArrayList<PlayerData> players = new ArrayList<PlayerData>();
			players.add(player);

		} else {
			this.camp = Camp.Villager;
			player.sendMessage(ChatColor.GREEN+"Vous êtes villageois");
		}
		
		this.labLoc = LocationUtil.getAlLocAroundFarfrom(400, 2, true, game.world, game);
		Main.placeCauldronStruct(labLoc, game);
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Le sorcier a 50% de gagner tout seul, et 50% de gagner avec le village, il peut créer des potions avec certains matériaux (1 gap, 3blocs de lapis et 30 redstone --> potion tp) (1 gap, 30 lapis, et 10 plumes --> potion révélation d'aura) (2 gaps, 5 chairs putréfiés, 3 fils et 3 silexs --> potion de paralysie) . Ces potions ont des effets divers, ce sont celles que des joueurs normaux trouverait dans des batiments bonus. Votre laboratoire se trouve en "+ this.labLoc.getBlockX() + ";"+ this.labLoc.getBlockZ());
	}
	public static ItemStack logo = new ItemStack(Material.WHEAT);

	
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
		
		
		
	}

	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
}
