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
import fr.fitzche.lgmore.RolesLg.Checkers.AncienChecker;
import fr.fitzche.lgmore.RolesLg.Checkers.AraigneeChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;


public class ARAIGNEE implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Araignée";
	public Camp camp = Camp.Other;
	public int powerUsed = 2;
	public boolean resus = false;
	public HashMap<String, Double> percentagePlayers = new HashMap<String, Double>();
	
	public ARAIGNEE(PlayerData player) {
		this.playerWithRole = player;
		ArrayList<PlayerData> players = new ArrayList<PlayerData>();
		players.add(player);
		GameLg game = (GameLg) player.game;
		game.resCheckers.add(new AraigneeChecker(this));
		
		
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez gagner tout seul. Vous posséder, pour chaque autre joueur, une jauge de manipulation. Si cette jauge atteint 100%, le joueur en question perdra 1/2 coeur que vous gagnerez à chaque début d'épisode où vous serez à moins de 20 blocs de lui. "+ "\n"+ 
	"Cette jauge augmentera de 10% à chaque kill de cette personne à moins de 20 blocs de vous et de 0.1% par seconde. De plus, si un joueur manipulé à 100% vient à vous tuer, vous réssuciteré (une seule fois). Vous pouvez également utiliser la commande /lg aveugler [nomDUJoueur] à deux reprises pour aveugler un role à info. Vous pouvez craft une sharpness 4, et êtes vu comme villageois avec une aura lumineuse.");
	}
	public static ItemStack logo = new ItemStack(Material.GOLD_SWORD);

	
	public void giveEffectAllTime() {
		for (PlayerData p:playerWithRole.game.getPlayers()) {
			if (LocationUtil.getDistanceBetween(p, playerWithRole) < 20 && percentagePlayers.getOrDefault(p.getName(), 0.0) < 100) {
				percentagePlayers.put(p.getName(), percentagePlayers.getOrDefault(p.getName(), 0.0) + 0.1);
				if (percentagePlayers.get(p.getName()) >= 100) {
					playerWithRole.sendMessage("Le joueur "+ p.getName() + " vous est manipulable.");
				}
			}
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
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 0, false, false));
		
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
		for (PlayerData p:playerWithRole.game.getPlayers()) {
			if (percentagePlayers.getOrDefault(p, 0.0) >= 100) {
				playerWithRole.sendMessage(Main.exclamation+" Vous volez un demi-coeur à"+ p.getName());
				playerWithRole.changeHealth(1);
				p.sendMessage(Main.exclamation+" L'araignée vous a volé un coeur");
				playerWithRole.changeHealth(-1);
			}
		}
		
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
if (args[0].equals("aveugler")) {
			
			if (sender instanceof Player) {
				Player senderPlayer = (Player) sender;
				PlayerData senderPlayerData = Main.getData(sender);
				if (senderPlayerData != null) {
					GameLg gameOfSender = (GameLg)senderPlayerData.game;
					if (gameOfSender != null) {
						if (senderPlayerData.getName().equals(playerWithRole.getName())) {
							
							
							if (powerUsed == 0) {
								sender.sendMessage(ChatColor.RED+"Il ne vous reste plus assez d'utilisation");
								return;
							}
							Main.getData(args[1]).roleIn.blind(senderPlayerData);
							if (!Main.getData(args[1]).roleIn.isInfoRole()) {
								playerWithRole.sendMessage("Ce n'est pas un role à info");
							}
							powerUsed --;
							gameOfSender.addorat(5, playerWithRole.getLocation());
							return;
						}
					} else {
						sender.sendMessage("Vous devez etre dans une partie pour effectuer cette commande");
						return;
					}
				} else {
					sender.sendMessage("Aucune info ne vous est associé, seul un joueur participant à une partie peut effectuer cette commande");
					return;
				}
				
			} 
		}
		
	}
}
