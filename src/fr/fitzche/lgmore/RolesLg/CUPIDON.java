package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
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

import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;


public class CUPIDON implements RoleInstance{
	public PlayerData playerWithRole;
	public boolean powerUsed;
	
	public Camp camp = Camp.Love;
	public String name ="CUPIDON";
	public PlayerData p1;
	public PlayerData p2;
	public PlayerData p3;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.camp.getColor() + name;
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Main.info +ChatColor.BLUE+"Vous etes Cupidon, "
				+ "\n"+ "Vous devez gagner tous seul ou avec le couple, que vous choisirez avant 25minutes avec la commande /lg couple (choisissez ensuite les membres du couple un par un) . Vous possédez également un arc enchanté "+ChatColor.UNDERLINE+" Punch I PowerII";
	}
	
	public CUPIDON(PlayerData player) {
		this.playerWithRole = player;
		
	}

	@Deprecated
	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		ItemStack arc = new ItemStack(Material.BOW, 1);
		
		arc.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		arc.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		
		player.player.getInventory().addItem(arc);
		
		Bukkit.getScheduler().runTaskLater(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (p1 == null || p2 == null) {
					GameLg gm = (GameLg)playerWithRole.game;
					PlayerData one = MathUtil.getAlPlayer(gm);
					createCouple(one, MathUtil.getAlPlayer(gm, one), playerWithRole);
				}
				
			}
			
		
		
			
		}, 6000);
		
	}
	
	public void createCouple(PlayerData player, PlayerData player2, PlayerData Cupidon) {
		System.out.println("create couple");
		if (p1 != null && p2!=null) {
			return;
		}
		p1 = player;
		p2 = player2;
		p1.camp = Camp.Love;
		p2.camp = Camp.Love;
		player.inLove = true;
		player2.inLove = true;
		System.out.println("proba of "+ MathUtil.pourcentage(((GameLg)playerWithRole.game).probasEvents.get("Trouple")));
		if (MathUtil.pourcentage(((GameLg)playerWithRole.game).probasEvents.get("Trouple"))) {
			PlayerData third = ((GameLg)playerWithRole.game).getPlayerAlive().get(MathUtil.generateAlInt(0, ((GameLg)playerWithRole.game).getPlayerAlive().size()));
			System.out.println("trouple");
			
			p3 = third;
			p3.camp = Camp.Love;
			
			player.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET  +ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ChatColor.BLUE+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /lg don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player2.Name);
			player2.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET  +ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ChatColor.BLUE+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /lg don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ third.Name);
			third.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET  +ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ChatColor.BLUE+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /lg don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player.Name);
			Cupidon.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET  +ChatColor.LIGHT_PURPLE +"Vous avez mis "+ player.Name + ", "+third.Name+" et "+ player2.Name + " en couple");

			third.inLove = true;


		} else {
			System.out.println("no trouple");
			player.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET+ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ChatColor.BLUE+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /lg don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player2.Name);
			player2.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET+ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ChatColor.BLUE+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /lg don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player.Name);
			Cupidon.sendMessage(ChatColor.BOLD + ""+ChatColor.RED+"♡ "+ChatColor.RESET+ChatColor.LIGHT_PURPLE +"Vous avez mis "+ player.Name + " et "+ player2.Name + " en couple");
		}
		
		
		
		System.out.println("end creating couple");
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void giveNightEffect() {
		
		
	}

	@Override
	public void giveDayEffect() {
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
	public void startSpecialEvent() {
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
