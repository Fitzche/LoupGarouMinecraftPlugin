package fr.fitzche.lgmore.RolesLg;

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
import fr.fitzche.lgmore.Util.PotionUtil;
import fr.fitzche.lgmore.Util.RoleUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_HURLEUR implements RoleInstance {
	public PlayerData playerWithRole;

	public static Camp camp = Camp.Wolf;
	public String name ="Loup Garou Hurleur";
	public int wait = 0;
	public int powerUse = 2;
	public GameLg game;
	
	public LOUP_HURLEUR(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
		
	}
	
	public static ItemStack logo = new ItemStack(Material.DIAMOND_SWORD);

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous obtenez Force I la Nuit. De plus vous pouvez hurler 2 fois (commande /lg hurler): tous les loups aux alentours entendront alors un hurlement, et vous gagnerez 1 demi coeur d'absorbsion pour chaque loups autour.");
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void giveNightEffect() {
		System.out.println("nk.1");
		if (playerWithRole == null) {
			System.out.println("effect can't be gived at null player");
		}
		
		if (wait > 0) {
			wait --;
			return;
		}
		wait = 3;
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
		
		//VOIR SCHEDULER + EFFECT = ERROR ???
		System.out.println("nk.2");
		
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
		if (args[0].equals("hurler")) {
			if (!sender.getName().equals(playerWithRole.getName()) && powerUse > 0) {
				return;
			}
			playerWithRole.player.removePotionEffect(PotionEffectType.ABSORPTION);
			int value = 0;
			powerUse --;
			for (PlayerData p:game.getFalseWolfAlive()) {
				if (p.getLocation().distance(playerWithRole.getLocation()) < 20) {
					game.playSoundWolf(p);
					value++;
					
					
				}
			}
			for (PlayerData p2:RoleUtil.getPlayersWithRole(game, RolesLg.PETITE_FILLE)) {
				game.playSoundWolf(p2);
			}
			int totalValue = -1;
			
			for (int value2 = value; value > 2; value -=2) {
				totalValue ++;
			}
			if (totalValue < 0) {
				totalValue = 0;
			}
			playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, totalValue, false, false));
		}
		
	}

}
