package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.commands.FutureAction;
import net.md_5.bungee.api.ChatColor;

public class VOYANTE implements RoleInstance{
	public PlayerData playerWithRole;
	public boolean powerUsed;
	public String name ="Voyante";
	public boolean isBlind= false;

	public VOYANTE(PlayerData player) {
		this.playerWithRole = player;
		this.powerUsed = false;
		
	}
	
	
	
	@Override
	public String getName() {
		return (this.camp.getColor() + name);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	
	
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Villageois, pour cela vous pouvez connaitre un role de joueur par épisode avec la commande /lg voir [nomDuJoueur], votre écoperez cependant d'un malus (5 coeurs de dégat et 5min de faiblesse) si vous faites erreur et que vous espionnez un villageois");
	}
	
	public void giveEffectAllTime() {
		//null
	}
	
	public void giveRoleEffectAndItem(PlayerData player) {
		
	}
	public static ItemStack logo = new ItemStack(Material.ENCHANTMENT_TABLE);
	public static Camp camp = Camp.Villager;
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
		
		if (isBlind) {
			isBlind = false;
			this.playerWithRole.sendMessage(ChatColor.AQUA +"Vous ne pouvez pas utiliser votre pouvoir à cet épisode à cause de votre aveuglement");
			return;
		} else {
			this.powerUsed = false;
			this.playerWithRole.sendMessage(ChatColor.AQUA +"Vous pouvez à nouveau utiliser votre pouvoir");

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
		origin.sendMessage(ChatColor.GOLD + "Ce joueur est Voyante, il ne pourra pas observer de joueur durant le prochain episode");
		isBlind = true;
		this.playerWithRole.sendMessage(ChatColor.GOLD+"Vous avez été aveuglé, vous ne pourrez donc pas espionner un joueur au prochain épisode");
	}



	@Override
	public boolean isInfoRole() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("voir")) {
			
			Player player = (Player) sender;
			
			if (!sender.getName().equals(playerWithRole.getName())) {
				
				return;
			}
			if (args.length < 2) {
				sender.sendMessage("Erreur de commande: /lg voir [nomDuJoueur]");
			}
			
			
			if (powerUsed) {
				player.sendMessage(ChatColor.BLACK + "vous avez déjà utilisé votre pouvoir !!!");
				return;
			
			}
			PlayerData ply = Main.getData(args[1]);
			
			if (ply.camp.equals(Camp.Villager)) {
				player.damage(10);
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 6000, 0, false, false));
				ply.boostS5 -= 3;
				((GameLg)ply.game).futuresActions.add(new FutureAction(new BukkitRunnable() {

					@Override
					public void run() {
						ply.boostS5 += 3;
						
					}
					
				}, 300));
		
				player.sendMessage(ChatColor.BLUE + ply.Name + " est "+ ply.role);
			}  else {
				Location loc = ply.getLocation();
				player.sendMessage(ChatColor.BLUE + ply.Name + " est "+ ply.role.getName()+ ", il se trouve en "+loc.getBlockX()+ ", "+loc.getBlockY() + ", " + loc.getBlockZ()+ "; (x/y/z)");

			}
			
			powerUsed = true;
			
			
			
			
			
			
			
			
		}
		
	}
}
