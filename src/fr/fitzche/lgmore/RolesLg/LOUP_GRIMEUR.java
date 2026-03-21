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
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class LOUP_GRIMEUR implements RoleInstance {
	public PlayerData playerWithRole;

	public static Camp camp = Camp.Wolf;
	public String name ="Loup-Garou Grimeur";
	public int wait = 0;
	
	public LOUP_GRIMEUR(PlayerData player) {
		this.playerWithRole = player;
		
		
	}
	
	

	
	
	@Override
	public String getName() {
		
		return (this.camp.getColor() +name);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return (Main.info +ChatColor.BLUE+"Vous devez gagner avec les Loups Garou (vous possédez la liste de vos alliés loups), pour cela vous obtenez Force I la Nuit, de plus vous aurez la possibilité d'afficher les joueurs que vous tuez comme loup garou à leurs morts");
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
	
	public void grim(PlayerData player) {
		TextComponent text = new TextComponent();
		text.setText(player.Name + ChatColor.BLUE+" est mort de votre main, vous avez 15s pour "+ChatColor.RED+"choisir de le grimmer"+ChatColor.BLUE+" ou non en cliquant sur ce message ");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg grimmer "+player.Name+ " "+ this.playerWithRole.Name)));
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
		 if (args[0].equals("grimmer")) {
				
			PlayerData senderPlD = Main.getData(args[2]);
			if (senderPlD.role != null && senderPlD.getName().equals(playerWithRole.getName())) {
					
				PlayerData p = Main.getData(args[1]);
				p.grimed = true;
				sender.sendMessage(ChatColor.GOLD+"Vous avez grimmé "+ args[1]);
			}
		} 
		
	}

}
