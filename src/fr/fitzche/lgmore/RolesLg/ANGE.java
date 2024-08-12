package fr.fitzche.lgmore.RolesLg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.fitzche.lgmore.GameLg;
import fr.fitzche.lgmore.Main;
import fr.fitzche.lgmore.PlayerData;
import fr.fitzche.lgmore.Love.Team;
import fr.fitzche.lgmore.RolesLg.Checkers.AngeChecker;
import fr.fitzche.lgmore.Util.GameLgUtil;
import fr.fitzche.lgmore.Util.LocationUtil;
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
		this.game = GameLgUtil.getGameOfPlayer(player, "at angel creating");
		this.game.resCheckers.add(new AngeChecker(game, target, this));
	}

	public boolean version;
	
	@Override
	public String getName() {
		return (this.camp.getColor() +name);
	}
	
	public String getDescription() {
		return (ChatColor.DARK_BLUE+"Vous devez choisir entre ange gardien et ange déchu,");
	}
	public static ItemStack logo = new ItemStack(Material.FEATHER);

	
	public void giveEffectAllTime() {
		if (version) {
			if (game.getPlayerAlive().size() == 2 && playerWithRole.inLife && target.inLife) {
				ArrayList<PlayerData> angels = new ArrayList<PlayerData>();
				angels.add(playerWithRole);
				angels.add(target);
				Team team = new Team("angel team", Camp.Other, game, null, null, "null", null, null, true, false, false, false);
			}
			
		}
		
	}


	public void targetDeath(String name) {
		if (version) {
			playerWithRole.sendMessage("Votre protégé est mort, vous perdez donc 4 coeurs");
			playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() - 8);

		} else {
			if (name.equals(playerWithRole.Name)) {
				playerWithRole.sendMessage("Votre cible est morte de votre main, vous gagnez donc 3 coeurs ainsi que résistance 40% ");
				playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 6);
				playerWithRole.player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0));
				playerWithRole.boostR5 = 2;
			} else {
				playerWithRole.sendMessage("Votre cible est morte, mais pas de votre main, vous gagnez donc 3 coeurs");
				playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 6);


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
		text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg angechoose d"+ playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text);

		TextComponent text2 = new TextComponent();
		text2.setText("Vous pouvez choisir ange gardien en cliquant ici");
		text2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ("/lg angechoose g"+ playerWithRole.Name)));
		this.playerWithRole.player.spigot().sendMessage(text2);


		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plug, new BukkitRunnable() {

			@Override
			public void run() {
				if (LocationUtil.getDistanceBetween(playerWithRole, target) < 21) {
					if ((playerWithRole.player.getMaxHealth() - playerWithRole.player.getHealth()) > 0) {
						playerWithRole.player.setHealth(playerWithRole.player.getHealth() +1);
					}
					if ((target.player.getMaxHealth() - target.player.getHealth()) > 0) {
						target.player.setHealth(target.player.getHealth() +1);
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
			playerWithRole.sendMessage("Vous êtes l'ange gardien du joueur "+ p.Name + " dont le role est "+ p.role.getName()+", vous devez le protéger  sous peine de perdre 4 coeurs et de devoir gagner tout seul, pour cela vous obtenez 5 coeur, et vous ainsi que votre protégé régénérez 1/2 coeur toutes les 30 s à condition d'etre à moins de 20 blocs");
			playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 8);

		} else {
			done = true;
			this.version = false;
			playerWithRole.sendMessage("Vous êtes ange déchu, vous devez donc tuer le joueur " + p.Name+ " pour gagner 3 coeurs et résistance 40% de manière permanente, pour cela vous posséder 2 coeur en plus");
			playerWithRole.player.setMaxHealth(playerWithRole.player.getMaxHealth() + 4);
		}

	}
	
	
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
}
