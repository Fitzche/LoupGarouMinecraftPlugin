package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class INFECT_PERE_DES_LOUPS implements RoleInstance {
	public PlayerData playerWithRole;
	public PotionEffect STRENGTH;
	public boolean powerUsed;
	public Camp camp;
	public String name ="Infect Père des loups";
	
	public static ItemStack logo = new ItemStack(Material.SPIDER_EYE);

	public INFECT_PERE_DES_LOUPS(PlayerData player) {
		this.playerWithRole = player;
		
		this.STRENGTH = PotionUtil.STRENGTH;
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
		}
		PotionUtil.giveIncreaseDamage(playerWithRole);
		
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
	
	public void infect(PlayerData player, PlayerData killer) {
		System.out.println("*1*1*1*0");
		
		if (player.equals(this.playerWithRole)) {
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
		System.out.println("*1*1*1*1");
		
		TextComponent text = new TextComponent();
		System.out.println("*1*1*1*2");
		text.setText(player.Name + ChatColor.BLUE+" est mort, vous avez 15s pour "+ChatColor.RED+"l infecter"+ChatColor.BLUE+" ou non en cliquant sur ce message ");
		System.out.println("*1*1*1*3");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg revive "+player.Name+ " "+ this.playerWithRole.Name)));
		System.out.println("*1*1*1*4");
		this.playerWithRole.player.spigot().sendMessage(text);
		System.out.println("*1*1*1*5");
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

}