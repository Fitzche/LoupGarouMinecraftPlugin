package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;

import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import net.minecraft.server.v1_8_R1.EnchantmentArrowDamage;

public class CUPIDON implements RoleInstance{
	public PlayerData playerWithRole;
	public boolean powerUsed;
	Team couple;
	public Camp camp = Camp.Love;
	public String name ="CUPIDON";

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
		return ChatColor.DARK_BLUE+"Vous etes Cupidon, "
				+ "\n"+ "Vous devez gagner tous seul ou avec le couple, que vous choisirez avant 25minutes avec la commande /lg couple . Vous possédez également un arc enchanté "+ChatColor.UNDERLINE+" Punch I PowerII";
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
				if (couple == null) {
					GameLg gm = GameLgUtil.getGameOfPlayer(playerWithRole, "at run of giveRoleAndEffect Cupidon");
					PlayerData one = MathUtil.getAlPlayer(gm);
					createCouple(one, MathUtil.getAlPlayer(gm, one), playerWithRole);
				}
				
			}
			
		
		
			
		}, 6000);
		
	}
	
	public void createCouple(PlayerData player, PlayerData player2, PlayerData Cupidon) {
		System.out.println("create couple");
		if (this.couple != null) {
			return;
		}
		System.out.println("couple null before creating");
		
		//FOR 2 MEMBER OF COUPLE
		ArrayList<PlayerData> members = new ArrayList<PlayerData>();
		members.add(player2);
		player2.team.remove(player2);
		members.add(player);
		player.team.remove(player);
		members.add(Cupidon);
		
		ArrayList<PlayerData> couple = new ArrayList<PlayerData>();
		couple.add(player2);
		couple.add(player);
		
		this.couple = new Team(ChatColor.LIGHT_PURPLE+"Couple", Camp.Love, GameLgUtil.getGameOfPlayer(playerWithRole, "at Couple creating"), members, null, "at Couple creating", null, couple, true, false, false, true);
		GameLgUtil.getGameOfPlayer(playerWithRole, "at Couple creating 2").teams.add(this.couple);
		
		player.sendMessage(ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player2.Name);
		player2.sendMessage(ChatColor.LIGHT_PURPLE + "Vous êtes amoureux..." + "\n"+ "Vous devez gagner vous votre amoureux et le cupidon, pour cela vous pouvez lui faire don d'une partie de votre vie avec la commande /don [pourcentage de votre vie], cependant, s'il meure, vous le rejoindrez dans sa tombe..." + "\n"+ "Votre amoureux est "+ player.Name);

		player.inLove = true;
		player2.inLove = true;
		
		Cupidon.sendMessage(ChatColor.LIGHT_PURPLE +"Vous avez mis "+ player.Name + " et "+ player2.Name + " en couple");
		System.out.println("end creating couple");
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void giveNightEffect() {
		// TODO Auto-generated method stub
		
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

}
