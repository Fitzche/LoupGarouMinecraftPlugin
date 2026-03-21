package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.Camp;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.RoleInstance;
import fr.fitzche.lgmore.Lg.GameLg;

import fr.fitzche.lgmore.RolesLg.Checkers.AngeChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
import fr.fitzche.lgmore.Util.MathUtil;
import fr.fitzche.lgmore.Util.PlayerUtil;
import fr.fitzche.lgmore.Util.PotionUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ANGE implements RoleInstance {
	public PlayerData playerWithRole;
	public PlayerData target;
	public boolean done = false;
	public String name ="Ange";
	public Camp camp = Camp.Other;
	public GameLg game;

	public ANGE(PlayerData player) {
		this.playerWithRole = player;
		this.game = (GameLg) player.game;
		
	}

	public boolean version;
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (Main.info +ChatColor.BLUE+"Vous devez choisir entre ange gardien et ange déchu, vous obtenez un message sur lequel vous devez cliquer pour choisir.");
	}
	public static ItemStack logo = new ItemStack(Material.FEATHER);

	
	public void giveEffectAllTime() {
		if (version) {
			if (game.getPlayerAlive().size() == 2 && playerWithRole.inLife && target.inLife) {
				game.win(Camp.Other);
				
			}
			
		}
		
	}


	public void targetDeath(String name) {
		if (version) {
			playerWithRole.sendMessage("Votre protégé est mort, vous perdez donc 4 coeurs");
			playerWithRole.changeHealth(-8);

		} else {
			if (name.equals(playerWithRole.Name)) {
				playerWithRole.sendMessage("Votre cible est morte de votre main, vous gagnez donc 3 coeurs ainsi que résistance 10% ");
				playerWithRole.changeHealth(6);
				playerWithRole.boostR5 += 2;
				
			} else {
				playerWithRole.sendMessage("Votre cible est morte, mais pas de votre main, vous gagnez donc 3 coeurs");
				playerWithRole.changeHealth(6);


			}
			
		}
	}
	
	
	@Override
    public void changeTo(PlayerData player) {
        playerWithRole = player;
    }
	@Deprecated
	public void giveRoleEffectAndItem(PlayerData player) {
		TextComponent text = new TextComponent();
		text.setText("Vous pouvez choisir ange déchu en cliquant ici");
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg angechoose "+"d "+ playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text);

		TextComponent text2 = new TextComponent();
		text2.setText("Vous pouvez choisir ange gardien en cliquant ici");
		text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg angechoose "+"g "+ playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text2);
		this.playerWithRole.sendMessage("Vous avez 28 sec pour choisir ou cela sera fait automatiquement");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (done) {
					return;
				}
				int x = MathUtil.generateAlInt(0, 1);
				chooseVersion((x==1));
			}
			
		}, 560);
		
		ANGE ange = this;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (target == null) {
					System.out.println("target is null");
				}
				game.resCheckers.add(new AngeChecker(game, target,ange));

				if (LocationUtil.getDistanceBetween(playerWithRole, target) < 21) {
					if ((playerWithRole.getMaxHealth() - playerWithRole.getHealth()) > 0) {
						playerWithRole.setHealth(playerWithRole.getHealth() +1);
					}
					if ((target.getMaxHealth() - target.getHealth()) > 0) {
						target.setHealth(target.getHealth() +1);
					}
				}
			}
			
		}, 600);
	}

	public void chooseVersion(boolean c) {
		if (done) {
			return;
		}

		PlayerData p = GameLgUtil.getAlPlayerWithout(game, playerWithRole);
		this.target = p;
		if (c) {
			done = true;
			this.version = true;
			playerWithRole.sendMessage(ChatColor.AQUA+"Vous êtes l'ange gardien du joueur "+ p.Name + " dont le role est "+ p.role.getName()+", vous devez le protéger  sous peine de perdre 4 coeurs et de devoir gagner tout seul, pour cela vous obtenez 5 coeur, et vous ainsi que votre protégé régénérez 1/2 coeur toutes les 30 s à condition d'etre à moins de 20 blocs, ce message ne vous sera pas renvoyé, ne le perdez pas");
			playerWithRole.setMaxHealth(playerWithRole.getMaxHealth() + 10);
			p.sendMessage(ChatColor.AQUA+"Vous êtes protégé par l'ange");

		} else {
			done = true;
			this.version = false;
			playerWithRole.sendMessage("Vous êtes ange déchu, vous devez donc tuer le joueur " + p.Name+ " pour gagner 3 coeurs et résistance 1 de manière permanente, pour cela vous posséder 2 coeur en plus, ce message ne vous sera pas renvoyé, ne le perdez pas");
			playerWithRole.changeHealth(4);
		}

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void command(CommandSender sender, Command cmd, String msg, String[] args) {
		if (args[0].equals("angechoose")) {
			PlayerData player = Main.strToPlayer.getOrDefault(args[2], null);
			if (player.getName().equals(playerWithRole.getName())) {
				
				switch (args[1]) {
				case "d":
					chooseVersion(false);

				case "g":
					chooseVersion(true);

			}
			}
			
		} 
		
	}
}
