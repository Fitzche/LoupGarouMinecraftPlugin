package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.RolesLg.Checkers.TrackRes;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class TRAQUEUR implements RoleInstance {
	public PlayerData playerWithRole;
	public String name ="Traqueur";
	public Camp camp = Camp.Villager;
	public GameLg game;
	public PlayerData tracked;
	
	
	public TRAQUEUR(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg)player.game;
		this.game.resCheckers.add(new TrackRes(this));
		
		
	}
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Villageois, pour cela vous pouvez choisir un joueur à traquer avec la commande /lg traquer [nomDuJoueur] (rayon de 20 blocs). Vous pourrez alors connaitre ses coordonnées avec la commande /lg traque. Vous obtiendrez également son nombre de kill. Vous pourrez choisir un autre joueur à traquer si le traqué vient à mourir ");
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
		this.tracked = null;
		playerWithRole.sendMessage(ChatColor.GOLD+"Vous perdez votre traque. ");
		return true;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("traquer")) {
			if (args.length < 2) {
				System.out.println("not enought argument in command traquer");
				return;
			}
			if (sender  instanceof Player) {
				Player player = (Player) sender;
				PlayerData plyD = Main.strToPlayer.getOrDefault(sender.getName(), null);
				if (plyD != null && plyD.getName().equals(playerWithRole.getName())) {
					TRAQUEUR traqueur = (TRAQUEUR) plyD.roleIn;
					if (traqueur.tracked == null ) {
						if (PlayerUtil.getPlayer(args[1]) != null && PlayerUtil.getPlayer(args[1]).getLocation().distance(player.getLocation()) < 20) {
							traqueur.tracked = Main.strToPlayer.getOrDefault(args[1], null);
							traqueur.playerWithRole.sendMessage("Vous traquez "+ args[1]);
						}
					}
				}
			}
		} else if (args[0].equals("traque")) {
			if (args.length < 1) {
				System.out.println("not enought argument in command traque");
				return;
			}
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (player != null && sender.getName().equals(playerWithRole.getName())) {
					
					if (this.tracked != null) {
						player.sendMessage(ChatColor.GOLD + "Le joueur traqué "+ this.tracked.getName() + " se trouve en "+LocationUtil.toString(this.tracked.getLocation())+ "; son nombre de kill s'élève à "+ this.tracked.numberOfKill);
					} else {
						player.sendMessage("Vous ne traquez personne");
					}
				}
			}
		}
		
	}
}
