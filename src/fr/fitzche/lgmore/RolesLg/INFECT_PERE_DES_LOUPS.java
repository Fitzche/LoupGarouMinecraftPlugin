package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class INFECT_PERE_DES_LOUPS implements RoleInstance {
	public PlayerData playerWithRole;
	
	public boolean powerUsed;
	public Camp camp;
	public String name ="Infect Père des loups";
	public int wait = 0;
	public static ItemStack logo = new ItemStack(Material.SPIDER_EYE);

	public INFECT_PERE_DES_LOUPS(PlayerData player) {
		this.playerWithRole = player;
		
		
		this.powerUsed = false;
		
		this.playerWithRole = player;
		this.camp = Camp.Wolf;
		

	}
	

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor()+name);
	}

	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (ChatColor.DARK_BLUE+"Vous êtes Infect Père des Loups, vous devez gagner avec les Loups Garou, vous possédez la liste de vos alliés loups, pour cela vous obtenez Force I la Nuit, de plus, une fois par partie vous pourrez infecter un joueur en cliquant sur un message qui vous sera envoyé avant l'annonce de sa mort, celui-ci deviendra loup garou mais gardera les pouvoirs de son role");
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
			return;
		}
		if (wait > 0) {
			wait --;
			return;
		}
		wait = 3;
		playerWithRole.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 79, 0, false, false));
		
	}


	@Override
	public void giveDayEffect() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startSpecialEvent() {
		// TODO Auto-generated method stub
		
	}
	
	public void infect(PlayerData player, PlayerData killer) {
		System.out.println("*1*1*1*0");
		
		if (player.getName().equals(this.playerWithRole.getName())) {
			System.out.println("ne peut pas s'auto-rez");
			return;
		}
		if (killer==null) {
			System.out.println("pas de killer");
			return;
		}
		if (!killer.role.getCampOfRole().equals(Camp.Wolf)) {
			System.out.println("tueur non loups");
			return;
		}
		
		if (this.powerUsed) {
			System.out.println("pouvoir déjà utilisé");
			return;
		}
		if (player.infected) {
			System.out.println("déjà infecté");
			return;
		}
		
		TextComponent text = new TextComponent();
		text.setText(player.Name + ChatColor.BLUE+" est mort, vous avez 15s pour "+ChatColor.RED+"l infecter"+ChatColor.BLUE+" ou non en cliquant sur ce message ");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg revive "+player.Name+ " "+ this.playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text);
		
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