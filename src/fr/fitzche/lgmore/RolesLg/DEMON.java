package fr.fitzche.lgmore.RolesLg;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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

import fr.fitzche.lgmore.RolesLg.Checkers.demonChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class DEMON implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Démon";
	public Camp camp = Camp.Other;
	public GameLg game;
	public HashMap<String, Boolean> toRevive = new HashMap<String, Boolean>();
	
	
	
	public void pacte(PlayerData ply) {
		
	}
	
	public DEMON(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		
		
		this.game = (GameLg) player.game;
		
		game.resCheckers.add(new demonChecker(this));
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		
		
		return (Main.info +ChatColor.BLUE+"Vous devez gagner tout seul avec vos ames damnées. Pour cela, vous pourrez sacrifier 2 coeurs permanents après chaque kill pour ramener l'âme de votre victime qui vous servira. Le joueur réssucité perdra 2 coeurs et écopera de 10% de faiblesse. Si une de vos ame meure, vous regagnerez 1 coeur. Vous commencez à 12 coeurs et pouvez fabriquer une sharpness 4 ");
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
		playerWithRole.changeHealth(4);
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
		
		if (args[0].equals("demonRevive")) {
			if (!toRevive.getOrDefault(args[1], false)) {
				toRevive.put(args[1], true);
			
				playerWithRole.changeHealth(-4);
			}
			
		}
		
		
			
		
		
	}
}
