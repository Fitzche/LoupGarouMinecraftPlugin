package fr.fitzche.lgmore.RolesLg;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.Potion.Tier;
import org.bukkit.potion.PotionType;

import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class SORCIERE implements RoleInstance {
	public boolean powerUsed;
	public PlayerData playerWithRole;
	public static Camp camp = Camp.Villager;
	public String name ="Sorcière";
	
	
	public SORCIERE(PlayerData ply) {
		this.powerUsed = false;
		this.playerWithRole = ply;
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.camp.getColor() + name;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return ChatColor.DARK_BLUE+"Vous devez gagner avec les villageois, pour cela vous pouvez une fois dans la partie ressuciter un joueur en cliquant sur un message, vous posédez également une potion de instant heal, une potion de regeneration, et une potion de instant damage";
	}


	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	@Override
	public void giveRoleEffectAndItem(PlayerData player) {
		Potion p1 = new Potion(PotionType.INSTANT_HEAL);
		p1.setSplash(true);
		player.player.getInventory().addItem(new ItemStack(p1.toItemStack(1)));
		Potion p2 = new Potion(PotionType.INSTANT_DAMAGE);
		p2.setSplash(true);
		player.player.getInventory().addItem(new ItemStack(p2.toItemStack(2)));
		Potion p3 = new Potion(PotionType.REGEN);
		p3.setSplash(true);
		player.player.getInventory().addItem(new ItemStack(p3.toItemStack(1)));
		
		
		
	}

	@Override
	public void giveEffectAllTime() {
		// TODO Auto-generated method stub
		
	}
	public static ItemStack logo = new ItemStack(Material.POTION);


	@Override
	public void giveNightEffect() {
		if (this.playerWithRole.infected) {
			System.out.println("nk.1");
			if (playerWithRole == null) {
				System.out.println("effect can't be gived at null player");
			}
			PotionUtil.giveIncreaseDamage(playerWithRole);
			
			//VOIR SCHEDULER + EFFECT = ERROR ???
			System.out.println("nk.2");
		}
		
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
	
	public void lifePotion(PlayerData player) {
		if (player.equals(this.playerWithRole)) {
			System.out.println("ne peut pas s'auto-rez");
			return;
		}
		if (this.powerUsed) {
			System.out.println("pouvoir déjà utilisé");
			return;
		}
		
		TextComponent text = new TextComponent();
		System.out.println("*1*1*1*2");
		text.setText(player.Name + ChatColor.BLUE+" est mort, vous avez 15s pour "+ChatColor.DARK_PURPLE+"le ressuciter"+ChatColor.BLUE+" ou non en cliquant sur ce message ");
		System.out.println("*1*1*1*3");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg revive "+player.Name+ " "+ this.playerWithRole.Name)));
		System.out.println("*1*1*1*4");
		this.playerWithRole.player.spigot().sendMessage(text);
		System.out.println("*1*1*1*5");
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
