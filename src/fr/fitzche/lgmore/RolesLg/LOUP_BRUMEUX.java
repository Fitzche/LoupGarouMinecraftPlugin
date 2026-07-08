package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

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
import fr.fitzche.lgmore.RolesLg.Checkers.Brume_Checker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;

public class LOUP_BRUMEUX implements RoleInstance {
	public PlayerData playerWithRole;

	public static Camp camp = Camp.Wolf;
	public String name ="Loup-Garou Brumeux";
	public int wait = 0;
	public int Using = 2;
	public GameLg game;
	public ArrayList<PlayerData> toHide = new ArrayList<PlayerData>();
	
	public LOUP_BRUMEUX(PlayerData player) {
		if (player == null) {
			return;
		}
		this.playerWithRole = player;
		this.game = (GameLg)player.game;
		game.resCheckers.add(new Brume_Checker(this));
	}
	
	public static ItemStack logo = new ItemStack(Material.DIAMOND_SWORD);

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous obtenez Force I la Nuit");
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
		 if (args[0].equals("hidelgbr")) {
				PlayerData commander = Main.getData(args[2]);
				PlayerData target = Main.getData(args[1]);
				
				if (commander == null || target == null) {
					sender.sendMessage("commande invalide");
					return;
				}
				
				if (!commander.getName().equals(playerWithRole.getName())) {
					sender.sendMessage("Votre joueur ne vous permet pas cette commande");
					return;
					
				}
				if (Using < 1) {
					sender.sendMessage(ChatColor.GOLD + "Vous avez déjà utilisé votre pouvoir 2 fois");
					return;
				}
				toHide.add(target);
				Using --;
				commander.sendMessage("Vous utilisez votre pouvoir et la mort du joueur "+target.getName()+" ne sera pas annoncée.");
				
			} 
		
	}

}
